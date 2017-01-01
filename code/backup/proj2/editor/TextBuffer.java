package editor;

import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBuffer {

    private fastLinkList<fastLinkList<Character>> content;
    private double currentX;
    private double currentY;
    private double lineHeight;
    private double windowWidth;
    private int charCount;
    private Text displayLetter = new Text(0, 0, "");


    public TextBuffer(double ww, String fontName, double fontSize) {
        content = new fastLinkList<fastLinkList<Character>>();
        currentX = 0;
        currentY = 0;
        lineHeight = 0;
        windowWidth = ww;
        charCount = 0;
        /* use the following to compute the width and height of the inserted character **/
        displayLetter.setTextOrigin(VPos.TOP);
        displayLetter.setFont(Font.font(fontName, fontSize));
    }

    public boolean isEmpty() {
        if (charCount == 0) {
            return true;
        }
        return false;
    }

    public void insert(char c) {

        // deal with the change line character first
        if (c == '\r') {
            if (isEmpty()) {
                fastLinkList<Character> newline = new fastLinkList<Character> ();
                // just randomly pick a to measure the height and y of new line.
                displayLetter.setText("a");
                lineHeight = displayLetter.getLayoutBounds().getHeight();
                currentY = displayLetter.getLayoutBounds().getHeight();
                currentX = 0;
                newline.insert(c, 0);
                content.insert(newline);
                fastLinkList<Character> changeLine = new fastLinkList<Character> ();
                content.insert(changeLine);
                charCount += 1;
                return;
            } else {
                fastLinkList<Character> currentLine = content.getCurrentPos().item;
                // just randomly pick the letter a to setup the height and width
                currentLine.insert(c, 0);
                displayLetter.setText("a");
                currentX = 0;
                currentY += displayLetter.getLayoutBounds().getHeight();
                lineHeight = displayLetter.getLayoutBounds().getHeight();
                fastLinkList<Character> changeLine = new fastLinkList<Character> ();
                content.insert(changeLine);
                charCount += 1;
                return;
            }
        }
        displayLetter.setText(Character.toString(c));
        if (isEmpty()) {
            fastLinkList<Character> newline = new fastLinkList<Character> ();
            lineHeight = displayLetter.getLayoutBounds().getHeight();
            currentY = displayLetter.getLayoutBounds().getHeight();
            currentX = displayLetter.getLayoutBounds().getWidth();
            newline.insert(c, displayLetter.getLayoutBounds().getWidth());
            content.insert(newline);
            charCount += 1;
            return;
        }
        if (currentX + displayLetter.getLayoutBounds().getWidth() < windowWidth) {
            /* we can still insert the character into this current line */
            fastLinkList<Character> currentLine = content.getCurrentPos().item;
            currentX += displayLetter.getLayoutBounds().getWidth();
            currentLine.insert(c, displayLetter.getLayoutBounds().getWidth());
            charCount += 1;
            return;
        } else {
            /* need to start a new line  */
            fastLinkList<Character> newline = new fastLinkList<Character> ();
            lineHeight = displayLetter.getLayoutBounds().getHeight();
            currentY += displayLetter.getLayoutBounds().getHeight();
            currentX = displayLetter.getLayoutBounds().getWidth();
            newline.insert(c, displayLetter.getLayoutBounds().getWidth());
            content.insert(newline);
            charCount += 1;
            return;
        }
    }

    public Character delete() {
        if (isEmpty()) {
            return null;
        }
        fastLinkList<Character> currentLine = content.getCurrentPos();
        if (currentLine.isCurrentPosAtFront()) {

           // now we can be sure, it is not empty, so there is prevline

            if (content.isAtFront(currentLine.prev)) {
                return null;
            }
            fastLinkList<Character> prevLine = currentLine.prev;
            prevLine.removeLast();
            charCount -= 1;
            currentY -= lineHeight;
            currentX = prevLine.getLineWidth();
            while (!content.isAtEnd(currentLine) || currentLine.getEndNode().item != '\n')  {
                boolean addedTo = addToCurrentLine(prevLine, currentLine);
                if (addedTo) {
                    // no need to further re-arranging
                    prevLine = currentLine;
                    currentLine = currentLine.next;
                } else {
                    break;
                }
            }
        } else {
            // we stay in the line
            if (content.isCurrentPosAtEnd()) {
                itemNode<Character> deleted = currentLine.delete();
                currentX -= deleted.getCharWidth();
                charCount -=1;
                return deleted;
            } else {
                fastLinkList<Character> nextLine = currentLine.next;
                itemNode<Character> deleted = currentLine.delete();
                double letterWidth = deleted.getCharWidth();
                currentX -= letterWidth;
                charCount -=1;
                while (!content.isAtEnd(currentLine) || currentLine.getEndNode().item != '\n')  {
                    boolean addedTo = addToCurrentLine(currentLine, nextLine);
                    if (addedTo) {
                        // no need to further re-arranging
                        currentLine = nextLine;
                        nextLine = currentLine.next;
                    } else {
                        break;
                    }
                }
            }

        }
    }

    public boolean addToCurrentLine(fastLinkList<Character> currentLine, fastLinkList<Character> nextLine) {
        boolean addedWidth = false;
        double lineWidth = currentLine.getLineWidth();
        itemNode<Character> charAddedTo = nextLine.removeFirst();
        double charAddedToWidth = charAddedTo.getCharWidth();
        while (lineWidth + charAddedToWidth < windowWidth) {
            currentLine.addLast(charAddedTo.item, charAddedToWidth);
            lineWidth = currentLine.getLineWidth();
            addedWidth = true;
            nextLine.removeFirst();
            charAddedToWidth = charAddedTo.getCharWidth();
        }
        nextLine.addFirst(charAddedTo.item, charAddedToWidth);
        return addedWidth;
    }

    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public double getLineHeight() {
        return lineHeight;
    }

    public double moveBackward() {
        if (isEmpty()) {
            return 0;
        }
        fastLinkList<Character> currentLine = content.getCurrentPos().item;
        if (currentLine.isCurrentPosAtFront()) {
            // only one line left
            if (content.size() == 1) {
                // we do nothing.
                return 0;
            }
            content.moveBackward();
            fastLinkList<Character> prevLine = content.getCurrentPos().item;
            currentY -= lineHeight;
            displayLetter.setText(prevLine.toString());
            currentX = displayLetter.getLayoutBounds().getWidth();
            return 0;
        } else {
            double movedDistance = currentLine.moveBackward().getCharWidth();
            currentX -= movedDistance;
            return movedDistance;
        }
    }

    public double moveForward() {
        if (isEmpty()) {
            return 0;
        }
        fastLinkList<Character> currentLine = content.getCurrentPos().item;
        if (currentLine.isCurrentPosAtEnd()) {
            // test if this is the last line of the content
            if (content.isCurrentPosAtEnd()) {
                // we do nothing
                return 0;
            }
            fastLinkList<Character> nextLine = content.moveForward().item;
            currentY += lineHeight;
            currentX = 0;
            return 0;
        } else {
            double movedDistance = currentLine.moveForward().getCharWidth();
            currentX += movedDistance;
            return movedDistance;
        }
    }

    @Override
    public String toString() {
        return content.toString();
    }



}