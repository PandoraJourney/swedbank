package lt.swedbank.playground.library.sites.impl;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DoubleSwingTest {

    @Test
    public void shouldEvaluateEmptyUtilization() {
        DoubleSwing ballPit = new DoubleSwing("test");
        double result = ballPit.utilization(1);

        assertThat(result, equalTo(0.0));
    }

    @Test
    public void shouldEvaluateFullUtilization() {
        DoubleSwing ballPit = new DoubleSwing("test");
        double result = ballPit.utilization(2);

        assertThat(result, equalTo(100.0));
    }
}
