package audioplayer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import audio.*;

/**
 * This class was adapted from the Audio Synth homework.
 * 
 * A utility for reading Songs from text files. The text file consists of the following four values
 * in this order:
 *
 * - One of the following waveform names: sine tri square saw noise
 * - pitch in MIDI units
 * - start time in seconds
 * - duration in seconds
 *
 * ...repeated for every note in the song. Values may be separated by commas, spaces, or newlines.
 * The order of notes does not matter.
 */
public class SongReader {
    private final Map<String, Waveform> waveforms = new HashMap<>();

    /**
     * Creates a new song reader with the following name-to-waveform mappings:
     *
     * - "sine"   → SineWave
     * - "tri"    → TriangularWave
     * - "square" → SquareWave
     * - "saw"    → SawtoothWave
     * - "noise"  → RandomNoise
     */
    public SongReader() {
        waveforms.put("sine", new SineWave());
        waveforms.put("tri", new TriangularWave());
        waveforms.put("square", new SquareWave());
        waveforms.put("saw", new SawtoothWave());
        waveforms.put("noise", new RandomNoise());
        waveforms.put("quart", new QuarterPulseWave());
        waveforms.put("half", new HalfTriangleWave());
        waveforms.put("period", new PeriodicNoise());
        waveforms.put("chorus", new ChorusSquareWave());
        waveforms.put("quiet", new QuietSineWave());
        waveforms.put("qnoise", new QuietNoise());
        waveforms.put("d", new D());
        waveforms.put("f", new F());
        waveforms.put("j", new J());
        waveforms.put("k", new K());
    }

    /**
     * Reads song data from the given project resource.
     */
    public Song readSong(String resourceName) {
        return readSong(getClass().getResourceAsStream("/" + resourceName));
    }

    /**
     * Reads song data from an arbitrary input stream.
     */
    @SuppressWarnings("resource")
    public Song readSong(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter("\\s*,\\s*|\\s+");
        Song song = new Song();
        while(scanner.hasNext()) {
            String waveformName = scanner.next();
            Waveform waveform = Objects.requireNonNull(
                waveforms.get(waveformName), "No waveform named " + waveformName);
            double pitch = scanner.nextDouble();
            double startTime = scanner.nextDouble();
            double duration = scanner.nextDouble();
            if (waveform instanceof D || waveform instanceof F || waveform instanceof J || waveform instanceof K) {
                startTime -= Math.min(0.1, duration * 0.25);
                if (startTime < 0) {
                    startTime = 0;
                }
                duration += Math.min(0.1, duration * 0.25);
            }
            song.addNote(new Note(waveform, pitch, startTime, duration));
        }
        return song;
    }
}
