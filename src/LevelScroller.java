import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import audio.Waveform;
import audioplayer.Note;
import audioplayer.Song;
import edu.macalester.graphics.GraphicsGroup;
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

    private final double pixelsPerSecond, pixelsPerSemitone;
    private final Map<Waveform,Color> waveformColors = new HashMap<>();

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
        noteGroup.removeAll();
        notesToRectangles.clear();
        for (Note note : song.getNotes()) {
            Rectangle noteRectangle = new Rectangle(note.getStartTime() * pixelsPerSecond, (MAX_PITCH - note.getPitch()) * pixelsPerSemitone, note.getDuration() * pixelsPerSecond, pixelsPerSemitone);
            noteRectangle.setStrokeWidth(0.5);
            noteRectangle.setFillColor(getNoteColor(note, false));
            noteGroup.add(noteRectangle);
            notesToRectangles.put(note, noteRectangle);
        }
    }

    @Override
    public void removeAll() {
        noteGroup.removeAll();
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

        // highlight currently playing notes white
        for (Note note : notesToRectangles.keySet()) {
            if (seconds >= note.getStartTime() && seconds <= note.getEndTime()) {
                notesToRectangles.get(note).setFillColor(Color.WHITE); // if seconds is during the time the note is playing, make it white
            } else if (seconds > note.getEndTime()) {
                notesToRectangles.get(note).setFillColor(getNoteColor(note, true)); // if the note's already done playing, set it back to what it was before, but somewhat transparent
            } else {
                notesToRectangles.get(note).setFillColor(getNoteColor(note, false)); // if the note hasn't started playing yet, set it to what it's supposed to be while we're here just in case
            }
            if (done) {
                notesToRectangles.get(note).setFillColor(getNoteColor(note, true)); // when the entire song finishes technically the playhead never actually exits the last notes so set them transparent too if the song's done
            }
        }
    }

    private Color getNoteColor(Note note, boolean donePlaying) {
        Waveform waveform = note.getWaveform();
        Color color = waveformColors.get(waveform);
        if (color == null) {
            color = Color.getHSBColor(waveformColors.size() * 0.382f % 1, 1, 0.6f);
            waveformColors.put(waveform, color);
        }
        if (donePlaying) {
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        } else {
            return color;
        }
    }
}
