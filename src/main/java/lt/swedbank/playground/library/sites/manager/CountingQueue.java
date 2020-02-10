package lt.swedbank.playground.library.sites.manager;

import java.util.Queue;


public class CountingQueue<E> extends ForwardingQueue<E> {
    private int count = 0;

    public CountingQueue(Queue<E> queue) {
        super(queue);
    }

    @Override
    public boolean remove(Object o) {
        if (super.remove(0)) {
            count++;
            return true;
        }
        return false;
    }

    @Override
    public E remove() {
        E kid = super.remove();
        if (kid != null) {
            count++;
        }
        return kid;
    }

    @Override
    public E poll() {
        E kid  = super.poll();
        if (kid != null) {
            count++;
        }
        return kid;
    }

    boolean removeWithoutCounter(E o) {
        return super.remove(o);
    }


    public int getCount() {
        return count;
    }
}
