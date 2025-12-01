import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GuitarHeroGame {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final double SQUARE_WIDTH = 30;
    private static final double SQUARE_HEIGHT = 30;
    private static final int NUM_LANES = 4;
    private static final double LANE_WIDTH = 50;    
    private static final double LANE_END = 100; 
    private int score = 0;
    private int misses = 0;
    private CanvasWindow canvas;
    private Level level;
    private List<Square> notes = new ArrayList<>();

    public GuitarHeroGame() {
        canvas = new CanvasWindow("Guitar Hero", CANVAS_WIDTH, CANVAS_HEIGHT);

        drawBackground();
        drawLanes();
        dropSquare();

        canvas.animate(() -> {});   
    }

    private void drawLanes() {
  
    }

    private void dropSquare() {

    }

    private void drawBackground() {

    }



    public static void main(String[] args) {
        new GuitarHeroGame();
    }
}
