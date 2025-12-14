package audioplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import audio.AudioBuffer;
import audio.Utils;
import audio.Waveform;

/**
 * This class was adapted from the Audio Synth assignment.
 * Any song is guaranteed to only have one gameplay note at a time; either no gameplay note or precisely one of D, F, J, or K is currently happening at any timestamp.
 * 
 * A collection of notes that (perhaps) make music together.
 * A newly created song starts out empty — no notes.
 */
public class Song {
    private final List<Note> notes = new ArrayList<>();

    /**
     * Adds the given note to the notes already in the piece.
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /**
     * Returns an unmodifiable collection of all the notes in the piece.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    /**
     * Returns maximum end time of any note in the song. Returns 0 if the song is empty.
     */
    public double getDuration() {
        return notes.stream()
            .mapToDouble(Note::getEndTime)
            .max().orElse(0);
    }

    /**
     * Renders all the notes in the piece to a normalized audio buffer.
     */
    public AudioBuffer renderAudio() {
        AudioBuffer buffer = new AudioBuffer(Utils.convertSecondsToSamples(this.getDuration()));
        for (Note note : notes) {
            buffer.mix(note.getWaveform().createSignal(Utils.convertPitchToWavelength(note.getPitch())), Utils.convertSecondsToSamples(note.getStartTime()), Utils.convertSecondsToSamples(note.getDuration()));
        }
        buffer.normalize();
        return buffer;
    }
}
