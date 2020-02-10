package lt.swedbank.playground.library.reports;

import lt.swedbank.playground.library.visitors.Kid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public interface SiteReports {
    Map<String, ChildPlayedOnSite> findPlaySitesKidPlayed(Kid kid);

    Long findVisitorCountDuringDay(LocalDate date);

    SiteUtilSnapshot retrieveSiteUtilizationSnapshot(String siteName, LocalDateTime time);
}
