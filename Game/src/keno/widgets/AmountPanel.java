/*
 * AmountPanel.java
 *
 * Created on Oct 5, 2009, 12:07:39 PM
 */
package keno.widgets;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import keno.KenoUtils;
import keno.interfaces.GameOverListener;
import keno.SoundEffects;
import keno.interfaces.ThemeListener;

/**
 *
 * @author mark
 */
public class AmountPanel extends javax.swing.JPanel implements GameOverListener, Runnable, ThemeListener {

    private javax.swing.JLabel amount;
    public static final boolean ANIMATE = true, NO_ANIMATE = false;
    private int payAmount = 0;
    private SoundEffects soundPay = new SoundEffects(SoundEffects.Sound.CREDIT_PAY);
    private SoundEffects soundDebit = new SoundEffects(SoundEffects.Sound.CREDIT_DEBIT);
    private Thread t = null;
    ArrayList<SoundEffects> soundEffectList;

    /** Creates new form AmountPanel */
    public AmountPanel() {
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        Dimension size = new Dimension(150, 75);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Title",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                getFont().deriveFont(Font.BOLD, 22.0f)));

        setName("AmountPanel"); // NOI18N

        amount = new javax.swing.JLabel();
        amount.setFont(getFont().deriveFont(Font.BOLD, 30.0f));
        amount.setText("8888888"); // NOI18N
        amount.setName("amount"); // NOI18N

        add(amount);

        soundEffectList = new ArrayList<SoundEffects>();
        soundEffectList.add(soundPay);
        soundEffectList.add(soundDebit);
    }

    public ArrayList<SoundEffects> getSoundEffects() {
        return soundEffectList;
    }

    @Override
    public void gameOver(int picks, int pay) {
        setAmount(pay);
    }

    public void setTitle(String title) {
        ((TitledBorder) this.getBorder()).setTitle(title);
    }

    public int getAmount() {
        return Integer.parseInt(amount.getText());
    }

    public void setAmount(int num) {
        setVisible(false);
        amount.setText(String.valueOf(num));
        setVisible(true);
    }

    public void setAmount(int num, boolean animate) {
        if (animate) {
            payAmount = num - getAmount();
            // Do animation
            t = new Thread(this);
            t.start();
        } else {
            setAmount(num);
        }
    }

    public void debitAmount(int num) {
        creditAmount(-num);
    }

    public void creditAmount(int num) {
        if (num == 0) {
            return;
        }
        if (num > 0) {
            soundPay.play();
        } else {
            soundDebit.play();
        }
        creditAmount(num, NO_ANIMATE, 0);
    }

    public void creditAmount(int num, boolean animate, long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
        }
        setAmount(getAmount() + num, animate);
    }

    @Override
    public void isPlayable(boolean playable) {
    }

    @Override
    public void run() {
        int payIncrement = payAmount / 40;
        int sleepTime = (payIncrement > 20 ? 80 : 120);

        if (payIncrement <= 1) {
            payIncrement = 1;
        }
        while (payAmount > 0) {
            if (payIncrement > payAmount) {
                creditAmount(payAmount);
                payAmount = 0;
            } else {
                creditAmount(payIncrement);
                payAmount -= payIncrement;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
            }
        }
        // Zero it out for good housekeeping.
        payAmount = 0;
    }

    @Override
    public void themeChanged() {
        //keno.winPanel.text.color
        //String textColorDef = System.getProperty("keno." + this.getName() + ".text.color" , "Not Set" );
        String textColor = System.getProperty("keno." + this.getName() + ".text.color" , "0xFF000000" );
        //System.out.println( "Change setting:  keno." + getName() + ".text.color" + " " + " current: " + textColorDef + "  about to set: " + textColor);
        amount.setForeground(KenoUtils.parseHexColor(textColor));
        TitledBorder b = (TitledBorder) this.getBorder();
        b.setTitleColor(KenoUtils.parseHexColor(textColor));
    }
}
