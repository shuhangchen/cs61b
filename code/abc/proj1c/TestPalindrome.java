/**
 * Created by mark on 6/21/16.
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class TestPalindrome {

    @Test
    public void testWordToDeque(){
        String test = "abcba";
        Deque<Character> testDeque = Palindrome.wordToDeque(test);
        testDeque.printDeque();
        assertTrue('a' == testDeque.removeLast());
        assertTrue(Palindrome.isPalindrome(test));
        String test2 = "abc";
        assertFalse(Palindrome.isPalindrome(test2));
    }

    @Test
    public void palindromComparator() {
        String test = "flake";
        Deque<Character> testDeque = Palindrome.wordToDeque(test);
        offByOne comp = new offByOne();
        assertTrue(comp.equalChars('f', 'e'));
        assertTrue(Palindrome.isPalindrome(test, comp));
        String test2 = "abcba";
        assertFalse(Palindrome.isPalindrome(test2, comp));
    }

    @Test
    public void palindromComparatorOffByN() {
        String test = "flake";
        Deque<Character> testDeque = Palindrome.wordToDeque(test);
        offByN comp = new offByN(1);
        assertTrue(comp.equalChars('f', 'e'));
        assertTrue(Palindrome.isPalindrome(test, comp));
        String test2 = "abcba";
        assertFalse(Palindrome.isPalindrome(test2, comp));
    }
}
