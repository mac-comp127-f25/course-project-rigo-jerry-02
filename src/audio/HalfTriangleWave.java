package audio;

public class HalfTriangleWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> {
            if ((n % wavelength) / wavelength <= 0.5) {
                return (Math.abs((n * 2) / wavelength % 1 - 0.5) * 4 - 1) * 0.75;
            } else {
                return 0;
            }
        };
    }
}