
public class FastLinkListOfChar {

    private CharNode sentinel;
    private int size;
    private double lineWidth;
    private double lineHeight;
    private CharNode currentPos;

    public FastLinkListOfChar() {
        size = 0;
        lineWidth = 0;
        lineHeight = 0;
        sentinel = new CharNode();
        currentPos = sentinel;
    }


    public void insert(Character c, double letterLength, double height) {
        if (c == null) {
            return;
        }
        if (isEmpty()) {
            CharNode newNode = new CharNode(c, sentinel, sentinel, letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            lineHeight = height;
            currentPos = newNode;
            return;
        }
        CharNode nextElem = currentPos.next;
        CharNode oldCurrentNode = currentPos;
        CharNode newNode = new CharNode(c, oldCurrentNode, nextElem, letterLength);
        nextElem.prev = newNode;
        oldCurrentNode.next = newNode;
        currentPos = newNode;
        size += 1;
        lineWidth += letterLength;
        return;
    }

    public CharNode delete() {
        if (isEmpty() || currentPos == sentinel) {
            return null;
        }
        CharNode prevElem = currentPos.prev;
        CharNode nextElem = currentPos.next;
        CharNode deleted = currentPos;
        prevElem.next = nextElem;
        nextElem.prev = prevElem;
        currentPos = prevElem;
        size -= 1;
        lineWidth -= deleted.getCharWidth();
        return deleted;
    }

    public void addFirst(Character item, double letterLength) {
        if (item == null) {
            return;
        }
        if (isEmpty()) {
            CharNode newNode = new CharNode(item, sentinel, sentinel, letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            return;
        }
        CharNode oldFirstCharNode = sentinel.next;
        CharNode newNode = new CharNode(item, sentinel, oldFirstCharNode, letterLength);
        sentinel.next = newNode;
        oldFirstCharNode.prev = newNode;
        size += 1;
        lineWidth += letterLength;
        return;
    }

    public void addLast(Character item, double letterLength) {
        if (item == null) {
            return;
        }
        if (isEmpty()) {
            CharNode newNode = new CharNode(item, sentinel, sentinel, letterLength);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            lineWidth += letterLength;
            return;
        }
        CharNode oldLastNode = sentinel.prev;
        CharNode newNode = new CharNode(item, oldLastNode, sentinel, letterLength);
        sentinel.prev = newNode;
        oldLastNode.next = newNode;
        size += 1;
        lineWidth += letterLength;
        return;
    }

    public CharNode removeFirst() {
        if (isEmpty()) {
            return null;
        }
        CharNode firstNode = sentinel.next;
        if (currentPos == firstNode) {
            currentPos = sentinel;
        }
        CharNode secondNode = firstNode.next;
        secondNode.prev = sentinel;
        sentinel.next = secondNode;
        size -= 1;
        lineWidth -= firstNode.getCharWidth();
        return firstNode;
    }

    public CharNode removeLast() {
        if (isEmpty()) {
            return null;
        }
        CharNode lastNode = sentinel.prev;
        CharNode secondLastNode = lastNode.prev;
        if (currentPos == lastNode) {
            currentPos = secondLastNode;
        }
        secondLastNode.next = sentinel;
        sentinel.prev = secondLastNode;
        size -= 1;
        lineWidth -= lastNode.getCharWidth();
        return lastNode;
    }

    public int size() {
        return size;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public double getLineHeight() { return lineHeight; }

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

    public CharNode getCurrentPos() {
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

    public boolean isAtFront(CharNode item) {
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

    public boolean isAtEnd(CharNode item) {
        if (item.next == sentinel) {
            return true;
        }
        return false;
    }

    public CharNode getEndNode() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.prev;
    }

    public CharNode getSentinel() {
        return sentinel;
    }

    public void resetCurrentPosToFront() {
        currentPos = sentinel;
        return;
    }

    public void resetCurrentPosToEnd() {
        if (!isEmpty()) {
            currentPos = sentinel.prev;
        }
    }

    public CharNode moveForward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos.next == sentinel) {
            return null;
        } else {
            currentPos = currentPos.next;
            return currentPos;
        }
    }

    public CharNode moveBackward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos == sentinel) {
            return null;
        } else {
            CharNode oldCurrentPos = currentPos;
            currentPos = currentPos.prev;
            return oldCurrentPos;
        }
    }

    @Override
    public String toString() {
        String string = "";
        if (isEmpty()) {
            return string;
        }
        CharNode printNode = sentinel.next;
        while (printNode != sentinel) {
            string += printNode.item;
            printNode = printNode.next;
        }
        return string;
    }

}