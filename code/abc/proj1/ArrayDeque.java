import java.lang.reflect.Array;
import java.math.*;
/**
 * Created by mark on 6/20/16.
 */
public class ArrayDeque<ItemType> {
    int arraySize = 8;
    ItemType[] itemArrays = (ItemType[])new Object[arraySize];
    int size;
    int nextFirst;
    int nextLast;

    public ArrayDeque() {
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (size == arraySize)
            return true;
        return false;
    }

    public void addFirst(ItemType item) {
       if (isFull()) {
            System.out.println("it is full!");
            expand();
            addFirst(item);
            return;
        }
        itemArrays[nextFirst] = item;
        size += 1;
        if(nextFirst!=0)
            nextFirst -= 1;
        else
            nextFirst = arraySize - 1;
    }

    public void addLast(ItemType item) {
        if (isFull()) {
            System.out.println("it is full!");
            expand();
            addLast(item);
            return;
        }
        itemArrays[nextLast] = item;
        size += 1;
        if (nextLast == arraySize - 1)
            nextLast = 0;
        else
            nextLast += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        System.out.println("starting at "+ nextFirst + ", and ending at "+ nextLast);
        String printedMess= " ";
        if (isEmpty()) {
            System.out.println(printedMess);
            return ;
        }
        int printedSize = 0;
        int index = nextFirst + 1;
        while (printedSize < size) {
            if (index > arraySize - 1) {
                index = 0;
            }
            printedMess += itemArrays[index].toString() + " ";
            index += 1;
            printedSize +=1;
        }
        System.out.println(printedMess);
        return;
    }

    public ItemType removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (nextFirst == arraySize - 1){
            ItemType item = itemArrays[0];
            nextFirst = 0;
            size -= 1;
            return item;
        } else {
            ItemType item = itemArrays[nextFirst + 1];
            nextFirst += 1;
            size -= 1;
            return item;
        }

    }

    public ItemType removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (nextLast == 0) {
            ItemType item = itemArrays[7];
            nextLast = arraySize - 1;
            size -= 1;
            return item;

        } else {
            ItemType item = itemArrays[nextLast - 1];
            nextLast -= 1;
            size -= 1;
            return item;
        }
    }

    public ItemType get (int index) {
        if (index >= size)
            return null;
        int realIndex = (index + nextFirst + 1)% arraySize;
        return itemArrays[realIndex];
    }

    public void expand () {
        int newArraySize =  2 * arraySize;
        ItemType[] newArray = (ItemType[])new Object[newArraySize];
        int index = 0;
        while (index < size ) {
            newArray[index+1] = get(index);
            index +=1;
        }
        nextFirst = 0;
        nextLast = size;
        arraySize = newArraySize;
        itemArrays = newArray;
    }
}
