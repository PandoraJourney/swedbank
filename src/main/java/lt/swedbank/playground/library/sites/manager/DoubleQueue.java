package lt.swedbank.playground.library.sites.manager;

import lt.swedbank.playground.library.visitors.Kid;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class DoubleQueue {
    private final CountingQueue<Kid> queue = new CountingQueue<>(new LinkedList<>());
    private final Queue<Kid> queueVip = new LinkedList<>();

    boolean addToQueue(Kid kid) {
        return queue.add(kid);
    }

    boolean addToVipQueue(Kid kid) {
        return queueVip.add(kid);
    }

    Optional<Kid> popFromQueue() {
        int kidsPlayed = queue.getCount();
        Optional<Kid> result = Optional.empty();
        if ((kidsPlayed != 0 && kidsPlayed % 2 == 0)) {
            result = Optional.ofNullable(queueVip.poll());
        }
        if (!result.isPresent()) {
            result = Optional.ofNullable(queue.poll());
        }
        return result;
    }

    public boolean removeKidWithoutCounterChange(Kid kid) {
        return queue.removeWithoutCounter(kid) || queueVip.remove(kid);
    }
}
