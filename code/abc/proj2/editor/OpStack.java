/**
 * Created by shuhang on 8/18/16.
 */
public class OpStack {
    OpNode[] items;
    int size;

    public OpStack() {
        items = new OpNode[100];
        size = 0;
    }

    public int size() {return size;}

    public boolean isEmpty() {
        if(size() == 0) {
            return true;
        }
        return false;
    }

    public void push(OpNode op) {
        // the 100 limit of the editor
        if (size() >= 100) {
            clearStack();
            return;
        }
        items[size] = op;
        size += 1;
    }

    public OpNode pop() {
        if (isEmpty()) {
            return null;
        }
        OpNode deleted = items[size() - 1];
        size -= 1;
        return deleted;
    }

    public void clearStack() {
        size = 0;
    }
}
