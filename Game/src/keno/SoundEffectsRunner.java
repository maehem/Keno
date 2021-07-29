/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno;

import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author mark
 */
public class SoundEffectsRunner implements Runnable {
    ByteArrayInputStream source;
    int delay = 0;
    AudioFormat format;
    SoundEffects parent;


    public SoundEffectsRunner(SoundEffects parent, byte[] samples, AudioFormat format, int delay) {
        this.parent = parent;
        this.delay = delay;
        this.format = format;
        source = new ByteArrayInputStream(samples);

        new Thread(this).start();
    }
    
    /**
    Plays a stream. This method blocks (doesn't return) until
    the sound is finished playing.
     */
    public void run() {
        if ( delay > 0 ) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {}
        }

        // use a short, 100ms (1/10th sec) buffer for real-time
        // change to the sound stream
        int bufferSize = format.getFrameSize() *
                Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];

        // create a line to play to
        SourceDataLine line;
        try {
            DataLine.Info info =
                    new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            return;
        }

        // start the line
        line.start();

        // copy data to the line
  //      try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    line.write(buffer, 0, numBytesRead);
                }
            }
  //      } catch (IOException ex) {
  //          ex.printStackTrace();
   //     }

        // wait until all data is played, then close the line
        line.drain();
        line.close();

        parent.playDone();
    }

}
