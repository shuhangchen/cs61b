/**
 * Created by shuhang on 8/18/16.
 */
public class OpNode {
    Character operation;  // op = i means insert, d means delete
    Character content;
    double posX;
    double posY;

    public OpNode(Character op, Character c, double x, double y) {
        if (op == 'i') {
            // it is insertion
            operation = 'i';
            content = c;
        } else if (op == 'd') {
            // it is deleting
            operation = 'd';
            content = c;
        } else {
            System.err.println(" The operation node is un-recognizable");
            System.exit(1);
        }
        posX = x;
        posY = y;
    }

    public Character getOperation() {
        return operation;
    }

    public Character getContent() {
        return content;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
