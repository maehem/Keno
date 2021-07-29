package keno;

import java.io.*;
import javax.sound.sampled.*;
import keno.interfaces.ThemeListener;

/**
The SimpleSoundPlayer encapsulates a sound that can be opened
from the file system and later played.
 */
public class SoundEffects implements ThemeListener {

    public enum Sound { 
            PICK_SET, PICK_ERASE,
            DRAW, MATCH, 
            WIN, LOSE,
            CREDIT_PAY, CREDIT_DEBIT,
            THEME_LOAD
        };

    public static final int NO_DELAY = 0;

    private boolean hasSound = false;
    private String name = "";
    private int threadCount = 0;

    private AudioFormat format;
    private byte[] samples = null;
    Sound sound = null;

    public SoundEffects(Sound sound) {
        this.sound = sound;
        initSound();
    }

    private void initSound() {
        AudioInputStream is = null;
        try {
            switch (sound) {
                case PICK_SET:
                    is = getAudioStream("pick.set");
                    name = "Pick Set";
                    break;
                case PICK_ERASE:
                    is = getAudioStream("pick.erase");
                    name = "Pick Erase";
                    break;
                case DRAW:
                    is = getAudioStream("draw");
                    name = "Draw";
                    break;
                case MATCH:
                    is = getAudioStream("match");
                    name = "Match";
                    break;
                case WIN:
                    is = getAudioStream("win");
                    name = "Win";
                    break;
                case LOSE:
                    is = getAudioStream("lose");
                    name = "Lose";
                    break;
                case CREDIT_PAY:
                    is = getAudioStream("credit.pay");
                    name = "Credit Pay";
                    break;
                case CREDIT_DEBIT:
                    is = getAudioStream("credit.debit");
                    name = "Credit Debit";
                    break;
                case THEME_LOAD:
                    is = getAudioStream("theme.load");
                    name = "Theme Loaded";
                    break;
            }
            this.format = null;
            this.samples = null;
            this.hasSound = false;
            if ( is != null ) {
                this.format = is.getFormat();
                this.samples = getSamples(is);
                this.hasSound = true;
            }
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private AudioInputStream getAudioStream(String key)
        throws UnsupportedAudioFileException, IOException {

//        System.out.println( "Sound file [" + key + "]: " +
//                        System.getProperty("keno.theme.path") +
//                        System.getProperty("keno.theme.name") + "/" +
//                        System.getProperty("keno.sound."+ key + ".0")
//        );
//
//        Enumeration<Object> keys = System.getProperties().keys();
//        while ( keys.hasMoreElements()) {
//            String s = (String) keys.nextElement();
//            if ( s.startsWith("keno")) {
//                System.out.println(s + " :: " + System.getProperty(s));
//            }
//        }
        if ( System.getProperty("keno.theme.path") == null ) return null;
        if ( System.getProperty("keno.theme.name") == null ) return null;
        if ( System.getProperty("keno.sound." + key + ".0" ) == null ) return null;

        InputStream is =                 this.getClass().getResourceAsStream(
                        System.getProperty("keno.theme.path") +
                        System.getProperty("keno.theme.name") + "/" +
                        System.getProperty("keno.sound."+ key + ".0")
                );

        if ( is == null ) return null;
        
        return AudioSystem.getAudioInputStream(
            new BufferedInputStream( is )
        );
    }

    /**
    Gets the samples of this sound as a byte array.
     */
    public byte[] getSamples() {
        return samples;
    }

    /**
    Gets the samples from an AudioInputStream as an array
    of bytes.
     */
    private byte[] getSamples(AudioInputStream audioStream) {
        int length = (int) (audioStream.getFrameLength() *
                format.getFrameSize());

        byte[] samp = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return samp;
    }

    public void play() {
        play(NO_DELAY);
    }

    public void play(int delay) {
        // debug
        //System.out.println("Play Sound: " + this.name);
        if (!hasSound) return;
        if (threadCount > 5 ) return;
        threadCount++;
        new SoundEffectsRunner(this, getSamples(), format, delay);
    }

    @Override
    public void themeChanged() {
        initSound();
    }

    public String getName() {
        return name;
    }

    public void playDone() {
        threadCount--;
        if (threadCount < 0 ) threadCount = 0;
    }
}
