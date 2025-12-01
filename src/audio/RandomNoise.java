package audio;

import java.util.Random;

/**
 * This class was adapted from the Audio Synth homework.
 */
public class RandomNoise implements Waveform {
    private final Random rand = new Random();

    @Override
    public Signal createSignal(double pitch) {
        return n -> rand.nextFloat() - 0.5;
    }
}
