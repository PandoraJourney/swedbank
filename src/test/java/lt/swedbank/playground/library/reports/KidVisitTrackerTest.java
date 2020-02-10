package lt.swedbank.playground.library.reports;

import lt.swedbank.playground.library.sites.Site;
import lt.swedbank.playground.library.visitors.Kid;
import lt.swedbank.playground.library.visitors.Visit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KidVisitTrackerTest {

    private final static Log LOG = LogFactory.getLog(KidVisitTrackerTest.class);

    private KidVisitTracker tracker;

    @BeforeEach
    public void init() {
        tracker = new KidVisitTracker();
    }

    @Test
    public void shouldRegisterKidOnSite() {
        Kid kid = new Kid(1, "test", true, 1);
        Site site = Mockito.mock(Site.class);

        tracker.registerKidOnSite(kid, site);

        List<Visit> result = tracker.getVisits();

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getKid().getId(), equalTo(1));
        assertThat(result.get(0).getKid().getName(), equalTo("test"));
        assertFalse(result.get(0).isEnded());
    }

    @Test
    public void shouldEndKidOnSite() {
        Kid kid = new Kid(1, "test", true, 1);
        Site site = Mockito.mock(Site.class);

        tracker.registerKidOnSite(kid, site);
        tracker.closeKidVisitOnSite(kid);

        List<Visit> result = tracker.getVisits();

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getKid().getId(), equalTo(1));
        assertThat(result.get(0).getKid().getName(), equalTo("test"));
        assertTrue(result.get(0).isEnded());
    }

    @Test
    public void shouldFindOnSiteKidPlayed() {
        Kid kid = new Kid(1, "test", true, 1);
        Site site = Mockito.mock(Site.class);

        when(site.getName()).thenReturn("name");
        tracker.registerKidOnSite(kid, site);
        tracker.closeKidVisitOnSite(kid);

        Map<String, ChildPlayedOnSite> result = tracker.findPlaySitesKidPlayed(kid);

        assertThat(result.keySet().size(), equalTo(1));
        assertThat(result.keySet().iterator().next(), equalTo("name"));
        assertThat(result.values().iterator().next().getKid().getName(), equalTo("test"));
        verify(site).getName();
    }

    @Test
    public void shouldfindVisitorCountDuringDay() {
        Kid kid = new Kid(1, "test", true, 1);
        Site site = Mockito.mock(Site.class);

        tracker.registerKidOnSite(kid, site);
        tracker.closeKidVisitOnSite(kid);

        long count = tracker.findVisitorCountDuringDay(LocalDate.now());

        assertThat(count, equalTo(1L));
    }

    @Test
    public void shoudlRetrieveSiteUtilizationSnapshot() {
        Kid kid = new Kid(1, "test", true, 1);
        Site site = Mockito.mock(Site.class);

        when(site.getName()).thenReturn("name");
        when(site.utilization(any(Long.class))).thenReturn(10.0);
        tracker.registerKidOnSite(kid, site);
        SiteUtilSnapshot snapshotOn = tracker.retrieveSiteUtilizationSnapshot("name", LocalDateTime.now());
        tracker.closeKidVisitOnSite(kid);
        SiteUtilSnapshot snapshotOff = tracker.retrieveSiteUtilizationSnapshot("name", LocalDateTime.now());

        assertThat(snapshotOn.getSite(), equalTo("name"));
        assertThat(snapshotOn.getUtilization(), equalTo(10.0));
        assertThat(snapshotOff.getUtilization(), equalTo(0.0));

        verify(site, times(3)).getName();
    }

}
