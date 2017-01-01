package synthesizer;

/**
 * Created by mark on 6/23/16.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }


}
