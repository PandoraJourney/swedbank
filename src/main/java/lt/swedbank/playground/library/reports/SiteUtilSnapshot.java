package lt.swedbank.playground.library.reports;

public class SiteUtilSnapshot {
    private final String site;
    private final double utilization;

    public SiteUtilSnapshot(String site, double utilization) {
        this.site = site;
        this.utilization = utilization;
    }

    public String getSite() {
        return site;
    }

    public double getUtilization() {
        return utilization;
    }

    @Override
    public String toString() {
        return "SiteUtilSnapshot{" +
                "site='" + site + '\'' +
                ", utilization=" + utilization +
                '}';
    }
}
