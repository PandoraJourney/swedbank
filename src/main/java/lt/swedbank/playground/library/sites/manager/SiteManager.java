package lt.swedbank.playground.library.sites.manager;

import lt.swedbank.playground.library.reports.KidVisitor;
import lt.swedbank.playground.library.sites.Site;
import lt.swedbank.playground.library.visitors.Kid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteManager implements Manager {
    @Autowired
    private KidVisitor tracker;
    @Autowired
    private DoubleQueue doubleQueue;
    private List<Kid> nowPlaying = new ArrayList<>();
    private Site site;

    public SiteManager(Site site) {
        this.site = site;
    }

    @Override
    public boolean popKid(Kid kid) {
        boolean result;
        if (doubleQueue.removeKidWithoutCounterChange(kid)) {
            result = true;
        } else {
            Optional<Kid> kidOnSite = nowPlaying.stream()
                    .filter(kid::equals)
                    .findFirst();
            kidOnSite.ifPresent(this::removeKidFromPlaySite);
            result = kidOnSite.isPresent();
        }
        return result;
    }

    @Override
    public boolean addKidToPlaySite(Kid kid, boolean useVip) {
        boolean kidAdded = false;
        if (!canAcceptKid() && kid.isAcceptWaiting()) {
            kidAdded = queueKid(kid, useVip);
        } else if (canAcceptKid()) {
            kidAdded = addKidToPlaySite(kid);
        }
        return kidAdded;
    }


    @Override
    public boolean canAcceptKid() {
        return nowPlaying.size() < site.kidsAllowed();
    }


    @Override
    public boolean containsKid(Kid kid) {
        assert kid != null;
        return nowPlaying.stream()
                .anyMatch(kid::equals);
    }

    @Override
    public String siteName() {
        return site.getName();
    }

    private boolean queueKid(Kid kid, boolean useVip) {
        boolean result;
        if (useVip && kid.getTicket().useVip()) {
            result = doubleQueue.addToVipQueue(kid);
        } else {
            result = doubleQueue.addToQueue(kid);
        }
        return result;
    }

    private void removeKidFromPlaySite(Kid kid) {
        nowPlaying.remove(kid);
        tracker.closeKidVisitOnSite(kid);
        doubleQueue.popFromQueue()
                .ifPresent(this::addKidToPlaySite);
    }

    private boolean addKidToPlaySite(Kid kid) {
        tracker.registerKidOnSite(kid, site);
        return nowPlaying.add(kid);
    }

}
