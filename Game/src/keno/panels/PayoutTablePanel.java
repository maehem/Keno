/*
 * PayoutTablePanel.java
 *
 * Created on Oct 3, 2009, 6:13:16 PM
 */
package keno.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import keno.KenoUtils;
import keno.interfaces.DrawNumberListener;
import keno.interfaces.GameOverListener;
import keno.interfaces.PickNumberListener;
import keno.SoundEffects;
import keno.interfaces.ThemeListener;
import keno.widgets.PayTable;

/**
 *
 * @author mark
 */
public class PayoutTablePanel extends javax.swing.JPanel implements DrawNumberListener, PickNumberListener, ThemeListener {

    private static final int MY_WIDTH = 180;
    private static final int MY_HEIGHT = 270;
    private boolean isPlaying = false;
    private boolean isPlayable = false;
    private JLabel message = new JLabel("Pick Some Numbers");
    private ArrayList<Integer> picks = new ArrayList<Integer>();
    private int maxPicks = 10;
    private int hits = 0;
    private int winAmount = 0;
    private ArrayList<GameOverListener> gameOverListeners = new ArrayList<GameOverListener>();
    private SoundEffects drawSound, matchSound,
            pickSetSound, pickEraseSound,
            winSound, loseSound,
            themeOnLoadSound;
    private boolean hasSound = true;
    ArrayList<SoundEffects> soundEffectList;

    private static final Color COLOR_TEXT_DEFAULT = Color.BLACK;
    private static final Color COLOR_TEXT_MATCH = Color.RED;

    private Color textNormalColor  = COLOR_TEXT_DEFAULT;
    private Color textMatchColor  = COLOR_TEXT_MATCH;


    /** Creates new form PayoutTablePanel */
    public PayoutTablePanel() {
        initComponents();
        initPanel();
        initSound();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(MY_WIDTH, MY_HEIGHT));
        setMaximumSize(new Dimension(MY_WIDTH, MY_HEIGHT));
        setOpaque(false);
        setName("PayoutTable");
        message.setFont(message.getFont().deriveFont(14.0f));
        message.setAlignmentX(CENTER_ALIGNMENT);
    }

    private void initPanel() {
        removeAll();
        setVisible(false);
        addPayTableTop();
        isPlayable = refactorPickWins();
        validate();
        setVisible(true);
        repaint();
        notifyIsPlayableListeners();
    }

    private void initSound() {
        try {
            drawSound = new SoundEffects(SoundEffects.Sound.DRAW);
            matchSound = new SoundEffects(SoundEffects.Sound.MATCH);
            pickSetSound = new SoundEffects(SoundEffects.Sound.PICK_SET);
            pickEraseSound = new SoundEffects(SoundEffects.Sound.PICK_ERASE);
            winSound = new SoundEffects(SoundEffects.Sound.WIN);
            loseSound = new SoundEffects(SoundEffects.Sound.LOSE);
            themeOnLoadSound = new SoundEffects(SoundEffects.Sound.THEME_LOAD);

            soundEffectList = new ArrayList<SoundEffects>();
            soundEffectList.add(drawSound);
            soundEffectList.add(matchSound);
            soundEffectList.add(pickSetSound);
            soundEffectList.add(pickEraseSound);
            soundEffectList.add(winSound);
            soundEffectList.add(loseSound);
            soundEffectList.add(themeOnLoadSound);
        } catch (Exception e) {
            hasSound = false;
        }
    }

    public ArrayList<SoundEffects> getSoundEffects() {
        return soundEffectList;
    }

    private void addPayTableTop() {
        add(Box.createVerticalStrut(25));
        add(message);
        add(Box.createVerticalStrut(10));
    }

    public void setMessage(String m) {
        message.setText(m);
    }

    public String getMessage() {
        return message.getText();
    }

    @Override
    public boolean numberDrawn(int n) {
        for (Integer i : picks) {
            if (i.intValue() == (n + 1)) {
                hits++;
                if (hasSound) {
                    matchSound.play();
                }
                initPanel();
                return false;
            }
        }
        if (hasSound) {
            drawSound.play();
        }
        return false;
    }

    @Override
    public void clear() {
        hits = 0;
        winAmount = 0;
        initPanel();
        // Reset color of paylines.
    }

    @Override
    public boolean picked(int num) {
        if (picks.size() < maxPicks) {
            pickSetSound.play();
            picks.add(new Integer(num));
            message.setText("Picked: " + picks.size());
            initPanel();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cleared(int num) {
        if (picks.remove(Integer.valueOf(num))) {
            // Bug in Apple's Java. Crashes app when too many sounds played.
            pickEraseSound.play();
            initPanel();
        }
    }

    private boolean refactorPickWins() {
        boolean playable = false;
        int size = picks.size();
        //System.out.println("Picks size: " + size);
        add(new PickWinPanel(textNormalColor));
        if (size > 0) {
            int pay[] = PayTable.pay[size - 1];
            int count = 1;
            winAmount = 0;
            for (int i : pay) {
                PickWinPanel p = new PickWinPanel(count, i, textNormalColor, textMatchColor);
                if (count == hits) {
                    p.setHitColor();
                    winAmount = i;
                }
                add(p);
                playable = true;
                count++;
            }
        }
        return playable && !isPlaying;
    }

    @Override
    public void gameOver() {
        // Calculate winnings
        isPlaying = false;
        if (winAmount > 0) {
            winSound.play();
        } else {
            loseSound.play();
        }
        initPanel();
        notifyGameOverListeners();
        notifyIsPlayableListeners();
    }

    public void addGameOverListener(GameOverListener l) {
        gameOverListeners.add(l);
    }

    public void removeGameOverListener(GameOverListener l) {
        gameOverListeners.remove(l);
    }

    public void notifyGameOverListeners() {
        for (GameOverListener l : gameOverListeners) {
            l.gameOver(hits, winAmount);
        }
    }

    public void notifyIsPlayableListeners() {
        for (GameOverListener l : gameOverListeners) {
            l.isPlayable(!isPlaying && isPlayable);
        }
    }

    @Override
    public void clearPicks() {
        isPlayable = false;
    }

    @Override
    public void gameStarted() {
        isPlaying = true;
    }

    public int getPickCount() {
        return picks.size();
    }

    @Override
    public void themeChanged() {
        themeOnLoadSound.play();
        String prefix = "keno." + this.getName() + ".text.color.";
        this.textNormalColor = KenoUtils.parseHexColorSystemProperty(
                prefix + "normal",
                Integer.toHexString(COLOR_TEXT_DEFAULT.getRGB()));
        this.textMatchColor = KenoUtils.parseHexColorSystemProperty(
                prefix + "match",
                Integer.toHexString(COLOR_TEXT_MATCH.getRGB()));
        message.setForeground(this.textNormalColor);

        initPanel();
    }
}
