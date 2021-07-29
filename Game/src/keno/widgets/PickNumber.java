/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PickNumber.java
 *
 * Created on Oct 3, 2009, 7:16:04 PM
 */
package keno.widgets;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import keno.KenoUtils;
import keno.interfaces.ThemeListener;

/**
 *
 * @author mark
 */
public class PickNumber extends javax.swing.JPanel implements ThemeListener {

    private javax.swing.JLabel label;
    private static final Color COLOR_TEXT_DEFAULT = new Color(0xff000000, true);
    private static final Color COLOR_TEXT_MATCH = new Color(0xff000000, true);
    private static final Color COLOR_BACKGROUND_DEFAULT = new Color(0x77ffffff, true);
    private static final Color COLOR_BACKGROUND_MATCH = new Color(0xaaff0000, true);
    private Color defaultTextColor = COLOR_TEXT_DEFAULT;
    private Color matchTextColor = COLOR_TEXT_MATCH;
    private Color defaultBackgroundColor = COLOR_BACKGROUND_DEFAULT;
    private Color matchBackgroundColor = COLOR_BACKGROUND_MATCH;

    /** Creates new form PickNumber */
    private PickNumber() {
        initComponents();
    }

    public PickNumber(int r) {
        this();
        this.label.setText(Integer.toString(r));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setName("pickTile");
        setLayout(new FlowLayout(FlowLayout.CENTER,0,2));
        label = new javax.swing.JLabel();
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16.0f));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("88"); // NOI18N
        label.setName("PickNumber"); // NOI18N

        setBorder(
                javax.swing.BorderFactory.createBevelBorder(
                javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(28, 28));

        // Load the colors from the System properties.
        themeChanged();
        add(label);
    }

    public void clear() {
        label.setForeground(defaultTextColor);
        setBackground(defaultBackgroundColor);
    }

    public void setMatch() {
        label.setForeground(matchTextColor);
        setBackground(matchBackgroundColor);
    }

    @Override
    public void themeChanged() {

        String textPropPrefix = "keno." + this.getName() + ".text.color.";
        this.defaultTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "normal",
                Integer.toHexString(COLOR_TEXT_DEFAULT.getRGB()));
        this.matchTextColor = KenoUtils.parseHexColorSystemProperty(
                textPropPrefix + "match",
                Integer.toHexString(COLOR_TEXT_MATCH.getRGB()));

        String backgroundPropPrefix = "keno." + this.getName() + ".background.color.";
        this.defaultBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "normal",
                Integer.toHexString(COLOR_BACKGROUND_DEFAULT.getRGB()));
        this.matchBackgroundColor = KenoUtils.parseHexColorSystemProperty(
                backgroundPropPrefix + "match",
                Integer.toHexString(COLOR_BACKGROUND_MATCH.getRGB()));

        clear();
    }
}
