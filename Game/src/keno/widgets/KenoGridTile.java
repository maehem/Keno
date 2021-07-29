/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KenoGridTile.java
 *
 * Created on Oct 4, 2009, 1:16:52 PM
 */
package keno.widgets;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import keno.KenoUtils;
import keno.interfaces.DrawNumberListener;
import keno.interfaces.PickNumberListener;
import keno.interfaces.ThemeListener;

/**
 *
 * @author mark
 */
public class KenoGridTile extends javax.swing.JPanel implements DrawNumberListener, ThemeListener {

    private JLabel label;
    private int number;
    private boolean userPicked = false;
    private ArrayList<PickNumberListener> pickListeners = new ArrayList<PickNumberListener>();
    private boolean clickable = true;

    private static final Color COLOR_TEXT_DEFAULT = new Color(0xff000000, true);
    private static final Color COLOR_TEXT_PICKED  = new Color(0xff000000, true);
    private static final Color COLOR_TEXT_DRAWN   = new Color(0xff000000, true);
    private static final Color COLOR_TEXT_MATCH   = new Color(0xff000000, true);

    private static final Color COLOR_BACKGROUND_DEFAULT = new Color(0x77ffffff, true);
    private static final Color COLOR_BACKGROUND_PICKED  = new Color(0x8000ff00, true);
    private static final Color COLOR_BACKGROUND_DRAWN   = new Color(0x44000000, true);
    private static final Color COLOR_BACKGROUND_MATCH   = new Color(0xaaff0000, true);

    private Color defaultTextColor  = COLOR_TEXT_DEFAULT;
    private Color pickedTextColor   = COLOR_TEXT_PICKED;
    private Color drawnTextColor    = COLOR_TEXT_DRAWN;
    private Color matchTextColor    = COLOR_TEXT_MATCH;

    private Color defaultBackgroundColor  = COLOR_BACKGROUND_DEFAULT;
    private Color pickedBackgroundColor   = COLOR_BACKGROUND_PICKED;
    private Color drawnBackgroundColor    = COLOR_BACKGROUND_DRAWN;
    private Color matchBackgroundColor    = COLOR_BACKGROUND_MATCH;
    
    /** Creates new form KenoGridTile */
    public KenoGridTile(int num) {
        this.number = num;
        initComponents();
        label.setText(String.valueOf(num));
        clear();
        setName("gridTile");
    }

    private void initComponents() {

        label = new javax.swing.JLabel();
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED,
                Color.WHITE,
                Color.LIGHT_GRAY,
                Color.GRAY,
                Color.LIGHT_GRAY)); // NOI18N
        setName("KenoGridTile"); // NOI18N
        setMaximumSize(  new java.awt.Dimension(54, 37));
        setMinimumSize(  new java.awt.Dimension(54, 37));
        setPreferredSize(new java.awt.Dimension(54, 37));
        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("88"); // NOI18N
        label.setName("label"); // NOI18N
        label.setFont(getFont().deriveFont(Font.BOLD, 24.0f));

        add(label);

    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (!clickable) {
            return;
        }
        // There is a bug in Java (or Apple's version) that does not
        // properly repaint items with alpha (translucent) colors.
        // The workaround is to setVisible(false) and then setVisible(true)
        // as part of your update code.
        setVisible(false);
        if (userPicked) {
            clearPick();
        } else {
            setPick();
        }
        setVisible(true);
        repaint();
    }

    @Override
    public boolean numberDrawn(int n) {
        if ((n + 1) == number) {
            if (userPicked) {
                setBackground(matchBackgroundColor);
                label.setForeground(matchTextColor);
                return true;
            } else {
                setBackground(drawnBackgroundColor);
                label.setBackground(drawnTextColor);
            }
        }
        return false;
    }

    @Override
    public void clear() {
        if (userPicked) {
            label.setForeground(pickedTextColor);
            setBackground(pickedBackgroundColor);
        } else {
            label.setForeground(defaultTextColor);
            setBackground(defaultBackgroundColor);
        }
    }

    public void clearPick() {
        userPicked = false;
        setBackground(defaultBackgroundColor);
        label.setForeground(defaultTextColor);
        notifyPickListenersCleared();
    }

    public void setPick() {
        userPicked = true;
        setBackground(pickedBackgroundColor);
        label.setForeground(pickedTextColor);
        notifyPickListenersPicked();
    }

    public void addPickListener(PickNumberListener l) {
        pickListeners.add(l);
    }

    public void removePickListener(PickNumberListener l) {
        pickListeners.remove(l);
    }

    public void notifyPickListenersPicked() {
        for (PickNumberListener l : pickListeners) {
            if (!l.picked(number)) {
                userPicked = false;
                setBackground(defaultBackgroundColor);
            }
        }
    }

    public void notifyPickListenersCleared() {
        for (PickNumberListener l : pickListeners) {
            l.cleared(number);
        }
    }

    @Override
    public void gameOver() {
        clickable = true;
    }

    @Override
    public void clearPicks() {
        clearPick();
    }

    @Override
    public void gameStarted() {
        clickable = false;
    }

    public boolean isPicked() {
        return userPicked;
    }

    @Override
    public void setBackground(Color bg) {
        setVisible(false);
        super.setBackground(bg);
        setVisible(true);
    }

    @Override
    public void themeChanged() {

        String textPropPrefix = "keno." + this.getName() + ".text.color.";
        this.defaultTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "normal", 
                Integer.toHexString(COLOR_TEXT_DEFAULT.getRGB()));
        this.pickedTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "pick",
                Integer.toHexString(COLOR_TEXT_PICKED.getRGB()));
        this.drawnTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "drawn",
                Integer.toHexString(COLOR_TEXT_DRAWN.getRGB()));
        this.matchTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "match",
                Integer.toHexString(COLOR_TEXT_MATCH.getRGB()));

        String backgroundPropPrefix = "keno." + this.getName() + ".background.color.";
        this.defaultBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "normal",
                Integer.toHexString(COLOR_BACKGROUND_DEFAULT.getRGB()));
        this.pickedBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "pick",
                Integer.toHexString(COLOR_BACKGROUND_PICKED.getRGB()));
        this.drawnBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "drawn",
                Integer.toHexString(COLOR_BACKGROUND_DRAWN.getRGB()));
        this.matchBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "match",
                Integer.toHexString(COLOR_BACKGROUND_MATCH.getRGB()));

        clear();
    }
}
