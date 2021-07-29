/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno.panels;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import keno.widgets.AmountPanel;
import keno.widgets.ControlButton;
import keno.widgets.CreditsAmountPanel;

/**
 *
 * @author mark
 */
public class ControlsPanel extends JPanel {
    public static final int DRAW_BUTTON = 1, ERASE_BUTTON = 2, QUICK_PICK_BUTTON = 3, THEMES_BUTTON = 4;
    private ControlButton themesButton = new ControlButton("Themes");
    private ControlButton drawButton = new ControlButton("Draw");
    private ControlButton eraseButton = new ControlButton("Erase");
    private ControlButton quickPickButton = new ControlButton(("Quick Pick"));
    private CreditsAmountPanel credits = new CreditsAmountPanel();

    public ControlsPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(800,88));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(225, this.getHeight()));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.add( Box.createHorizontalStrut(5));
        leftPanel.add( themesButton );
        leftPanel.add( Box.createHorizontalGlue());
        
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(quickPickButton);
        rightPanel.add( Box.createHorizontalStrut(5));
        rightPanel.add(eraseButton);
        rightPanel.add( Box.createHorizontalGlue());
        rightPanel.add(credits);
        rightPanel.add( Box.createHorizontalGlue());
        rightPanel.add(drawButton);
        rightPanel.add( Box.createHorizontalStrut(15));

        add(leftPanel);
        add(rightPanel);
        
        doLayout();
        revalidate();
    }

    public AmountPanel getCreditsPanel() {
        return credits;
    }

    public ControlButton getButton(int button) {
        switch( button ) {
            case DRAW_BUTTON:       return drawButton;
            case ERASE_BUTTON:      return eraseButton;
            case QUICK_PICK_BUTTON: return quickPickButton;
            case THEMES_BUTTON:     return themesButton;
        }
        return null;
    }
}
