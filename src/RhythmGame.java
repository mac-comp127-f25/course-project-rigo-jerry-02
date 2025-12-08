import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.KeyboardEvent;

import java.awt.Color;

import audioplayer.Song;
import audioplayer.SongReader;

public class RhythmGame {
    private static final int CANVAS_WIDTH = 1150;
    private static final int CANVAS_HEIGHT = 1000;

    private int score = 0; // total score
    private int positiveScore = 0; // points gained
    private int negativeScore = 0; // points lost by missing
    private int misses = 0; // all misses
    private int wrongKeyMisses = 0; // misses incurred by pressing something
    private int nothingMisses = 0; // misses incurred by not pressing something
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
    

    public RhythmGame() {
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

    //draws the level end screen
    private void drawFinishScreen() {
        Image finishImage = new Image(0,0, "End.png");
        canvas.add(finishImage);

        GraphicsText finishMessage = new GraphicsText();
        if (score >= 563) { // TODO: make this the actual perfect score when the gameplay is done
            finishMessage.setText("Player is perfect");
        } else if (score > 200) {
            finishMessage.setText("Player is cracked");
        } else if (score > 100) {
            finishMessage.setText("Player is based");
        } else if (score > 0) {
            finishMessage.setText("Player is ok");
        } else if (score > -100) {
            finishMessage.setText("Player is cooked");
        } else if (score > -200000) {
            finishMessage.setText("Player is incompetent");
        } else {
            finishMessage.setText("Bruh"); //yes, this is possible
        }
        GraphicsText finishScoreText = new GraphicsText("Score: " + score);
        finishMessage.setFontSize(40);
        finishMessage.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT * 0.3);
        finishScoreText.setFontSize(70);
        finishScoreText.setCenter(CANVAS_WIDTH/2, CANVAS_HEIGHT * 0.35);

        canvas.add(finishMessage);
        canvas.add(finishScoreText);
    }

    //resets the screen when the level ends to make room for the level end screen
    private void cleanScreen() {
        canvas.removeAll();
        drawFinishScreen();
    }

    // reads the CSV into a Song, creates the LevelScroller, and shows the notes
    private void setupSongAndScroller() {
        String fileName = "song.csv";
        song = new SongReader().readSong(fileName);

        double pixelsPerSecond = 166;
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

        nothingMisses = scroller.numMissedNotes(seconds);
        misses = nothingMisses + wrongKeyMisses;
        missesText.setText("Misses: " + misses);
        negativeScore = misses * (misses + 1) / 2; // formula for the nth triangular number (every time you miss you lose as many points as every time you've missed over the course of the game)
        score = positiveScore - negativeScore;
        scoreText.setText("Score: " + score);

        if (done) {
            cleanScreen();
        }
    }
    
    // frame-based timer: advances currentTime and reuses tick()
    private void tickFrame() {
        currentTime = scroller.getTime();
        boolean done = currentTime >= song.getDuration();
        tick(currentTime, done);
    }

    // checks if a key press hits any note in the correct lane at the right time and updates the score if necessary
    private void checkHit(Input input) {
        if (scroller.tryToRemove(currentTime, input)) {
            positiveScore += 3;
            score = positiveScore - negativeScore;
            scoreText.setText("Score: " + score);
        } else {
            wrongKeyMisses++;
            misses = wrongKeyMisses + nothingMisses;
            missesText.setText("Misses: " + misses);
        }
    }

    public static void main(String[] args) {
        new RhythmGame();
    }
}
