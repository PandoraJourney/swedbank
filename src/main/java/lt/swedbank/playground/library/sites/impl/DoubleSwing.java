package lt.swedbank.playground.library.sites.impl;

import lt.swedbank.playground.library.sites.Site;

public class DoubleSwing extends Site {


    public DoubleSwing(String name) {
        super(name);
    }

    @Override
    public long kidsAllowed() {
        return 2;
    }

    @Override
    public double utilization(long curentlyPlaying) {
        if (curentlyPlaying < 2) {
            return 0;
        }
        return 100;
    }

}
