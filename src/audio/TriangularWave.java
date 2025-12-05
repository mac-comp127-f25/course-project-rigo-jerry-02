package audio;

/**
 * This class is adapted from the Audio Synth homework.
 */
public class TriangularWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> Math.abs(n / wavelength % 1 - 0.5) * 4 - 1;
    }
}
