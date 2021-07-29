/*
 * Keno Panel for Left-Side of Play Field
 */

package keno.panels;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class WestPanel extends JPanel {
    public WestPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(165, 420));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
