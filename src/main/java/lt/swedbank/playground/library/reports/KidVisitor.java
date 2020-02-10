package lt.swedbank.playground.library.reports;

import lt.swedbank.playground.library.sites.Site;
import lt.swedbank.playground.library.visitors.Kid;

public interface KidVisitor {
    void registerKidOnSite(Kid kid, Site site);

    void closeKidVisitOnSite(Kid kid);
}
