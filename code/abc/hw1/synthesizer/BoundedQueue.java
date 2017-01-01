package synthesizer;
import java.util.Iterator;
/**
 * Created by mark on 6/23/16.
 */
public interface BoundedQueue<T> extends Iterable<T>{
    int capacity();
    int fillCount();
    void enqueue(T t);
    T dequeue();
    T peek();
    Iterator<T> iterator();
    default boolean isEmpty() {
        if (fillCount() == 0)
            return true;
        else
            return false;
    }
    default boolean  isFull(){
        if (fillCount() == capacity())
            return true;
        else
            return false;
    }

}
