package lt.swedbank.playground.library.sites.manager;

import lt.swedbank.playground.library.visitors.Kid;

public interface Manager {
    boolean popKid(Kid kid);

    boolean addKidToPlaySite(Kid kid, boolean useVip);

    boolean canAcceptKid();

    boolean containsKid(Kid kid);

    String siteName();
}
