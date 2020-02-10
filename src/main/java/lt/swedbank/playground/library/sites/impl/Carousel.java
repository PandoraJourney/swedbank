package lt.swedbank.playground.library.sites.impl;

import lt.swedbank.playground.library.sites.Site;

public class Carousel extends Site {
    private final long kidCount;

    public Carousel(long kidCount, String name) {
        super(name);
        this.kidCount = kidCount;
    }

    @Override
    public long kidsAllowed() {
        return kidCount;
    }

    @Override
    public double utilization(long currentlyPlaying) {
        return (double)currentlyPlaying / (double)kidCount * 100.0;
    }

}
