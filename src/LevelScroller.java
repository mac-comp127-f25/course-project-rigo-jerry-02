import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import audio.Waveform;
import audioplayer.Note;
import audioplayer.Song;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

/**
 * This class was adapted from the Audio Synth homework.
 * 
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class LevelScroller extends GraphicsGroup {
    public static final int MAX_PITCH = 120;

    private GraphicsGroup noteGroup = new GraphicsGroup();
    private Map<Note,Rectangle> notesToRectangles = new HashMap<>(); // stores only gameplay notes, because those are the only ones displayed
    private Map<Rectangle,Note> rectanglesToNotes = new HashMap<>();

    private final double pixelsPerSecond, pixelsPerSemitone;

    private double currentTime = 0;

    /**
     * Creates an empty song visualization.
     *
     * @param pixelsPerSecond   Horizontal distance of one second
     * @param pixelsPerSemitone Number of pixels per pitch unit
     */
    public LevelScroller(double pixelsPerSecond, double pixelsPerSemitone) {
        this.pixelsPerSecond = pixelsPerSecond;
        this.pixelsPerSemitone = pixelsPerSemitone;

        this.add(noteGroup); // the (sub)group is empty at the moment, but should be added as soon as possible regardless
    }

    /**
     * Shows the gameplay notes of the given song, removing any song already present.
     */
    public void showSong(Song song) {
        this.removeAll();
        for (Note note : song.getNotes()) {
            if (note.getWaveform() instanceof audio.D || note.getWaveform() instanceof audio.F || note.getWaveform() instanceof audio.J || note.getWaveform() instanceof audio.K) {
                Rectangle noteRectangle = new Rectangle(note.getStartTime() * pixelsPerSecond, (MAX_PITCH - note.getPitch()) * pixelsPerSemitone, note.getDuration() * pixelsPerSecond, pixelsPerSemitone);
                noteRectangle.setStrokeWidth(0.5);
                noteRectangle.setFillColor(getNoteColor(note));
                noteGroup.add(noteRectangle);
                notesToRectangles.put(note, noteRectangle);
                rectanglesToNotes.put(noteRectangle, note);
            }
        }
    }

    @Override
    public void removeAll() {
        noteGroup.removeAll();
        notesToRectangles.clear();
        rectanglesToNotes.clear();
    }

    /**
     * Use this function when the user presses a key.
     */
    public boolean tryToRemove(double currentTime, Input key) {
        // detect which note the player is trying to remove
        Note noteToRemove = null;
        for (Note note : notesToRectangles.keySet()) { // contract guarantees that there is only one gameplay note happening at a time
            if (note.isHappening(currentTime)) {
                noteToRemove = note;
                break;
            }
        }
        if (noteToRemove == null) { // if no note is happening now at all, return false and do nothing else
            return false;
        }

        if (noteToRemove.isHappening(currentTime)) {
            switch (key) {
                case D:
                    if (noteToRemove.getWaveform() instanceof audio.D) {
                        Rectangle rectangleToRemove = notesToRectangles.get(noteToRemove);
                        noteGroup.remove(rectangleToRemove);
                        notesToRectangles.remove(noteToRemove);
                        rectanglesToNotes.remove(rectangleToRemove);
                        return true;
                    }
                    break;
                case F:
                    if (noteToRemove.getWaveform() instanceof audio.F) {
                        Rectangle rectangleToRemove = notesToRectangles.get(noteToRemove);
                        noteGroup.remove(rectangleToRemove);
                        notesToRectangles.remove(noteToRemove);
                        rectanglesToNotes.remove(rectangleToRemove);
                        return true;
                    }
                    break;
                case J:
                    if (noteToRemove.getWaveform() instanceof audio.J) {
                        Rectangle rectangleToRemove = notesToRectangles.get(noteToRemove);
                        noteGroup.remove(rectangleToRemove);
                        notesToRectangles.remove(noteToRemove);
                        rectanglesToNotes.remove(rectangleToRemove);
                        return true;
                    }
                    break;
                case K:
                    if (noteToRemove.getWaveform() instanceof audio.K) {
                        Rectangle rectangleToRemove = notesToRectangles.get(noteToRemove);
                        noteGroup.remove(rectangleToRemove);
                        notesToRectangles.remove(noteToRemove);
                        rectanglesToNotes.remove(rectangleToRemove);
                        return true;
                    }
                    break;
            
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * Moves the visualization to show that the given time is the current time.
     *
     * @param seconds Time from the beginning of the song
     * @param done    True if the song is done playing
     */
    public void setTime(double seconds, boolean done) {
        // move noteGroup to scroll display
        noteGroup.setPosition((2 - seconds) * pixelsPerSecond, getPosition().getY()); // noteGroup is 2 seconds further to the right than normal because highlighting notes looks cooler when you can see them unhighlight again

        // highlight currently clicked notes white
        for (Note note : notesToRectangles.keySet()) {
            if (note.isHappening(seconds)) {
                notesToRectangles.get(note).setFillColor(Color.WHITE);
            } else {
                notesToRectangles.get(note).setFillColor(getNoteColor(note));
            }
        }

        // set currentTime
        currentTime = seconds;
    }

    public double getTime() {
        return currentTime;
    }

    private Color getNoteColor(Note note) {
        return Color.LIGHT_GRAY;
    }
}
