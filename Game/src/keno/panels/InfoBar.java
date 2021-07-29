/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import keno.KenoUtils;
import keno.interfaces.ThemeListener;

/**
 *
 * @author mark
 */
public class InfoBar extends JPanel implements ThemeListener {
    private JLabel middleLabel;
    private JLabel rightLabel;
    private JLabel infoButton;
    private JLabel settingsButton;

    public InfoBar() {
        setName("infoBar");
        setPreferredSize(new Dimension(800, 42));
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel icons = new JPanel();
        icons.setLayout(new BoxLayout(icons, BoxLayout.X_AXIS));
        icons.setPreferredSize(new Dimension(256,getHeight()));
        icons.setOpaque(false);

        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
        middle.setOpaque(false);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setPreferredSize(new Dimension(256,getHeight()));
        right.setOpaque(false);


        ImageIcon icon = new ImageIcon(getClass().getResource("/keno/images/information.png"));
        infoButton = new JLabel(icon);
        infoButton.setToolTipText("Information");
        infoButton.setAlignmentY(BOTTOM_ALIGNMENT);
        icon = new ImageIcon(getClass().getResource("/keno/images/settings.png"));
        settingsButton = new JLabel(icon);
        settingsButton.setToolTipText("Settings");
        settingsButton.setAlignmentY(BOTTOM_ALIGNMENT);

        middleLabel = new JLabel("Not a gambling device. Credits have no cash or prize value.");
        middleLabel.setAlignmentX(CENTER_ALIGNMENT);
        middleLabel.setFont(middleLabel.getFont().deriveFont(10.0f));

        rightLabel = new JLabel("Copyright 2009 by Maehem Media, Inc.");
        rightLabel.setAlignmentX(RIGHT_ALIGNMENT);
        rightLabel.setFont(middleLabel.getFont().deriveFont(10.0f));

        
        // Add all the components
        //icons.add(Box.createRigidArea(new Dimension(5,48)));
        icons.add(Box.createHorizontalStrut(5));
        icons.add(infoButton);
        icons.add(Box.createHorizontalStrut(3));
        icons.add(settingsButton);
        icons.add(Box.createHorizontalGlue());

        middle.add(Box.createVerticalGlue());
        middle.add(middleLabel);

        right.add(Box.createVerticalGlue());
        right.add(rightLabel);
        
        add(icons, BorderLayout.WEST);
        add(middle, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    @Override
    public void themeChanged() {
        String textColor = System.getProperty("keno." + this.getName() + ".text.color" , "0xFF000000" );
        //System.out.println( "Change setting:  keno." + getName() + ".text.color" + " " + " current: " + textColorDef + "  about to set: " + textColor);
        //iconLabel.setForeground(KenoUtils.parseHexColor(textColor));
        middleLabel.setForeground(KenoUtils.parseHexColor(textColor));
        rightLabel.setForeground(KenoUtils.parseHexColor(textColor));
    }

    /**
     * @return the infoButton
     */
    public JLabel getAboutButton() {
        return infoButton;
    }

    /**
     * @return the settingsButton
     */
    public JLabel getSettingsButton() {
        return settingsButton;
    }
}
