package lt.swedbank.playground.library.sites.manager;

import lt.swedbank.playground.library.visitors.Kid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DoubleQueueTest {

    @Test
    public void shouldPopFromQueueInCorrectOrder() {
        Kid first = new Kid(1, "first", true, 1);
        Kid second = new Kid(2, "second", true, 2);
        Kid third = new Kid(3, "third", true, 3);
        Kid vip = new Kid(4, "fourth", true, 4);

        DoubleQueue queue = new DoubleQueue();
        queue.addToVipQueue(vip);
        queue.addToQueue(first);
        queue.addToQueue(second);
        queue.addToQueue(third);

        List<Kid> result = new ArrayList<>();
        result.add(queue.popFromQueue().get());
        result.add(queue.popFromQueue().get());
        result.add(queue.popFromQueue().get());
        result.add(queue.popFromQueue().get());

        assertThat(result.get(0).getId(), equalTo(1));
        assertThat(result.get(1).getId(), equalTo(2));
        assertThat(result.get(2).getId(), equalTo(4));
        assertThat(result.get(3).getId(), equalTo(3));
    }
}
