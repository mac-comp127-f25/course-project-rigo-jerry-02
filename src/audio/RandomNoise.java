package audio;

import java.util.Random;

/**
 * This class was adapted from the Audio Synth homework.
 */
public class RandomNoise implements Waveform {
    private final Random rand = new Random();
    private double currentValue = 0;

    @Override
    public Signal createSignal(double wavelength) {
        return n -> {
            if (Math.round(n % (wavelength / 512.0)) < 1) {
                currentValue = rand.nextFloat() - 0.5;
            }
            return currentValue;
        };
    }
}