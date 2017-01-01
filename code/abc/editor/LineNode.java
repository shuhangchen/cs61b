
public class LineNode{
    public FastLinkListOfChar item;
    public LineNode next;
    public LineNode prev;
    public LineNode() {
        next = this;
        prev = this;
    }

    public LineNode(FastLinkListOfChar c, LineNode p, LineNode n) {
        item = c;
        next = n;
        prev = p;
    }

}