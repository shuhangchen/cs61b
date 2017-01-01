import javax.sound.sampled.Line;

/**
 * Created by mark on 7/24/16.
 */



public class FastLinkListOfLine {

    private LineNode sentinel;
    private int size;
    private LineNode currentPos;

    public FastLinkListOfLine() {
        size = 0;
        sentinel = new LineNode();
        currentPos = sentinel;
    }

    public void insert(FastLinkListOfChar c) {
        if (isEmpty()) {
            LineNode newNode = new LineNode(c, sentinel, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
            currentPos = newNode;
            return;
        }
        LineNode nextElem = currentPos.next;
        LineNode oldCurrentNode = currentPos;
        LineNode newNode = new LineNode(c, oldCurrentNode, nextElem);
        nextElem.prev =newNode;
        oldCurrentNode.next =newNode;
        currentPos = newNode;
        size += 1;
        return;
    }


    public LineNode delete() {
        if (isEmpty() || currentPos == sentinel) {
            return null;
        }
        LineNode prevElem = currentPos.prev;
        LineNode nextElem = currentPos.next;
        LineNode deleted = currentPos;
        prevElem.next = nextElem;
        nextElem.prev = prevElem;
        currentPos = prevElem;
        size -= 1;
        return deleted;
    }

    public LineNode deleteNode(LineNode l) {
        if (isEmpty()) {
            return null;
        }
        LineNode prevElem = l.prev;
        LineNode nextElem = l.next;
        LineNode deleted = l;
        prevElem.next = nextElem;
        nextElem.prev = prevElem;
        size -= 1;
        return deleted;
    }

    public LineNode removeFirst() {
        if (isEmpty()) {
            return null;
        }
        LineNode firstNode = sentinel.next;
        if (currentPos == firstNode) {
            currentPos = sentinel;
        }
        LineNode secondNode = firstNode.next;
        secondNode.prev = sentinel;
        sentinel.next = secondNode;
        size -= 1;
        return firstNode;
    }

    public LineNode removeLast() {
        if (isEmpty()) {
            return null;
        }
        LineNode lastNode = sentinel.prev;
        LineNode secondLastNode = lastNode.prev;
        if (currentPos == lastNode) {
            currentPos = secondLastNode;
        }
        secondLastNode.next = sentinel;
        sentinel.prev = secondLastNode;
        size -= 1;
        return lastNode;
    }

    public int size() {
        return size;
    }
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

    public void resetToEmpty() {
        size = 0;
        sentinel = new LineNode();
        currentPos = sentinel;
    }

    public LineNode getCurrentPos() {
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

    public boolean isAtFront(LineNode item) {
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

    public boolean isAtEnd(LineNode item) {
        if (item.next == sentinel) {
            return true;
        }
        return false;
    }

    public LineNode getSentinel() {
        return sentinel;
    }

    public LineNode getEndNode() {
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

    public LineNode moveForward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos.next == sentinel) {
            return null;
        } else {
            currentPos = currentPos.next;
            return currentPos;
        }
    }

    public LineNode moveBackward() {
        if (isEmpty()) {
            return null;
        } else if (currentPos == sentinel) {
            return null;
        } else {
            LineNode oldCurrentPos = currentPos;
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
        LineNode printNode = sentinel.next;
        while (printNode != sentinel) {
            string += printNode.item.toString();
            if (string.charAt(string.length() - 1) != '\n') {
                string += '\n';
            }
            printNode = printNode.next;
        }
        return string;
    }


}