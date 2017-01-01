/**
 * Created by mark on 6/21/16.
 */
public class offByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y){
        if (Math.abs(x - y) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
