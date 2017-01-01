

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.text.Font;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;

public class Editor extends Application {
    public static int WINDOW_WIDTH = 500;
    public static int WINDOW_HEIGHT = 500;
    public static double USEABLE_SCREEN;
    private static final int STARTING_FONT_SIZE = 20;
    public static int fontSize = STARTING_FONT_SIZE;
    public static String fontName = "Verdana";
    public static TextBuffer textBuffer;
    private static final Rectangle cursorLine = new Rectangle(0, 0);
    String filename;
    private static Text displayText = new Text(0, 0, "");
    private static ScrollBar scrollBar = new ScrollBar();
    public static MouseClickEventHandler mouseClick;
//    private EventHandler<KeyEvent> keyEventHandler;
    private static  KeyEventHandler keyEventHandler;
    public static double window_offset = 0;
    public OpStack undoStack = new OpStack();
    public OpStack redoStack = new OpStack();


    public void makeRectangleColorChange() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        CursorLineBlinkEventHandler cursorChange = new CursorLineBlinkEventHandler(cursorLine);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void initScrollBar() {
        // setup the scrollbar
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(WINDOW_HEIGHT);
        scrollBar.setMin(0);
        scrollBar.setMax(WINDOW_HEIGHT);
    }

    public static void reformatText(double window_width, String fn, int fs) {
        fontSize =fs;
        fontName = fn;
        TextBuffer newTextBuffer = new TextBuffer(window_width, fn, fs);
        boolean achivedCurrentPos = false;
        if (textBuffer.isEmpty()) {
            textBuffer = newTextBuffer;
            return;
        }
        // textbuffer is not empty, we need to scan the content
        FastLinkListOfLine content = textBuffer.getContent();
        double currentX = textBuffer.getCurrentX();
        double currentY = textBuffer.getCurrentY();

        double currentXForNewText = 0;
        double currentYForNewText = 0;

        LineNode scanLine = content.getSentinel();
        double scanedY = 0;
        double scanedX = 0;
        //System.out.println(linesTobeScaned);
        while (!textBuffer.getContent().isAtEnd(scanLine)) {

            scanLine = scanLine.next;
            //System.out.println(scanLine.item.getLineHeight());
            scanedY += scanLine.item.getLineHeight();
            scanedX = 0;
            // start to scan this line
            CharNode scanChar = scanLine.item.getSentinel();
            while (!scanLine.item.isAtEnd(scanChar)) {
                scanChar = scanChar.next;
                scanedX += scanChar.getCharWidth();
                newTextBuffer.insert(scanChar.item);

                // check the current scanned position in the old window
                if (scanedY == currentY) {
                    // we are in the target line now.
                    if (scanedX == currentX) {
                        // we are in the exact position now.
                        // mark down the new CurrentX and CurrentY
                        System.out.print(scanChar.item);
                        if (!achivedCurrentPos) {

                            currentXForNewText = newTextBuffer.getCurrentX();
                            currentYForNewText = newTextBuffer.getCurrentY() - 0.5 * scanLine.item.getLineHeight();
                            achivedCurrentPos = true;
                        }
                    }
                }
            }
        }

        newTextBuffer.moveToMousePos(currentXForNewText, currentYForNewText);
        textBuffer = newTextBuffer;
        mouseClick.updateTextBuffer(textBuffer);
        keyEventHandler.updateTextBuffer(textBuffer);

        // reset the font name and font size
        displayText.setFont(Font.font(fontName, fontSize));
        // reshow the text in the window app
        // set the string to the buffer
        displayText.setText(textBuffer.toString());

        // show the initlized contents in the file filename
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
        double scrollRatio = scrollBar.getValue() / scrollBar.getMax();
        scrollBar.setMax(Math.max(WINDOW_HEIGHT, textBuffer.getBufferLength() - WINDOW_HEIGHT));
        scrollBar.setValue(scrollBar.getMax() * scrollRatio);
        return;
    }


    public void loadFile() {
        List<String> args = getParameters().getRaw();
        if (args.size() != 1) {
            System.out.println("You need to give me exactly one arg parameters");
            System.out.println(args.size());
            System.exit(1);
        }

        filename = args.get(0);

        try {
            File file = new File(filename);
            if (file.exists()) {
                // read the contents in the file
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                int intRead = -1;

                while ((intRead = bufferedReader.read()) != -1) {
                    // The integer read can be cast to a char, because we're assuming ASCII.
                    char charRead = (char) intRead;
                    textBuffer.insert(charRead);
                }

                // set the string to the buffer
                displayText.setText(textBuffer.toString());

                // show the initlized contents in the file filename
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
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when reading; exception was: " + ioException);
        }
    }


    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        Group textRoot = new Group();
        root.getChildren().add(textRoot);
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        // setup display
        displayText.setTextOrigin(VPos.TOP);
        displayText.setFont(Font.font(fontName, fontSize));


        // add the node of cursor line blink
        textRoot.getChildren().add(cursorLine);
        makeRectangleColorChange();

        // All new Nodes need to be added to the root in order to be displayed
        textRoot.getChildren().add(displayText);
        // load the input files into text buffer.
        initScrollBar();
        root.getChildren().add(scrollBar);
        USEABLE_SCREEN = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(USEABLE_SCREEN);

        textBuffer = new TextBuffer(USEABLE_SCREEN, fontName, fontSize);

        loadFile();
        if (!textBuffer.isEmpty() && textBuffer.getBufferLength() > WINDOW_HEIGHT) {
            scrollBar.setMax(textBuffer.getBufferLength() - WINDOW_HEIGHT);
            scrollBar.setValue(textBuffer.getBufferLength() - WINDOW_HEIGHT);
            textRoot.setLayoutY(-textBuffer.getBufferLength() + WINDOW_HEIGHT);
            window_offset = textBuffer.getBufferLength() - WINDOW_HEIGHT;
        }
        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        keyEventHandler =
                new KeyEventHandler(textRoot, textBuffer, cursorLine, filename, displayText, scrollBar, undoStack, redoStack);

        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        //register the mouse click event to the scene
        mouseClick = new MouseClickEventHandler(root, textBuffer, cursorLine, displayText);
        scene.setOnMouseClicked(mouseClick);

        // register the scroll bar
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                // newValue describes the value of the new position of the scroll bar. The numerical
                // value of the position is based on the position of the scroll bar, and on the min
                // and max we set above. For example, if the scroll bar is exactly in the middle of
                // the scroll area, the position will be:
                //      scroll minimum + (scroll maximum - scroll minimum) / 2
                // Here, we can directly use the value of the scroll bar to set the height of Josh,
                // because of how we set the minimum and maximum above.
                if (newValue != oldValue) {
                    textRoot.setLayoutY(-newValue.doubleValue());
                    window_offset =  newValue.doubleValue();
                    //System.out.println(window_offset);
                }
            }
        });

        // register the resize listener
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                // Re-compute Allen's width.
                USEABLE_SCREEN = newScreenWidth.intValue() - scrollBar.getLayoutBounds().getWidth();
                scrollBar.setLayoutX(USEABLE_SCREEN);
                reformatText(USEABLE_SCREEN, fontName, fontSize);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                WINDOW_HEIGHT = newScreenHeight.intValue();
                scrollBar.setPrefHeight(WINDOW_HEIGHT);
                double scrollRatio = scrollBar.getValue() / scrollBar.getMax();
                scrollBar.setMax(Math.max(WINDOW_HEIGHT, textBuffer.getBufferLength() - WINDOW_HEIGHT));
                scrollBar.setValue(scrollBar.getMax() * scrollRatio);
            }
        });

        primaryStage.setTitle("editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}