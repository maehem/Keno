/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NumberBoardStatusPanel.java
 *
 * Created on Oct 6, 2009, 2:43:24 PM
 */

package keno.widgets;

import java.awt.Dimension;
import java.awt.Font;
import keno.KenoUtils;
import keno.interfaces.DrawNumberListener;
import keno.interfaces.ThemeListener;

/**
 *
 * @author mark
 */
public class NumberBoardStatusPanel extends javax.swing.JPanel implements DrawNumberListener, ThemeListener {
    private javax.swing.JLabel statusLabel;

    /** Creates new form NumberBoardStatusPanel */
    public NumberBoardStatusPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setName("statusPanel");
        setOpaque(false);
        setPreferredSize(new Dimension(500, 42));

        statusLabel = new javax.swing.JLabel();
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 24.0f));

        statusLabel.setText("GAME OVER"); // NOI18N
        statusLabel.setName("statusLabel"); // NOI18N
        add(statusLabel);
    }

    @Override
    public boolean numberDrawn(int n) { return false;}

    @Override
    public void clear() {}

    @Override
    public void gameStarted() {
        setVisible(false);
        statusLabel.setText("GOOD LUCK!!!!");
        setVisible(true);
    }

    @Override
    public void gameOver() {
        setVisible(false);
        statusLabel.setText("GAME OVER");
        setVisible(true);
    }

    @Override
    public void clearPicks() { }

    @Override
    public void themeChanged() {
        String textColor = System.getProperty("keno." + this.getName() + ".text.color" , "0xFF000000" );
        statusLabel.setForeground(KenoUtils.parseHexColor(textColor));
    }


}
