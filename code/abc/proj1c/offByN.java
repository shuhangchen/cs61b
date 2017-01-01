/**
 * Created by mark on 6/21/16.
 */
public class offByN implements CharacterComparator {
    int offSet;

    public offByN(int n) {
        offSet = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == offSet) {
            return true;
        } else {
            return false;
        }
    }
}
