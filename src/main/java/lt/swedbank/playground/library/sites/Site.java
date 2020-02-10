package lt.swedbank.playground.library.sites;

public abstract class Site {
    private final String name;

    public abstract double utilization(long currentlyPlaying);

    public abstract long kidsAllowed();

    public Site(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
