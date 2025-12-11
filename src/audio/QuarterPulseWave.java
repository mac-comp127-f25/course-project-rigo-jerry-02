package audio;

public class QuarterPulseWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> ((n % wavelength) / wavelength >= 0.75) ? 0.3 : -0.3;
    }
}