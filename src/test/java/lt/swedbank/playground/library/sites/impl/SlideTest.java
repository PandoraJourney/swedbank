package lt.swedbank.playground.library.sites.impl;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SlideTest {
    @Test
    public void shouldEvaluateUtilization() {
        Slide slide = new Slide(20, "test");
        double result = slide.utilization(2);

        assertThat(result, equalTo(10.0));
    }
}
