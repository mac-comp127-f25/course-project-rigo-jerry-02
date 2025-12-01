package audioplayer;

import audio.Waveform;

import java.util.Objects;

/**
 * This class was adapted from the Audio Synth assignment.
 * 
 * A single note in a piece of music: a waveform at a given pitch, starting at a certain time, and
 * lasting for a certain duration.
 */
public class Note {
    private final Waveform waveform;
    private final double pitch, startTime, duration;

    /**
     * Creates a note.
     *
     * @param waveform  The shape of the wave (must not be null)
     * @param pitch     Pitch in MIDI units
     * @param startTime Start time in seconds
     * @param duration  Duration in seconds
     */
    public Note(Waveform waveform, double pitch, double startTime, double duration) {
        this.waveform = Objects.requireNonNull(waveform, "waveform");
        this.pitch = pitch;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Waveform getWaveform() {
        return waveform;
    }

    public double getPitch() {
        return pitch;
    }

    /**
     * Returns whether this note is currently playing, given the input timestamp in the song.
     * @param timestamp The amount of time in seconds into the song we're currently at
     */
    public boolean isHappening(double timestamp) {
        return (startTime <= timestamp) && (timestamp <= startTime + duration);
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return startTime + duration;
    }

    public double getDuration() {
        return duration;
    }
}
