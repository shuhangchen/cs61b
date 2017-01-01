/**
 * Created by mark on 6/21/16.
 */
public interface Deque<ItemTye> {
    public void addFirst(ItemTye item);
    public void addLast(ItemTye item);
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public ItemTye removeFirst();
    public ItemTye removeLast();
    public ItemTye get(int index);
}
