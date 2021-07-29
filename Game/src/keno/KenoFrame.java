/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import keno.panels.AboutPanel;
import keno.panels.ControlsPanel;
import keno.panels.MachinePanel;
import keno.panels.MainPanel;
import keno.panels.SettingsPanel;

/**
 *
 * @author mark
 */
public class KenoFrame extends JFrame implements ActionListener, MouseListener {

    private MachinePanel machinePanel;
    private MainPanel mainPanel;
    private AboutPanel aboutPanel;
    private SettingsPanel settingsPanel;

    private JPanel contentPanel = new JPanel(new CardLayout());
    private static final String MAIN = "Main", THEMES = "Themes",
                ABOUT = "About", SETTINGS = "Settings";

    public KenoFrame() throws IOException {
        System.getProperties().load(
                this.getClass().getResourceAsStream(
                "/keno/default.properties"));

        this.setTitle(System.getProperty("keno.title", "Keno - default key"));

        // Set the default close action.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel.setPreferredSize(new Dimension(800,600));
        this.getContentPane().add(contentPanel);
        pack();
        setResizable(false);

        mainPanel = new MainPanel();
        machinePanel = new MachinePanel();
        aboutPanel = new AboutPanel();
        settingsPanel = new SettingsPanel();

        contentPanel.add(mainPanel, MAIN);
        contentPanel.add(machinePanel, THEMES);
        contentPanel.add(aboutPanel, ABOUT);
        contentPanel.add(settingsPanel, SETTINGS);

        pack();

        mainPanel.getControlsPanel().getButton(
                ControlsPanel.THEMES_BUTTON).addActionListener(this);
        machinePanel.getReturnButton().addActionListener(this);
        aboutPanel.getReturnButton().addActionListener(this);
        settingsPanel.getReturnButton().addActionListener(this);
        mainPanel.getInfoBar().getAboutButton().addMouseListener(this);
        mainPanel.getInfoBar().getSettingsButton().addMouseListener(this);

        //setSize(1024, 784);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        //System.out.println("Frame button pressed: \n[" + cmd + "]");

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        if (cmd.equals("Themes")) {
            cl.show(contentPanel, THEMES);
        } else if (cmd.equals("Done")) {
            mainPanel.setTheme( machinePanel.getChooserPanel().getSelectedThemeDescriptor());
            cl.show(contentPanel, MAIN);
        } else if ( cmd.equals("Return to Game")) {
            cl.show(contentPanel, MAIN);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        if ( e.getSource() == mainPanel.getInfoBar().getAboutButton() ) {
            cl.show(contentPanel, ABOUT);
        } else
        if ( e.getSource() == mainPanel.getInfoBar().getSettingsButton() ) {
            cl.show(contentPanel, SETTINGS);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
