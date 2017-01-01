
public class CharNode{
    public Character item;
    public CharNode next;
    public CharNode prev;
    private double charWidth;
    public CharNode() {
        next = this;
        prev = this;
    }

    public CharNode(Character c, CharNode p, CharNode n, double cw) {
        item = c;
        next = n;
        prev = p;
        charWidth = cw;
    }

    public double getCharWidth() {
        return charWidth;
    }

}