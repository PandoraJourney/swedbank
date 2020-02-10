package lt.swedbank.playground.library.sites.impl;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarouselTest {

    @Test
    public void shouldEvaluateUtilization() {
        Carousel carousel = new Carousel(10, "test");
        double result = carousel.utilization(3);

        assertThat(result, equalTo(30.0));
    }
}
