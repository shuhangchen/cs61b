/**
 * Created by mark on 6/20/16.
 */
public class LinkedListDeque<itemType> {
    public class IntNode {
        public itemType item;
        public IntNode next;
        public IntNode prev;

        public IntNode() {
            next = this;
            prev = this;
        }

        public IntNode (itemType i, IntNode p, IntNode n) {
            item = i ;
            next = n;
            prev = p;
        }
    }


    private IntNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new IntNode();
    }

    public void addFirst(itemType item) {
        if (sentinel.next == sentinel) {
            IntNode newNode = new IntNode(item, sentinel, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            return;
        }
        IntNode firstElem = sentinel.next;
        IntNode newNode = new IntNode(item, sentinel, firstElem);
        sentinel.next = newNode;
        firstElem.prev = newNode;
        size += 1;
        return;
    }

    public void addLast(itemType item) {
        if (sentinel.next == sentinel) {
            IntNode newNode = new IntNode(item, sentinel, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            return ;
        }
        IntNode lastElem = sentinel.prev;
        IntNode newNode = new IntNode(item, lastElem, sentinel);
        sentinel.prev = newNode;
        lastElem.next = newNode;
        size += 1;
        return;

    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel)
            return true;
        else
            return false;
    }

    public int size() {
        return size;
    }

    public void printDeque () {
        if (isEmpty()) {
            System.out.println(" ");
            return;
        }
        IntNode elem = sentinel.next;
        String printedMess = " ";
        while(elem != sentinel) {
            printedMess += elem.item.toString() + " ";
            elem = elem.next;
        }
        System.out.println(printedMess);
    }

    public itemType removeFirst() {
        if (isEmpty()) {
            return null;
        }
        IntNode firstElem = sentinel.next;
        IntNode nextElem = firstElem.next;
        nextElem.prev = sentinel;
        sentinel.next = nextElem;
        size -= 1;
        return firstElem.item;
    }

    public itemType removeLast() {
        if (isEmpty()) {
            return null;
        }
        IntNode lastElem = sentinel.prev;
        IntNode lastSecondElem = lastElem.prev;
        lastSecondElem.next = sentinel;
        sentinel.prev = lastSecondElem;
        return lastElem.item;
    }

    public itemType get(int index) {
        if (index > size -1) {
            return null;
        }
        IntNode indexElem = sentinel;
        while (index >= 0) {
            indexElem = indexElem.next;
            index -= 1;
        }
        return indexElem.item;
    }

}
