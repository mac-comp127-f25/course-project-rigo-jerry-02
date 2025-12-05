import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.KeyboardEvent;

import java.awt.Color;

import audioplayer.Song;
import audioplayer.SongReader;
import audioplayer.Note;

public class GuitarHeroGame {
    private static final int CANVAS_WIDTH = 1060;
    private static final int CANVAS_HEIGHT = 800;

    private int score = 0;
    private int misses = 0;
    private Song song; 
    private CanvasWindow canvas;
    private LevelScroller scroller;
    private double currentTime = 0;   

    private InputHandler dHandler;   
    private InputHandler fHandler;   
    private InputHandler jHandler;     
    private InputHandler kHandler; 

    private GraphicsText scoreText;   
    private GraphicsText missesText;
    

    public GuitarHeroGame() {
        canvas = new CanvasWindow("Guitar Hero", CANVAS_WIDTH, CANVAS_HEIGHT);

        drawBackground();
        setupSongAndScroller();
        setupInput();            
        setupHud(); 

        canvas.animate(this::tickFrame);
        song.renderAudio().play(this::tick);  
    }

    //draws the background image behind everything
    private void drawBackground() {
        Image bg = new Image(0,0, "background.png");
        canvas.add(bg);
    }

    // reads the CSV into a Song, creates the LevelScroller, and shows the notes
    private void setupSongAndScroller() {
        String fileName = "song.csv";
        song = new SongReader().readSong(fileName);

        double pixelsPerSecond = 80;  
        double pixelsPerSemitone = 6;

        scroller = new LevelScroller(pixelsPerSecond, pixelsPerSemitone);
        scroller.setPosition(0, 0); 
        canvas.add(scroller);

        scroller.showSong(song);
    }

    // creates the score / misses text and adds it to the canvas
    private void setupHud() {
        scoreText = new GraphicsText("Score: 0");
        scoreText.setFillColor(Color.WHITE);
        scoreText.setPosition(10, 20);
        canvas.add(scoreText);

        missesText = new GraphicsText("Misses: 0");
        missesText.setFillColor(Color.WHITE);
        missesText.setPosition(10, 40);
        canvas.add(missesText);
    }

    // sets up input handlers for D/F/J/K and wires them to key presses
    private void setupInput() {
        dHandler = new InputHandler(Input.D);
        fHandler = new InputHandler(Input.F);
        jHandler = new InputHandler(Input.J);
        kHandler = new InputHandler(Input.K);

        canvas.onKeyDown((KeyboardEvent e) -> {
            dHandler.pollInput(e);
            fHandler.pollInput(e);
            jHandler.pollInput(e);
            kHandler.pollInput(e);
        });
    }

    // called by the audio engine: updates time, moves notes, and handles hits  
    private void tick(double seconds, boolean done) {
        currentTime = seconds;
        scroller.setTime(seconds, done);
        if (dHandler.isPressed()) {
            checkHit(Input.D);
        }
        if (fHandler.isPressed()) {
            checkHit(Input.F);
        } 
        if (jHandler.isPressed()) {
            checkHit(Input.J);
        } 
        if (kHandler.isPressed()) {
            checkHit(Input.K);
        }
        dHandler.doFrame();
        fHandler.doFrame();
        jHandler.doFrame();
        kHandler.doFrame();
    }
    
    // frame-based timer: advances currentTime and reuses tick()
    private void tickFrame() {
        currentTime = scroller.getTime();
        boolean done = currentTime >= song.getDuration();
        tick(currentTime, done);
    }

    // checks if a key press hits any note in the correct lane at the right time
    private void checkHit(Input input) {
        if (scroller.tryToRemove(currentTime, input)) {
            score += 1;
            scoreText.setText("Score: " + score);
        } else {
            misses++;
            missesText.setText("Misses: " + misses);
        }
    }

    public static void main(String[] args) {
        new GuitarHeroGame();
    }
}
