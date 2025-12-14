package audio;

import java.util.ArrayList;
import java.util.Random;

public class PeriodicNoise implements Waveform {
    private final Random rand = new Random();
    private ArrayList<Float> randList = new ArrayList<Float>();

    @Override
    public Signal createSignal(double wavelength) {
        return n -> {
            try {
                return (double) randList.get((int) Math.round(n % wavelength));
            } catch (Exception e) {
                // the (n % wavelength)th entry in randList must not exist yet, so create it
                while (randList.size() <= Math.round(n % wavelength)) {
                    randList.add(rand.nextFloat());
                }
                return (double) randList.get((int) Math.round(n % wavelength));
            }
        };
    }
}