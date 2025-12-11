package audio;

/**
 * This class was adapted from the Audio Synth homework.
 */
public class ChorusSquareWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> (
            (Math.round((n*0.99) / wavelength % 1) - 0.5) * 0.7 +
            (Math.round(n / wavelength % 1) - 0.5) * 0.7 +
            (Math.round((n*1.01) / wavelength % 1) - 0.5) * 0.7
        ) / 2.5;
    }
}
