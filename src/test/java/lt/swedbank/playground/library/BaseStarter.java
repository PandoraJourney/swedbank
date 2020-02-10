package lt.swedbank.playground.library;

import lt.swedbank.playground.library.reports.ChildPlayedOnSite;
import lt.swedbank.playground.library.reports.KidVisitTracker;
import lt.swedbank.playground.library.reports.SiteUtilSnapshot;
import lt.swedbank.playground.library.sites.manager.Manager;
import lt.swedbank.playground.library.visitors.Kid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ImportResource(value = "classpath:playground.xml")
public class BaseStarter {

    private static final Log LOG = LogFactory.getLog(BaseStarter.class);

    @Autowired
    SiteControlService siteControl;

    @Autowired
    KidVisitTracker tracker;

    @Autowired
    Manager manager;

    @Test
    public void setupOneKidTest() {
        Kid kid = new Kid(1, "Juozas", true, 10);

        try {
            boolean result = siteControl.addKidToSite("slide", kid, false);
            SiteUtilSnapshot snapshot = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
            LOG.debug(snapshot);
            Kid remove = siteControl.popKidFromSite("slide", kid);
            SiteUtilSnapshot snapshotOff = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
            LOG.debug(snapshotOff);

            Long count = tracker.findVisitorCountDuringDay(LocalDate.now());
            LOG.debug("Count per day: " + count);

            Map<String, ChildPlayedOnSite> statistics = tracker.findPlaySitesKidPlayed(kid);

            statistics.values().stream().forEach(LOG::debug);

            assertTrue(result);
            assertThat(remove, equalTo(kid));
            assertThat(count, equalTo(1L));
        } catch (SiteNotFoundException | KidException ex) {
            LOG.debug("Site not found", ex);
        }


    }

    @Test
    public void setupWithQueueTest() throws InterruptedException{
        LinkedList<Kid> kids = new LinkedList<>();
        kids.add(new Kid(1, "test1", true, 10));
        kids.add(new Kid(2, "test2", true, 10));
        kids.add(new Kid(3, "test3", true, 10));
        kids.add(new Kid(4, "test4", true, 10));
        kids.add(new Kid(5, "test5", true, 10));

        try {
            for(Kid k : kids) {
                siteControl.addKidToSite("slide", k, false);
            }
            SiteUtilSnapshot snapshot = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
            LOG.debug(snapshot);
            sleep(3L * 1000L);
            Kid removedKid = siteControl.popKidFromSite("slide", kids.get(0));
            LOG.debug(kids.pop());
            SiteUtilSnapshot snapshotOff = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
            LOG.debug("Snapshot after kid " + removedKid.getName() + " left " + snapshotOff);

            siteControl.addKidToSite("slide", removedKid, true);
            kids.add(removedKid);
            snapshot = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
            LOG.debug(snapshot);

            for(Kid k : kids) {
                siteControl.popKidFromSite("slide", k);
                sleep(1000L);
                snapshot = tracker.retrieveSiteUtilizationSnapshot("slide", LocalDateTime.now());
                LOG.debug("Snapshot after kid " + k.getName() + " left " + snapshot);
            }

            Long count = tracker.findVisitorCountDuringDay(LocalDate.now());
            LOG.debug("Count per day: " + count);

            Map<String, ChildPlayedOnSite> playedOnSite = tracker.findPlaySitesKidPlayed(removedKid);
            playedOnSite.values().forEach(LOG::debug);

        } catch (SiteNotFoundException | KidException ex) {
            LOG.debug("Site not found" + ex.getMessage());
        }
    }
}
