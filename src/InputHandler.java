import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

/**
 * Each one of these only handles one input type at a time. Make one of these for each input being polled for.
 */
public class InputHandler {
    private final Input input; // the input this particular InputHandler handles
    private boolean pressed; // whether or not this input was just pressed this frame

    /**
     * Constructor. Sets which input this InputHandler handles.
     */
    public InputHandler(Input input) {
        this.input = input;
        pressed = false;
    }

    /**
     * Returns whether or not this particular input was just pressed this frame. (Still returns false if you're holding it down from a previous frame.)
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Runs every time a KeyboardEvent happens. Registers whether or not you pressed the corresponding key to this InputHandler's input this frame. Does *not* register whether you pressed anything else, including other gameplay inputs.
     */
    public void pollInput(KeyboardEvent event) {
        switch (input) {
            // gameplay inputs
            case D:
                pressed = (event.getKey() == Key.D);
                break;
            case F:
                pressed = (event.getKey() == Key.F);
                break;
            case J:
                pressed = (event.getKey() == Key.J);
                break;
            case K:
                pressed = (event.getKey() == Key.K);
                break;

            // GUI inputs
            case LEFT_ARROW:
                pressed = (event.getKey() == Key.LEFT_ARROW);
                break;
            case RIGHT_ARROW:
                pressed = (event.getKey() == Key.RIGHT_ARROW);
                break;
            case ENTER:
                pressed = (event.getKey() == Key.RETURN_OR_ENTER);
                break;
            case ESCAPE:
                pressed = (event.getKey() == Key.ESCAPE);
                break;
        }
    }
    /**
     * Runs every frame *after* anything that needs this frame's inputs happens.
     */
    public void doFrame() {
        pressed = false;
    }

    /**
     * *Used only for testing.* Run this to check whether input handling is working properly.
     */
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("InputHandler test", 400, 400);

        Rectangle d = new Rectangle(0, 0, 40, 40);
        d.setFillColor(Color.WHITE);
        canvas.add(d);
        Rectangle f = new Rectangle(40, 0, 40, 40);
        f.setFillColor(Color.WHITE);
        canvas.add(f);
        Rectangle j = new Rectangle(80, 0, 40, 40);
        j.setFillColor(Color.WHITE);
        canvas.add(j);
        Rectangle k = new Rectangle(120, 0, 40, 40);
        k.setFillColor(Color.WHITE);
        canvas.add(k);

        Rectangle tab = new Rectangle(0, 40, 40, 40);
        tab.setFillColor(Color.WHITE);
        canvas.add(tab);
        Rectangle shifttab = new Rectangle(40, 40, 40, 40);
        shifttab.setFillColor(Color.WHITE);
        canvas.add(shifttab);
        Rectangle enter = new Rectangle(80, 40, 40, 40);
        enter.setFillColor(Color.WHITE);
        canvas.add(enter);
        Rectangle escape = new Rectangle(120, 40, 40, 40);
        escape.setFillColor(Color.WHITE);
        canvas.add(escape);

        InputHandler DHandler = new InputHandler(Input.D);
        InputHandler FHandler = new InputHandler(Input.F);
        InputHandler JHandler = new InputHandler(Input.J);
        InputHandler KHandler = new InputHandler(Input.K);
        InputHandler TABHandler = new InputHandler(Input.LEFT_ARROW);
        InputHandler SHIFTTABHandler = new InputHandler(Input.RIGHT_ARROW);
        InputHandler ENTERHandler = new InputHandler(Input.ENTER);
        InputHandler ESCAPEHandler = new InputHandler(Input.ESCAPE);

        canvas.animate(() -> {
            d.setFillColor(new Color(DHandler.isPressed() ? 0 : 100000));
            f.setFillColor(new Color(FHandler.isPressed() ? 0 : 100000));
            j.setFillColor(new Color(JHandler.isPressed() ? 0 : 100000));
            k.setFillColor(new Color(KHandler.isPressed() ? 0 : 100000));
            tab.setFillColor(new Color(TABHandler.isPressed() ? 0 : 100000));
            shifttab.setFillColor(new Color(SHIFTTABHandler.isPressed() ? 0 : 100000));
            enter.setFillColor(new Color(ENTERHandler.isPressed() ? 0 : 100000));
            escape.setFillColor(new Color(ESCAPEHandler.isPressed() ? 0 : 100000));

            DHandler.doFrame();
            FHandler.doFrame();
            JHandler.doFrame();
            KHandler.doFrame();
            TABHandler.doFrame();
            SHIFTTABHandler.doFrame();
            ENTERHandler.doFrame();
            ESCAPEHandler.doFrame();
        });
        canvas.onKeyDown((KeyboardEvent e) -> {
            DHandler.pollInput(e);
            FHandler.pollInput(e);
            JHandler.pollInput(e);
            KHandler.pollInput(e);
            TABHandler.pollInput(e);
            SHIFTTABHandler.pollInput(e);
            ENTERHandler.pollInput(e);
            ESCAPEHandler.pollInput(e);
        });
    }
}
