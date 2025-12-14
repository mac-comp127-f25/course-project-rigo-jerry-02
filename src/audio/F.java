package audio;

/**
 * This is only used for GameplayNotes that correspond to the F key. They shouldn't actually have any sound attached to them, so their waveform is completely silent.
 */
public class F implements Waveform {

    @Override
    public Signal createSignal(double pitch) {
        return n -> 0;
    }
}
