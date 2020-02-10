package lt.swedbank.playground.library.reports;

import lt.swedbank.playground.library.visitors.Kid;

import java.time.Duration;

public class ChildPlayedOnSite {
    private final String siteName;
    private final Kid kid;
    private final Duration duration;

    public ChildPlayedOnSite(String siteName, Kid kid, Duration duration) {
        this.siteName = siteName;
        this.kid = kid;
        this.duration = duration;
    }

    public String getSiteName() {
        return siteName;
    }

    public Duration getDuration() {
        return duration;
    }

    public Kid getKid() {
        return kid;
    }

    public ChildPlayedOnSite add(ChildPlayedOnSite report) {
        return new ChildPlayedOnSite(siteName, kid, duration.plus(report.duration));
    }

    @Override
    public String toString() {
        return "ChildPlayedOnSite{" +
                "siteName='" + siteName + '\'' +
                ", kid=" + kid.getName() +
                ", duration=" + duration.getSeconds() + " sec" +
                '}';
    }
}
