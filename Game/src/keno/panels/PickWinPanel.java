/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PickWinPanel.java
 *
 * Created on Oct 3, 2009, 6:35:09 PM
 */

package keno.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

/**
 *
 * @author mark
 */
public class PickWinPanel extends javax.swing.JPanel {
    private javax.swing.JLabel numberLabel;
    private javax.swing.JLabel payLabel;
    private Color textMatchColor = Color.RED;

    public PickWinPanel(Color textColor) {
        initComponents();
        numberLabel.setForeground(textColor);
        payLabel.setForeground(textColor);

    }

    /** Creates new form PickWinPanel */
    public PickWinPanel( int num, int pay, Color textNormalColor, Color textMatchColor) {
        this(textNormalColor);
        numberLabel.setText(String.valueOf(num));
        payLabel.setText(String.valueOf(pay));
        this.textMatchColor = textMatchColor;

    }

    private void initComponents() {
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(130,18));
        setMaximumSize(new Dimension(130,18));

        numberLabel = new javax.swing.JLabel();
        payLabel = new javax.swing.JLabel();
        payLabel.setAlignmentX(RIGHT_ALIGNMENT);
        payLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberLabel.setFont(getFont().deriveFont(Font.BOLD, 14.0f));
        payLabel.setFont(getFont().deriveFont(Font.BOLD, 14.0f));

        setName("PickWinPanel"); 
        setOpaque(false);

        numberLabel.setText("Match");
        numberLabel.setName("numberLabel");

        payLabel.setText("Pay");
        payLabel.setName("payLabel");

        add(numberLabel);
        add(payLabel);

    }

    public void setHitColor() {
        numberLabel.setForeground(textMatchColor);
        payLabel.setForeground(textMatchColor);
    }


}
