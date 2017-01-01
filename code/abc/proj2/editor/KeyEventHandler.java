

// import apple.security.KeychainStore;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import java.lang.StringBuilder;
import java.security.Key;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/** An EventHandler to handle keys that get pressed. */
public class KeyEventHandler implements EventHandler<KeyEvent> {

    int textTopLeftX;
    int textTopLeftY;

    /** The Text to display on the screen. */
    private  Group root;
    private Text displayText;
    private TextBuffer textBuffer;
    private final Rectangle cursorLine;
    private String outputFilename;
    private ScrollBar scrollBar;
    private OpStack undoStack;
    private OpStack redoStack;

    KeyEventHandler(final Group rt, TextBuffer text, Rectangle cl, String fn, Text disText, ScrollBar sb, OpStack undo, OpStack redo) {
        root = rt;
        textBuffer = text;
        cursorLine = cl;
        outputFilename = fn;
        displayText = disText;
        scrollBar = sb;
        undoStack = undo;
        redoStack = redo;
        textTopLeftX = 0;
        textTopLeftY = 0;

        // Initialize some empty text and add it to root so that it will be displayed.
//            displayText = new Text(textCenterX, textCenterY, "");
        /*
        displayText = new Text(textTopLeftX, textTopLeftY, "");
        // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
        // that when the text is assigned a y-position, that position corresponds to the
        // highest position across all letters (for example, the top of a letter like "I", as
        // opposed to the top of a letter like "e"), which makes calculating positions much
        // simpler!
        displayText.setTextOrigin(VPos.TOP);
        displayText.setFont(Font.font(fontName, fontSize));
        */

        // All new Nodes need to be added to the root in order to be displayed.
        // root.getChildren().add(displayText);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        // System.out.println(keyEvent);
        if (keyEvent.isShortcutDown()) {
            KeyCode keyCode =keyEvent.getCode();
            if (keyCode == KeyCode.S) {
                //System.out.println("Conl + \"s\" detected, saving files.");
                textBuffer.writeBufferIntoFile(outputFilename);
            } else if (keyCode == KeyCode.P) {
                System.out.println("Current pos of X: "+textBuffer.getCurrentX()+ "and Y: " + textBuffer.getCurrentY());
            } else if (keyCode == KeyCode.Z) {
                // user requests undo
                if (!undoStack.isEmpty()) {
                    OpNode op = undoStack.pop();
                    // applyOpNode(op);
                    redoStack.push(applyOpNode(op));
                }
            } else if (keyCode == KeyCode.Y) {
                if (!redoStack.isEmpty()) {
                    OpNode op = redoStack.pop();
                    // applyOpNode(op);
                    undoStack.push(applyOpNode(op));
                }
            } else if (keyCode == KeyCode.PLUS || keyCode == KeyCode.EQUALS) {
                 // we shall increase the font size
                Editor.fontSize += 4;
                Editor.reformatText(Editor.USEABLE_SCREEN, Editor.fontName, Editor.fontSize);
            } else if (keyCode == KeyCode.MINUS) {
                if (Editor.fontSize > 4) {
                    Editor.fontSize -= 4;
                    Editor.reformatText(Editor.USEABLE_SCREEN, Editor.fontName, Editor.fontSize);
                }
            }
        } else if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
            // System.out.println("typed");
            // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
            // the KEY_TYPED event, javafx handles the "Shift" key and associated
            // capitalization.
            String characterTyped = keyEvent.getCharacter();
            if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                // Ignore control keys, which have non-zero length, as well as the backspace
                // key, which is represented as a character of value = 8 on Windows.
                // it is just a single character string.
                textBuffer.insert(characterTyped.charAt(0));
                undoStack.push(new OpNode('i', characterTyped.charAt(0), textBuffer.getCurrentX(), textBuffer.getCurrentY()));
                redoStack.clearStack();
                // increment the max value of the scroll bar is necessary
                if (textBuffer.getBufferLength() - Editor.WINDOW_HEIGHT  > scrollBar.getMax()) {
                    // System.out.println("The max is changed.");
                    scrollBar.setMax(textBuffer.getBufferLength() - Editor.WINDOW_HEIGHT);
                }
                // move the root layout if necessary
                // System.out.println(textBuffer.getCurrentY());
                // System.out.println(Editor.window_offset);
                if (textBuffer.getCurrentY() < Editor.window_offset) {
                    // the cursor is not visable and above the window
                    // System.out.println("We are here.");
                    scrollBar.setValue(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                    root.setLayoutY(-Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                    Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);

                } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                    scrollBar.setValue(textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
                    root.setLayoutY(- textBuffer.getCurrentY() + Editor.WINDOW_HEIGHT);
                    Editor.window_offset = textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT;
                }

                displayText.setText(textBuffer.toString());
                leftTopText();
                //System.out.println(scrollBar.getValue());
                // keyEvent.consume();
            }

//
        } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
            // events have a code that we can check (KEY_TYPED events don't have an associated
            // KeyCode).
            // System.out.println("pressed");
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.UP) {
                /** disable this feature for now
                 fontSize += 5;
                 displayText.setFont(Font.font(fontName, fontSize));
                 centerText();
                 */
            } else if (code == KeyCode.DOWN) {
                /** disable this feature for now
                 fontSize = Math.max(0, fontSize - 5);
                 displayText.setFont(Font.font(fontName, fontSize));
                 //                    centerText();
                 leftTopText();
                 */
            } else if (code == KeyCode.LEFT) {
                textBuffer.moveBackward();
                // move the scrollbar is necessary
                if (textBuffer.getCurrentY() < Editor.window_offset) {
                    // the cursor is not visable and above the window
                    root.setLayoutY(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                    Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
                } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                    root.setLayoutY(textBuffer.getBufferLength());
                    Editor.window_offset = textBuffer.getBufferLength();
                }

                // display the contents
                displayText.setText(textBuffer.toString());
                leftTopText();

            } else if (code == KeyCode.RIGHT) {
                textBuffer.moveForward();
                // move the scrollbar is necessary
                if (textBuffer.getCurrentY() < Editor.window_offset) {
                    // the cursor is not visable and above the window
                    root.setLayoutY(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                    Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
                } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                    root.setLayoutY(textBuffer.getBufferLength());
                    Editor.window_offset = textBuffer.getBufferLength();
                }

                // display the contents
                displayText.setText(textBuffer.toString());
                leftTopText();

            } else if (code == KeyCode.BACK_SPACE) {
                // System.out.println("back space detected!");
                Character deleted = textBuffer.delete();
                if (deleted != null) {
                    undoStack.push(new OpNode('d', deleted, textBuffer.getCurrentX(), textBuffer.getCurrentY()));
                }
                // move the scrollbar is necessary
                if (textBuffer.getCurrentY() < Editor.window_offset) {
                    // the cursor is not visable and above the window
                    root.setLayoutY(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                    Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
                } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                    root.setLayoutY(textBuffer.getBufferLength());
                    Editor.window_offset = textBuffer.getBufferLength();
                }

                // display the contents
                displayText.setText(textBuffer.toString());
                leftTopText();
            }

        }
    }

    public void updateTextBuffer(TextBuffer tb) {
        textBuffer = tb;
    }

    public void leftTopText() {

        // Re-position the text.
        displayText.setX(0);
        displayText.setY(0);

        // set the width and height for the cursor line
        cursorLine.setHeight(textBuffer.getLineHeight());
        cursorLine.setWidth(1);

        // For rectangles, the position is the upper left hand corner.
        cursorLine.setX(textBuffer.getCurrentX());
        cursorLine.setY(textBuffer.getCurrentY() - textBuffer.getLineHeight());

        // Make sure the text appears in front of any other objects you might add.
        displayText.toFront();
    }

    public OpNode applyOpNode(OpNode op) {
        // System.out.println(op.getOperation());
        // System.out.println(op.getContent());
        if (textBuffer.isEmpty()) {
            return null;
        }

        if (op.getOperation() == 'i') {
            // the last op is insert, so we need to delete it here
            textBuffer.moveToMousePos(op.getPosX(), op.getPosY());
            Character deleted = textBuffer.delete();
            // move the scrollbar is necessary
            if (textBuffer.getCurrentY() < Editor.window_offset) {
                // the cursor is not visable and above the window
                root.setLayoutY(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
            } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                root.setLayoutY(textBuffer.getBufferLength());
                Editor.window_offset = textBuffer.getBufferLength();
            }
            // display the contents
            displayText.setText(textBuffer.toString());
            leftTopText();
            return new OpNode('d', deleted, textBuffer.getCurrentX(), textBuffer.getCurrentY());
        } else if (op.getOperation() == 'd') {
            // it was deletion last time, so we need to insert what is deleted
            textBuffer.moveToMousePos(op.getPosX(), op.getPosY());
            textBuffer.insert(op.getContent());
            if (textBuffer.getBufferLength() - Editor.WINDOW_HEIGHT  > scrollBar.getMax()) {
                // System.out.println("The max is changed.");
                scrollBar.setMax(textBuffer.getBufferLength() - Editor.WINDOW_HEIGHT);
            }

            // set up the scrollbar
            if (textBuffer.getCurrentY() < Editor.window_offset) {
                // the cursor is not visable and above the window
                System.out.println("We are here.");
                scrollBar.setValue(Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                root.setLayoutY(-Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT));
                Editor.window_offset = Math.max(0, textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);

            } else if (textBuffer.getCurrentY() > Editor.window_offset + Editor.WINDOW_HEIGHT) {
                scrollBar.setValue(textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT);
                root.setLayoutY(- textBuffer.getCurrentY() + Editor.WINDOW_HEIGHT);
                Editor.window_offset = textBuffer.getCurrentY() - Editor.WINDOW_HEIGHT;
            }

            displayText.setText(textBuffer.toString());
            leftTopText();
            return new OpNode('i', op.getContent(), textBuffer.getCurrentX(), textBuffer.getCurrentY());
        } else {
            System.err.println("You give the wrong opearation command in undo and redostack");
            System.exit(1);
            return null;
        }
    }


}