package editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;

public class CursorLineBlinkEventHandler implements EventHandler<ActionEvent> {
    private int currentColorIndex = 0;
    private Color[] boxColors = {Color.BLACK, Color.WHITE};
    private Rectangle cursorLine;

    CursorLineBlinkEventHandler(Rectangle cl) {
        // Set the color to be the first color in the list.
        cursorLine = cl;
        changeColor();
    }

    private void changeColor() {
        cursorLine.setFill(boxColors[currentColorIndex]);
        currentColorIndex = (currentColorIndex + 1) % boxColors.length;
    }

    @Override
    public void handle(ActionEvent event) {
        changeColor();
    }
}