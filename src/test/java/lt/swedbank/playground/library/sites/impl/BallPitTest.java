package lt.swedbank.playground.library.sites.impl;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class BallPitTest {

    @Test
    public void shouldEvaluateUtilization() {
        BallPit ballPit = new BallPit(10, "test");
        double result = ballPit.utilization(5);

        assertThat(result, equalTo(50.0));
    }
}
