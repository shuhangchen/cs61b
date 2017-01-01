/**
 * Created by mark on 8/6/16.
 */

import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MouseClickEventHandler implements EventHandler<MouseEvent> {
    /** A Text object that will be used to print the current mouse position. */

    private TextBuffer textBuffer;
    private final Rectangle cursorLine;
    private Text displayText;

    double mousePressedX;
    double mousePressedY;
    MouseClickEventHandler(Group root, TextBuffer tb, Rectangle cl, Text disText) {
        textBuffer = tb;
        cursorLine = cl;
        displayText = disText;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        // Because we registered this EventHandler using setOnMouseClicked, it will only called
        // with mouse events of type MouseEvent.MOUSE_CLICKED.  A mouse clicked event is
        // generated anytime the mouse is pressed and released on the same JavaFX node.
        mousePressedX = mouseEvent.getX();
        mousePressedY = mouseEvent.getY();

        // move the currentX and currentY to the closet char.

        textBuffer.moveToMousePos(mousePressedX, mousePressedY + Editor.window_offset);

        // re-show the cursor line and the contents
        showCursorAndText();
    }

    public void showCursorAndText() {
        // Re-position the text.
        /*
        displayText.setX(0);
        displayText.setY(0);
        */
        // set the width and height for the cursor line
        cursorLine.setHeight(textBuffer.getLineHeight());
        cursorLine.setWidth(1);

        // For rectangles, the position is the upper left hand corner.
        cursorLine.setX(textBuffer.getCurrentX());
        cursorLine.setY(textBuffer.getCurrentY() - textBuffer.getLineHeight());

        // Make sure the text appears in front of any other objects you might add.
        // displayText.toFront();
    }

    public void updateTextBuffer(TextBuffer tb) {
        textBuffer = tb;
    }
}
