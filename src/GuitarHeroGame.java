import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;


public class GuitarHeroGame {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private CanvasWindow canvas;

    public GuitarHeroGame() {
        canvas = new CanvasWindow("Guitar Hero", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private int score = 0;
    private int misses = 0;





    public static void main(String[] args) {
        new GuitarHeroGame();
    }
}
