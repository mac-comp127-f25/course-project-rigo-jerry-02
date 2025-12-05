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
    private static final double LANE_WIDTH = 150;
    private static final double LANE_START_X = 260;

    private static final double NOTE_WIDTH = 40;

    private GraphicsGroup noteGroup = new GraphicsGroup();
    private Map<Note,Rectangle> notesToRectangles = new HashMap<>(); // stores only gameplay notes, because those are the only ones displayed
    private Map<Rectangle,Note> rectanglesToNotes = new HashMap<>();

    private final double pixelsPerSecond, pixelsPerSemitone;

    private double currentTime = 0;
    private double songFinishTime = 0;

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
        notesToRectangles.clear();
        add(noteGroup);

        for (Note note : song.getNotes()) {
            if (note.getWaveform() instanceof audio.D) {
                Rectangle noteRectangle = new Rectangle(LANE_START_X, -note.getStartTime() * pixelsPerSecond, NOTE_WIDTH, note.getDuration() * pixelsPerSecond);
                noteRectangle.setStrokeWidth(0.5);
                noteRectangle.setFillColor(getNoteColor(note));
                noteGroup.add(noteRectangle);
                notesToRectangles.put(note, noteRectangle);
                rectanglesToNotes.put(noteRectangle, note);
            } else if (note.getWaveform() instanceof audio.F) {
                Rectangle noteRectangle = new Rectangle(LANE_START_X + LANE_WIDTH, -note.getStartTime() * pixelsPerSecond, NOTE_WIDTH, note.getDuration() * pixelsPerSecond);
                noteRectangle.setStrokeWidth(0.5);
                noteRectangle.setFillColor(getNoteColor(note));
                noteGroup.add(noteRectangle);
                notesToRectangles.put(note, noteRectangle);
                rectanglesToNotes.put(noteRectangle, note);
            } else if (note.getWaveform() instanceof audio.J) {
                Rectangle noteRectangle = new Rectangle(LANE_START_X + LANE_WIDTH * 2, -note.getStartTime() * pixelsPerSecond, NOTE_WIDTH, note.getDuration() * pixelsPerSecond);
                noteRectangle.setStrokeWidth(0.5);
                noteRectangle.setFillColor(getNoteColor(note));
                noteGroup.add(noteRectangle);
                notesToRectangles.put(note, noteRectangle);
                rectanglesToNotes.put(noteRectangle, note);
            } else if (note.getWaveform() instanceof audio.K) {
                Rectangle noteRectangle = new Rectangle(LANE_START_X + LANE_WIDTH * 3, -note.getStartTime() * pixelsPerSecond, NOTE_WIDTH, note.getDuration() * pixelsPerSecond);
                noteRectangle.setStrokeWidth(0.5);
                noteRectangle.setFillColor(getNoteColor(note));
                noteGroup.add(noteRectangle);
                notesToRectangles.put(note, noteRectangle);
                rectanglesToNotes.put(noteRectangle, note);
            }
        }

        songFinishTime = song.getDuration();
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
            if (note.isHappening(currentTime) && matchesKey(note, key)) {
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
     * Returns the number of notes that have passed by without being clicked on, as of the input time.
     */
    public int numMissedNotes(double seconds) {
        int total = 0;
        for (Note note : notesToRectangles.keySet()) {
            if (note.getEndTime() < seconds) {
                total++;
            }
        }
        return total;
    }

    /**
     * Moves the visualization to show that the given time is the current time.
     *
     * @param seconds Time from the beginning of the song
     * @param done    True if the song is done playing
     */
    public void setTime(double seconds, boolean done) {
        // move noteGroup to scroll display
        noteGroup.setPosition(getPosition().getX(), (seconds + 4) * pixelsPerSecond); // noteGroup is 2 seconds further to the right than normal because highlighting notes looks cooler when you can see them unhighlight again

        // highlight currently clicked notes white
        for (Note note : notesToRectangles.keySet()) {
            if (note.isHappening(seconds)) {
                notesToRectangles.get(note).setFillColor(Color.WHITE);
            } else if (seconds <= note.getStartTime()) {
                notesToRectangles.get(note).setFillColor(getNoteColor(note));
            } else {
                notesToRectangles.get(note).setFillColor(Color.PINK);
            }
        }

        // set currentTime
        currentTime = seconds;
    }

    public double getTime() {
        return currentTime;
    }

    public Boolean hasFinished(double seconds) {
        return seconds > songFinishTime;
    }

    // Check lane key user pressed on the actual note
    private boolean matchesKey(Note note, Input key) {
        Waveform wf = note.getWaveform();

        if (key == Input.D && wf instanceof audio.D) {
            return true;
        } else if (key == Input.F && wf instanceof audio.F) {
            return true;
        } else if (key == Input.J && wf instanceof audio.J) {
            return true;
        } else if (key == Input.K && wf instanceof audio.K) {
            return true;
        } else {
            return false;
        }
    }

    private Color getNoteColor(Note note) {
        return Color.LIGHT_GRAY;
    }
}
