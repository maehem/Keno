/*
 * About Panel
 */

package keno.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import keno.KenoUtils;

/**
 *
 * @author mark
 */
public class AboutPanel extends SubPanel {
    Font fontS = getFont().deriveFont(18.0f);
    Font fontM = getFont().deriveFont(24.0f);
    Font fontMB = fontM.deriveFont(Font.BOLD);

    public AboutPanel() {
        super("About");
        Icon icon = KenoUtils.getIconFromClassPath(this, "/keno/images/information-lg.png");
        getIcon().setIcon(icon);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Game Icon/Logo
        contentPanel.add(gameLogo());
        contentPanel.add(Box.createVerticalStrut(10));
        // Game Name
        addGameName();
        // Version
        contentPanel.add(Box.createVerticalStrut(10));
        addGameVersion();
        // Gap

        contentPanel.add(Box.createVerticalStrut(10));
        // Copyright
        addCopyrightInfo();

        // Gap
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(bottomLogos());
    }

    private JPanel gameLogo() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        JLabel icon = KenoUtils.getWebLabelImage(
            KenoUtils.getIconFromClassPath(
                this, "/keno/images/kenbun-logo.png"
            ),
            "http://maehem.com"
        );
        p.add(icon);
        p.add(Box.createHorizontalGlue());
        p.validate();

        return p;
    }

    private void addGameName() {
        JLabel c = new JLabel(gp("keno.title"));
        c.setFont(fontM);
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(c);
    }

    private void addGameVersion() {
        JLabel c = new JLabel("Version: " + gp("keno.version"));
        c.setFont(fontM);
        c.setForeground(new Color( 0xbb000000, true));
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(c);
    }

    private void addCopyrightInfo() {
        JLabel c = new JLabel(gp("keno.copyright"));
        c.setFont(fontS);
        c.setForeground(new Color( 0x99000000, true));
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(c);
    }

    private JPanel companyLogo() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        //p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        JLabel icon = KenoUtils.getWebLabelImage(
            KenoUtils.getIconFromClassPath(
                this, "/keno/images/maehem.png"
            ),
            "http://maehem.com"
        );
        icon.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        p.add(icon);
        p.add(Box.createHorizontalStrut(20));
        JLabel l = new JLabel("maehem.com" );
        l.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        l.setFont(fontM);
        l.setForeground(new Color( 0x99000000, true));
        p.add(l);
        p.add(Box.createHorizontalGlue());
        p.validate();

        return p;
    }

    private JPanel bottomLogos() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(companyLogo());
        p.add(Box.createHorizontalGlue());
        //p.add(madeWithNetbeans());
        //p.add(poweredByJava());
        p.validate();

        return p;
    }
    private JLabel poweredByJava() {
        JLabel l =  KenoUtils.getWebLabelImage(
                    "http://java.com/im/get_powered_med.jpg", 
                    "http://www.java.com?cid=2436"
                );
        l.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        return l;
    }

    private JLabel madeWithNetbeans() {
        JLabel l = KenoUtils.getWebLabelImage(
                    KenoUtils.getIconFromClassPath(this, "/keno/images/netbeans.jpg"),
                    "http://netbeans.org"
                );
        l.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        return l;
    }

    private String gp(String key) {
        return System.getProperty(key);
    }

}
