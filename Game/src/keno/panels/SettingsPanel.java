/*
 * Settings Panel
 */

package keno.panels;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author mark
 */
public class SettingsPanel extends SubPanel {
    public SettingsPanel() {
        super("Settings");
        Icon icon = new ImageIcon(getClass().getResource("/keno/images/settings-lg.png"));
        getIcon().setIcon(icon);
    }
}
