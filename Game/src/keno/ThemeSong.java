/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 *
 * @author mark
 */
public class ThemeSong {

    private static Sequencer sequencer = null;
    private static Synthesizer synthesizer = null;

    public ThemeSong() throws InvalidMidiDataException, IOException, MidiUnavailableException {
        InputStream ms = this.getClass().getResourceAsStream("/keno/theme/sounds/base/midi/disco.mid");
        Sequence sequence = null;
        sequence = MidiSystem.getSequence(ms);
        sequencer = MidiSystem.getSequencer();
        if (sequencer == null) {
            throw new MidiUnavailableException();
        }

        sequencer.open();
        sequencer.setSequence(sequence);

        if (!(sequencer instanceof Synthesizer)) {
            /*
             *	We try to get the default synthesizer, open()
             *	it and chain it to the sequencer with a
             *	Transmitter-Receiver pair.
             */
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            Receiver synthReceiver = synthesizer.getReceiver();
            Transmitter seqTransmitter = sequencer.getTransmitter();
            seqTransmitter.setReceiver(synthReceiver);
        }
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(-1);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

        // Set the volume for playback.
        Receiver receiver = MidiSystem.getReceiver();
        ShortMessage volumeMessage = new ShortMessage();
        for (int i = 0; i < 16; i++) {
            volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, 20);
            receiver.send(volumeMessage, -1);
        }
    }

    public void play() {
        sequencer.start();
    }

    public void pause() {
        sequencer.stop();
    }

    public void close() {
        sequencer.close();
        if (synthesizer != null) {
            synthesizer.close();
        }

    }
}
