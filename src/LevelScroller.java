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
    private Map<Note,Rectangle> notesToRectangles = new HashMap<>();
    private Map<Rectangle,Note> rectanglesToNotes = new HashMap<>();

    private final double pixelsPerSecond, pixelsPerSemitone;

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
     * Shows the notes of the given song, removing any song already present.
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
     * Use this function when the user 
     */
    public boolean tryToRemove(Note noteToRemove, double currentTime) {
        if (noteToRemove.isHappening(currentTime) && (noteToRemove.getWaveform() instanceof audio.D || noteToRemove.getWaveform() instanceof audio.F || noteToRemove.getWaveform() instanceof audio.J || noteToRemove.getWaveform() instanceof audio.K)) {
            Rectangle rectangleToRemove = notesToRectangles.get(noteToRemove);
            noteGroup.remove(rectangleToRemove);
            notesToRectangles.remove(noteToRemove);
            rectanglesToNotes.remove(rectangleToRemove);
            return true;
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
            if (seconds >= note.getStartTime() && seconds <= note.getEndTime()) {
                notesToRectangles.get(note).setFillColor(Color.WHITE);
            } else {
                notesToRectangles.get(note).setFillColor(getNoteColor(note));
            }
        }
    }

    private Color getNoteColor(Note note) {
        return Color.LIGHT_GRAY;
    }
}
