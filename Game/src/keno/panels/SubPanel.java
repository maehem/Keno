/*
 * SubPanel  - A convenient JPanel for 'settings', 'themes', and 'about' panes.
 */

package keno.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import keno.widgets.ControlButton;

/**
 *
 * @author mark
 */
public class SubPanel extends JPanel {
    private JLabel icon = new JLabel();
    private JLabel title = new JLabel();
    private JPanel headingPanel = new JPanel();
    protected JPanel contentPanel = new JPanel();
    private JPanel controlsPanel = new JPanel();
    private ControlButton returnButton = new ControlButton("Return to Game");
    Image backgroundImage;

    public SubPanel() {
        setLayout(new BorderLayout(20, 20));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 48.0f));
        title.setForeground(new Color( 0x80000000, true));

        headingPanel.setLayout(new BoxLayout(getHeadingPanel(), BoxLayout.X_AXIS));
        headingPanel.setPreferredSize(new Dimension(1024,96));
        headingPanel.setOpaque(false);
        headingPanel.add(Box.createHorizontalStrut(20));
        headingPanel.add(icon);
        headingPanel.add(Box.createHorizontalStrut(20));
        headingPanel.add(title);
        headingPanel.add(Box.createHorizontalGlue());

        controlsPanel.setLayout(new BoxLayout(getControlsPanel(), BoxLayout.X_AXIS));
        controlsPanel.setPreferredSize(new Dimension(1024, 96));
        controlsPanel.setOpaque(false);
        controlsPanel.add(Box.createHorizontalGlue());
        controlsPanel.add(returnButton);
        controlsPanel.add(Box.createHorizontalGlue());

        
        add( Box.createHorizontalStrut(72), BorderLayout.WEST);
        add( Box.createHorizontalStrut(72), BorderLayout.EAST);

        add(headingPanel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);

        String fileName = "/keno/images/brushedmetal.jpg";
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (Exception e) {
            System.err.println("Warning: Could not find or load background image for ABOUT panel.");
            System.err.println("    Image: " + fileName);
        }
    }

    public SubPanel( Icon icon, String titleText ) {
        this(titleText);
        this.icon.setIcon(icon);
    }
    
    public SubPanel( String titleText ) {
        this();
        this.title.setText(titleText);        
    }

    /**
     * @return the icon
     */
    public JLabel getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(JLabel icon) {
        if ( icon == null ) icon = new JLabel();
        icon.setPreferredSize(new Dimension (128,128));
        this.icon = icon;

        // Need clear panel and reload components?
        // or tweek the icon?
    }

    /**
     * @return the title
     */
    public JLabel getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String titleText) {
        this.title.setText(titleText);
    }

    /**
     * @return the headingPanel
     */
    public JPanel getHeadingPanel() {
        return headingPanel;
    }

    /**
     * @return the contentPanel
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }

    /**
     * @param contentPanel the contentPanel to set
     */
    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    /**
     * @return the controlsPanel
     */
    public JPanel getControlsPanel() {
        return controlsPanel;
    }

    /**
     * @return the returnButton
     */
    public ControlButton getReturnButton() {
        return returnButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

}
