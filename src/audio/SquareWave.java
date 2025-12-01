package audio;

/**
 * This class was adapted from the Audio Synth homework.
 */
public class SquareWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> (Math.round(n / wavelength % 1) - 0.5) * 0.7;
    }
}
