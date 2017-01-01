/**
 * Created by mark on 7/24/16.
 */
/* package editor;  */

package editor;

public class fastLinkList<itemType> {

    public class itemNode{
        public itemType item;
        public itemNode next;
        public itemNode prev;
        public double charWidth;
        public itemNode() {
            next = this;
            prev = this;
        }

        public itemNode(itemType c, itemNode p, itemNode n) {
            item = c;
            next = n;
            prev = p;
            charWidth = 0;
        }

        public itemNode(itemType c, itemNode p, itemNode n, double cw) {
            item = c;
            next = n;
            prev = p;
            charWidth = cw;
        }

        public double getCharWidth() {
            return charWidth;
        }
    }

    private itemNode sentinel;
    private int size;
    private double lineWidth;
    private itemNode currentPos;

    public fastLinkList() {
        size = 0;
        sentinel = new itemNode();
        currentPos = sentinel;
        lineWidth = 0;
    }

    public void insert(itemType c) {
        if (isEmpty()) {
            itemNode newNode = new itemNode(c, sentinel, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            currentPos = newNode;
            return;
        }
        itemNode nextElem = currentPos.next;
        itemNode oldCurrentNode = currentPos;
        itemNode newNode = new itemNode(c, oldCurrentNode, nextElem);
        nextElem.prev =newNode;
        oldCurrentNode.next =newNode;
        currentPos = newNode;
        size += 1;
        return;
    }

    public void insert(itemType c, double letterLength) {
        if (isEmpty()) {
            itemNode newNode = new itemNode(c, sentinel, sentinel, letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            currentPos = newNode;
            return;
        }
        itemNode nextElem = currentPos.next;
        itemNode oldCurrentNode = currentPos;
        itemNode newNode = new itemNode(c, oldCurrentNode, nextElem, letterLength);
        nextElem.prev =newNode;
        oldCurrentNode.next =newNode;
        currentPos = newNode;
        size += 1;
        lineWidth += 1;
        return;
    }

    public itemNode delete() {
        if (isEmpty() || currentPos == sentinel) {
            return null;
        }
        itemNode prevElem = currentPos.prev;
        itemNode nextElem = currentPos.next;
        itemNode deleted = currentPos;
        prevElem.next = nextElem;
        nextElem.prev = prevElem;
        currentPos = prevElem;
        size -= 1;
        if (lineWidth != 0) {
            lineWidth -= deleted.getCharWidth();
        }
        return deleted;
    }

    public void addFirst(itemType item, double letterLength) {
        if (isEmpty()) {
            itemNode newNode = new itemNode(item, sentinel, sentinel,letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            return;
        }
        itemNode oldFirstItemNode = sentinel.next;
        itemNode newNode = new itemNode(item, sentinel, oldFirstItemNode, letterLength);
        sentinel.next = newNode;
        oldFirstItemNode.prev = newNode;
        size += 1;
        lineWidth += letterLength;
        return;
    }

    public void addLast(itemType item, double letterLength) {
        if (isEmpty()) {
            itemNode newNode = new itemNode(item, sentinel, sentinel, letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            return;
        }
        itemNode oldLastNode = sentinel.prev;
        itemNode newNode = new itemNode(item, oldLastNode, sentinel, letterLength);
        sentinel.prev = newNode;
        oldLastNode.next = newNode;
        size += 1;
        lineWidth += letterLength;
        return;
    }

    public itemNode removeFirst() {
        if (isEmpty()) {
            return null;
        }
        itemNode firstNode = sentinel.next;
        if (currentPos == firstNode) {
            currentPos = sentinel;
        }
        itemNode secondNode = firstNode.next;
        secondNode.prev = sentinel;
        sentinel.next = secondNode;
        size -= 1;
        if (lineWidth != 0 ) {
            lineWidth -= firstNode.getCharWidth();
        }
        return firstNode;
    }

    public itemNode removeLast() {
        if (isEmpty()) {
            return null;
        }
        itemNode lastNode = sentinel.prev;
        itemNode secondLastNode = lastNode.prev;
        if (currentPos == lastNode) {
            currentPos = secondLastNode;
        }
        secondLastNode.next = sentinel;
        sentinel.prev = secondLastNode;
        size -= 1;
        if (lineWidth != 0 ) {
            lineWidth -= lastNode.getCharWidth();
        }
        return lastNode;
    }

    public int size() {
        return size;
    }
    public double getLineWidth() {return lineWidth;}
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else if (size > 0) {
            return false;
        } else {
            System.out.println("size is less than 0, something is wrong");
            return false;
        }
    }

    public itemNode getCurrentPos() {
        if (size() == 0) {
            return null;
        } else if (currentPos == sentinel) {
            System.out.println("the current postion is on  the very begining of this line");
            return null;
        }
        return currentPos;
    }

    public boolean isCurrentPosAtFront() {
        if (currentPos == sentinel) {
            return true;
        }
        return false;
    }

    public boolean isAtFront(itemNode item) {
        if (item == sentinel) {
            return true;
        }
        return false;
    }

    public boolean isCurrentPosAtEnd() {
        if (currentPos.next == sentinel) {
            return true;
        }
        return false;
    }

    public boolean isAtEnd(itemNode item) {
        if (item.next == sentinel) {
            return true;
        }
        return false;
    }

    public itemNode getEndNode() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.prev;
    }

    public void resetCurrentPosToFront() {
        currentPos = sentinel;
        return;
    }

    public void resetCurrentPosToEnd() {
        if ( !isEmpty()) {
            currentPos = sentinel.prev;
        }
    }

    public itemNode moveForward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos.next == sentinel) {
            return null;
        } else {
            currentPos = currentPos.next;
            return currentPos;
        }
    }

    public itemNode moveBackward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos == sentinel) {
            return null;
        } else {
            itemNode oldCurrentPos = currentPos;
            currentPos = currentPos.prev;
            return oldCurrentPos;
        }
    }

    public void printList() {
        if (isEmpty()) {
            System.out.println(' ');
            return;
        }
        itemNode printNode = sentinel.next;
        String printed = "";
        while (printNode != sentinel) {
            printed += printNode.item;
            printNode = printNode.next;
        }
        System.out.println(printed);
    }

    @Override
    public String toString() {
        String string = "";
        if (isEmpty()) {
            return string;
        }
        itemNode printNode = sentinel.next;
        if (printNode.item instanceof Character) {
            while (printNode != sentinel) {
                string += printNode.item;
                printNode = printNode.next;
            }
            return string;
        } else if (printNode.item instanceof fastLinkList) {
            while (printNode != sentinel) {
                string += printNode.item.toString();
                string += '\n';
                printNode = printNode.next;
            }
            return string;
        } else {
            System.out.println("Undefined fastlinklist type for toString");
            return null;
        }
    }


}