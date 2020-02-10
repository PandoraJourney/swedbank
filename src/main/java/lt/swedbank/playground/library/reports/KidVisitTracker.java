package lt.swedbank.playground.library.reports;

import lt.swedbank.playground.library.sites.Site;
import lt.swedbank.playground.library.visitors.Kid;
import lt.swedbank.playground.library.visitors.Visit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class KidVisitTracker implements KidVisitor, SiteReports {
    private final List<Visit> visits = new ArrayList<>();

    @Override
    public void registerKidOnSite(Kid kid, Site site) {
        Visit visit = new Visit(site, kid, LocalDateTime.now());
        visits.add(visit);
    }

    @Override
    public void closeKidVisitOnSite(Kid kid) {
        visits.stream()
                .filter(v -> v.getKid().equals(kid))
                .filter(Visit::isActive)
                .findFirst()
                .ifPresent(Visit::finish);
    }

    @Override
    public Map<String, ChildPlayedOnSite> findPlaySitesKidPlayed(Kid kid) {
        return visits.stream()
                .filter(Visit::isEnded)
                .map(Visit::createReport)
                .collect(Collectors.toMap(ChildPlayedOnSite::getSiteName, Function.identity(), ChildPlayedOnSite::add));
    }

    @Override
    public Long findVisitorCountDuringDay(LocalDate date) {
        return visits.stream()
                .filter(visit -> visit.getStart().toLocalDate().equals(date))
                .map(Visit::getKid)
                .count();
    }

    @Override
    public SiteUtilSnapshot retrieveSiteUtilizationSnapshot(String siteName, LocalDateTime time) {
        assert time != null;
        Optional<Pair> pair =
                visits.stream()
                        .filter(visit -> visit.getSite().getName().equals(siteName))
                        .filter(visit -> verifyCorrectTime(time, visit))
                        .map(v -> new Pair(v.getSite(), 1L))
                        .reduce(Pair::add);
        return pair.map(p -> new SiteUtilSnapshot(p.site.getName(), p.site.utilization(p.count)))
                .orElse(new SiteUtilSnapshot(siteName, 0.0));
    }

    private boolean verifyCorrectTime(LocalDateTime time, Visit visit) {
        return time.isAfter(visit.getStart()) &&
                (!visit.isEnded() || time.isBefore(visit.getFinish()));
    }


    private static class Pair {
            private final Site site;
            private final long count;

        public Pair(Site site, long count) {
            this.site = site;
            this.count = count;
        }

        public Pair add(Pair pair) {
            return new Pair(site, pair.count + count);
        }
    }

    public List<Visit> getVisits() {
        return visits;
    }
}
