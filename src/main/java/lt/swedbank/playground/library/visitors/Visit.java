package lt.swedbank.playground.library.visitors;

import lt.swedbank.playground.library.reports.ChildPlayedOnSite;
import lt.swedbank.playground.library.sites.Site;

import java.time.Duration;
import java.time.LocalDateTime;

public class Visit {
    private final Site site;
    private final Kid kid;
    private final LocalDateTime start;
    private LocalDateTime finish;
    private boolean active;

    public Visit(Site site, Kid kid, LocalDateTime start) {
        this.site = site;
        this.kid = kid;
        this.start = start;
        this.finish = null;
        this.active = true;
    }

    public Visit(Visit visit, LocalDateTime endTime) {
        this.site = visit.site;
        this.kid = visit.kid;
        this.start = visit.start;
        this.finish = endTime;
        this.active = false;
    }

    public void finish() {
        this.finish = LocalDateTime.now();
        this.active = false;
    }

    public Site getSite() {
        return site;
    }

    public Kid getKid() {
        return kid;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public boolean isEnded() {
        return !active;
    }

    public boolean isActive() {
        return active;
    }

    public ChildPlayedOnSite createReport() {
        Duration period = Duration.between(start, finish);
        return new ChildPlayedOnSite(site.getName(), kid, period);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "site=" + site.getName() +
                ", kid=" + kid.getName() +
                ", start=" + start +
                ", finish=" + finish +
                ", active=" + active +
                '}';
    }
}
