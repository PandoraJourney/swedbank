package lt.swedbank.playground.library.sites.impl;

import lt.swedbank.playground.library.sites.Site;

public class BallPit extends Site {
    private final long kidCount;


    public BallPit(long kidCount, String name) {
        super(name);
        this.kidCount = kidCount;
    }

    @Override
    public double utilization(long currentlyPlaying) {
        return (double)currentlyPlaying / (double)kidCount * 100.0;
    }

    @Override
    public long kidsAllowed() {
        return kidCount;
    }
}
