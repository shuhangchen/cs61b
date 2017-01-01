
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;

public class TextBuffer {

    private FastLinkListOfLine content;
    private double currentX;
    private double currentY;
    private double lineHeight;
    private double windowWidth;
    private double bufferLength;
    private int charCount;
    private Text displayLetter = new Text(0, 0, "");


    public TextBuffer(double ww, String fontName, double fontSize) {
        content = new FastLinkListOfLine();
        currentX = 0;
        currentY = 0;
        lineHeight = 0;
        windowWidth = ww;
        charCount = 0;
        bufferLength = 0;
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
        int oldContentSize = content.size();
        // deal with the change line character first
        if (c == '\r' || c == '\n') {
            if (isEmpty()) {
                FastLinkListOfChar newline = new FastLinkListOfChar ();
                // just randomly pick a to measure the height and y of new line.
                displayLetter.setText("a");
                lineHeight = displayLetter.getLayoutBounds().getHeight();
                currentY = displayLetter.getLayoutBounds().getHeight();
                currentX = 0;
                newline.insert(c, 0, lineHeight);
                content.insert(newline);
                FastLinkListOfChar changeLine = new FastLinkListOfChar ();
                content.insert(changeLine);
                if (content.size() > oldContentSize) {
                    bufferLength += lineHeight;
                }
                charCount += 1;
                return;
            } else {
                LineNode currentLine = content.getCurrentPos();
                // just randomly pick the letter a to setup the height and width
                displayLetter.setText("a");
                currentX = 0;
                currentY += displayLetter.getLayoutBounds().getHeight();
                lineHeight = displayLetter.getLayoutBounds().getHeight();
                currentLine.item.insert(c, 0 , lineHeight);
                charCount += 1;
                if (currentLine.item.isAtEnd(currentLine.item.getCurrentPos())) {
                    FastLinkListOfChar changeLine = new FastLinkListOfChar();
                    content.insert(changeLine);
                    bufferLength += lineHeight;
                    return;
                } else if (content.isAtEnd(currentLine)) {
                    FastLinkListOfChar changeLine = new FastLinkListOfChar();
                    CharNode currentNode= currentLine.item.getCurrentPos();
                    CharNode charAddedTo;
                    while (!currentLine.item.isAtEnd(currentNode)) {
                        charAddedTo = currentLine.item.removeLast();
                        changeLine.addFirst(charAddedTo.item, charAddedTo.getCharWidth());
                    }
                    content.insert(changeLine);
                    if (content.size() > oldContentSize) {
                        bufferLength += lineHeight;
                    }
                    return;
                } else {
                    LineNode nextLine = currentLine.next;
                    CharNode currentNode= currentLine.item.getCurrentPos();
                    CharNode charAddedTo;
                    while (!currentLine.item.isAtEnd(currentNode)) {
                        charAddedTo = currentLine.item.removeLast();
                        nextLine.item.addFirst(charAddedTo.item, charAddedTo.getCharWidth());
                    }

                    currentLine = nextLine;
                    nextLine = currentLine.next;
                    while (!content.isAtEnd(currentLine) && currentLine.item.getLineWidth() > windowWidth && !nextLine.item.isEmpty())  {
                        boolean addedTo = addToNextLine(currentLine, nextLine);
                        if (addedTo) {
                            // no need to further re-arranging
                            currentLine = nextLine;
                            nextLine = currentLine.next;
                        } else {
                            break;
                        }
                    }
                    if (content.size() > oldContentSize) {
                        bufferLength += lineHeight;
                    }
                    return;
                }
            }
        }
        displayLetter.setText(Character.toString(c));
        if (isEmpty()) {
            FastLinkListOfChar newline = new FastLinkListOfChar ();
            lineHeight = displayLetter.getLayoutBounds().getHeight();
            currentY = displayLetter.getLayoutBounds().getHeight();
            currentX = displayLetter.getLayoutBounds().getWidth();
            newline.insert(c, displayLetter.getLayoutBounds().getWidth(), lineHeight);
            content.insert(newline);
            charCount += 1;
            if (content.size() > oldContentSize) {
                bufferLength += lineHeight;
            }
            return;
        }
        if (currentX + displayLetter.getLayoutBounds().getWidth() < windowWidth) {
            /* we can still insert the character into this current line */
            LineNode currentLine = content.getCurrentPos();
            currentX += displayLetter.getLayoutBounds().getWidth();
            currentLine.item.insert(c, displayLetter.getLayoutBounds().getWidth(), lineHeight);
            charCount += 1;

            // need to re-formatting the following.
            LineNode nextLine = currentLine.next;
            while (!content.isAtEnd(currentLine) && currentLine.item.getLineWidth() > windowWidth && !nextLine.item.isEmpty())  {
                boolean addedTo = addToNextLine(currentLine, nextLine);
                if (addedTo) {
                    // no need to further re-arranging
                    currentLine = nextLine;
                    nextLine = currentLine.next;
                } else {
                    break;
                }
            }
            if (content.size() > oldContentSize) {
                bufferLength += lineHeight;
            }
            return;
        } else {
            /* need to start a new line  */
            LineNode currentLine = content.getCurrentPos();
            if (content.isAtEnd(currentLine)) {
                // we are at the end of the buffer
                FastLinkListOfChar newline = new FastLinkListOfChar();
                lineHeight = displayLetter.getLayoutBounds().getHeight();
                currentY += displayLetter.getLayoutBounds().getHeight();
                currentX = displayLetter.getLayoutBounds().getWidth();
                newline.insert(c, displayLetter.getLayoutBounds().getWidth(), lineHeight);
                content.insert(newline);
                charCount += 1;
                if (content.size() > oldContentSize) {
                    bufferLength += lineHeight;
                }
                return;
            } else {
                // we are in the middle of the text buffer now.
                LineNode nextLine = currentLine.next;
                currentY += displayLetter.getLayoutBounds().getHeight();
                currentX = displayLetter.getLayoutBounds().getWidth();
                charCount += 1;

                // nextline is now the current line.
                content.moveForward();
                nextLine.item.addFirst(c, displayLetter.getLayoutBounds().getWidth());
                nextLine.item.resetCurrentPosToFront();
                nextLine.item.moveForward();
                currentLine = nextLine;
                nextLine = currentLine.next;
                while (!content.isAtEnd(currentLine) && currentLine.item.getLineWidth() > windowWidth && !nextLine.item.isEmpty())  {
                    boolean addedTo = addToNextLine(currentLine, nextLine);
                    if (addedTo) {
                        // no need to further re-arranging
                        currentLine = nextLine;
                        nextLine = currentLine.next;
                    } else {
                        break;
                    }
                }
                if (content.size() > oldContentSize) {
                    bufferLength += lineHeight;
                }
                return;
            }
        }
    }

    public boolean addToNextLine(LineNode currentLine, LineNode nextLine) {
        boolean addedWidth = false;
        double lineWidth = currentLine.item.getLineWidth();
        if (currentLine.item.getEndNode().item == '\n') {
            // System.out.println("we detected the end node are \n");
            FastLinkListOfChar insertedChars = new FastLinkListOfChar();
            LineNode insertedLine = new LineNode(insertedChars, currentLine, nextLine);
            currentLine.next =insertedLine;
            nextLine.prev = insertedLine;
            nextLine = insertedLine;
        }
        CharNode charAddedTo;
        double charAddedToWidth;
        while (lineWidth > windowWidth) {
            charAddedTo = currentLine.item.removeLast();
            charAddedToWidth = charAddedTo.getCharWidth();
            nextLine.item.addFirst(charAddedTo.item, charAddedToWidth);
            lineWidth = currentLine.item.getLineWidth();
            addedWidth = true;
        }
        return addedWidth;
    }

    public Character delete() {
        if (isEmpty()) {
            content.resetToEmpty();
            return null;
        }
        int oldContentSize = content.size();
        LineNode currentLine = content.getCurrentPos();
        if (currentLine.item.isCurrentPosAtFront()) {

           // now we can be sure, it is not empty, so there is prevline
            // except it is at the beginning, but there is contents after it.
            if (content.isAtFront(currentLine.prev)) {
                return null;
            }
            LineNode prevLine = currentLine.prev;
            CharNode deleted = prevLine.item.removeLast();
            prevLine.item.resetCurrentPosToEnd();
            content.moveBackward();
            charCount -= 1;
            currentY -= lineHeight;
            currentX = prevLine.item.getLineWidth();
            if (currentLine.item.size() == 0) {
                content.deleteNode(currentLine);
                if (content.size() < oldContentSize) {
                    bufferLength -= lineHeight;
                }
                return deleted.item;
            }
            while (!content.isAtEnd(prevLine) && prevLine.item.getEndNode().item != '\n' && !currentLine.item.isEmpty())  {
                boolean addedTo = addToCurrentLine(prevLine, currentLine);
                if (addedTo) {
                    // no need to further re-arranging
                    prevLine = currentLine;
                    currentLine = currentLine.next;
                } else {
                    break;
                }
            }
            if (content.size() < oldContentSize) {
                bufferLength -= lineHeight;
            }
            return deleted.item;
        } else {
            // we stay in this line
            if (content.isCurrentPosAtEnd()) {
                CharNode deleted = currentLine.item.delete();
                currentX -= deleted.getCharWidth();
                charCount -=1;
                return deleted.item;
            } else {
                LineNode nextLine = currentLine.next;
                CharNode deleted = currentLine.item.delete();
                double letterWidth = deleted.getCharWidth();
                currentX -= letterWidth;
                charCount -=1;
                content.isAtEnd(currentLine);
                Character hhh = currentLine.item.getEndNode().item;
                nextLine.item.isEmpty();
                while (!content.isAtEnd(currentLine) && currentLine.item.getEndNode().item != '\n' && !nextLine.item.isEmpty())  {
                    boolean addedTo = addToCurrentLine(currentLine, nextLine);
                    if (addedTo) {
                        // no need to further re-arranging
                        currentLine = nextLine;
                        nextLine = currentLine.next;
                    } else {
                        break;
                    }
                }
                if (content.size() < oldContentSize) {
                    bufferLength -= lineHeight;
                }
                return deleted.item;
            }

        }
    }

    public boolean addToCurrentLine(LineNode currentLine, LineNode nextLine) {
        boolean addedWidth = false;
        boolean nullDetected = false;
        double lineWidth = currentLine.item.getLineWidth();
        CharNode charAddedTo = nextLine.item.removeFirst();
        double charAddedToWidth = charAddedTo.getCharWidth();
        while (lineWidth + charAddedToWidth < windowWidth) {
            currentLine.item.addLast(charAddedTo.item, charAddedToWidth);
            lineWidth = currentLine.item.getLineWidth();
            addedWidth = true;
            charAddedTo = nextLine.item.removeFirst();
            if (charAddedTo == null) {
                content.deleteNode(nextLine);
                nullDetected = true;
                break;
            }
            charAddedToWidth = charAddedTo.getCharWidth();
        }
        if (!nullDetected) {
            nextLine.item.addFirst(charAddedTo.item, charAddedToWidth);
        }
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

    public double getBufferLength() {return bufferLength;}

    public double moveBackward() {
        if (isEmpty()) {
            return 0;
        }
        FastLinkListOfChar currentLine = content.getCurrentPos().item;
        if (currentLine.isCurrentPosAtFront()) {
            // only one line left
            if (content.size() == 1) {
                // we do nothing.
                return 0;
            }
            content.moveBackward();
            FastLinkListOfChar prevLine = content.getCurrentPos().item;
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
        FastLinkListOfChar currentLine = content.getCurrentPos().item;
        if (currentLine.isCurrentPosAtEnd()) {
            // test if this is the last line of the content
            if (content.isCurrentPosAtEnd()) {
                // we do nothing
                return 0;
            }
            FastLinkListOfChar nextLine = content.moveForward().item;
            currentY += lineHeight;
            currentX = 0;
            return 0;
        } else {
            double movedDistance = currentLine.moveForward().getCharWidth();
            currentX += movedDistance;
            return movedDistance;
        }
    }

    public void writeBufferIntoFile(String fn) {
        if (content.isEmpty()) {
            return;
        }
        try {
            FileWriter writer = new FileWriter(fn);
            BufferedWriter bw = new BufferedWriter(writer);

            LineNode lineNode = content.getSentinel();
            for (int i = 0; i < content.size(); i += 1) {
                lineNode = lineNode.next;
                bw.write(lineNode.item.toString());
            }

            bw.close();
        } catch (IOException ioException) {
            System.out.println("Error when copying; exception was: " + ioException);
        }

        return;
    }

    public void moveToMousePos(double x, double y) {
        double height = content.size() * lineHeight;
        if (y > height) {
            y = height;
        }
        if (y > currentY) {
            // need to move forward
            while (y > currentY) {
                content.moveForward();
                currentY += lineHeight;
            }

            // move x cursor only
            moveCurrentXToMousePos(x);
        }
        else if (y < currentY) {
            // need to move backward
            while (y < currentY - lineHeight) {
                content.moveBackward();
                currentY -= lineHeight;
            }
            // need to move x cursor only
            moveCurrentXToMousePos(x);
        } else {
            // y == currentY

            // just move x cursor only
            moveCurrentXToMousePos(x);
        }
        // System.out.println(currentY);
    }

    public void moveCurrentXToMousePos(double x) {
        LineNode currentLine = content.getCurrentPos();
        double width = currentLine.item.getLineWidth();
        if (x > width) {
            x = width;
        }

        currentX = 0;   // start from the beginning of this line
        currentLine.item.resetCurrentPosToFront();  // it is the sentinel node

        if (x ==0 ) {
            return;
        }
        if (width == 0) {
            // there is only a '\n' in this line
            return;
        }
        while (x > currentX) {
            currentLine.item.moveForward();
            currentX += currentLine.item.getCurrentPos().getCharWidth();
        }
        //System.out.println(x);
        //System.out.println(currentX);
        double halfCharWidth =0.5 * currentLine.item.getCurrentPos().getCharWidth();
        if (currentX - halfCharWidth > x) {
            // need to movebackward
            currentLine.item.moveBackward();
            currentX -= halfCharWidth * 2;
        }
        return;
    }

    public FastLinkListOfLine getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }



}