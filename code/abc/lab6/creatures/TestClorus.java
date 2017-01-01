package creatures;

import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
/**
 * Created by mark on 6/23/16.
 */
public class TestClorus {


    @Test
    public void testClorus() {
        Clorus clorus = new Clorus(1.4);
        assertEquals("clorus",clorus.name());
        clorus.move();
        assertEquals(1.37,clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.36,clorus.energy(), 0.01);
        assertEquals(new Color(34, 0,231),clorus.color());
        Clorus off = clorus.replicate();assertEquals(0.68,clorus.energy(), 0.01);
        assertEquals(0.68,off.energy(), 0.01);
        assertNotSame(clorus, off);
        Plip plip = new Plip(1.0);
        clorus.attack(plip);
        assertEquals(1.68, clorus.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(0.9);
        Plip p = new Plip(1.9);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!

        Action actual = c.chooseAction(surrounded);
//        Action expected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);
//        Action expected = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        Action expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);




    }
    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
