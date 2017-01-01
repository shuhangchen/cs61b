/**
 * Created by mark on 6/21/16.
 */
public class Palindrome {

    public static Deque<Character> wordToDeque(String word) {
        Deque deque = new LinkedListDequeSolution();
        Character c;
        for (int i=0; i< word.length(); i+= 1) {
            c = word.charAt(i);
            deque.addLast(c);
        }
        return deque;
    }

    public static boolean isPalindrome(String word) {
        LinkedListDequeSolution<Character> deque = (LinkedListDequeSolution<Character>) Palindrome.wordToDeque(word);
        return dequeIsPalindrome(deque);

    }

    public static boolean dequeIsPalindrome(Deque<Character> dequeOfWord) {
        if (dequeOfWord.size() == 1 ) {
            return true;
        } else if (dequeOfWord.size() == 0 )  {
            return true;
        }
        else {
            Character front =(Character) dequeOfWord.removeFirst();
            Character end = (Character) dequeOfWord.removeLast();
            if (front==end) {
                return dequeIsPalindrome(dequeOfWord);
            } else {
                return false;
            }

        }

    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        LinkedListDequeSolution<Character> deque = (LinkedListDequeSolution<Character>) Palindrome.wordToDeque(word);
        return dequeIsPalindrome(deque, cc);
    }

    public static boolean dequeIsPalindrome(Deque<Character> dequeOfWord, CharacterComparator cc) {
        if (dequeOfWord.size() == 1 ) {
            return true;
        } else if (dequeOfWord.size() == 0 )  {
            return true;
        }
        else {
            Character front =(Character) dequeOfWord.removeFirst();
            Character end = (Character) dequeOfWord.removeLast();
            if (cc.equalChars(front, end)) {
                return dequeIsPalindrome(dequeOfWord, cc);
            } else {

                return false;
            }

        }

    }
}
