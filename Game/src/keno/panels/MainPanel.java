/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import keno.KenoUtils;
import keno.SoundEffects;
import keno.ThemeDescriptor;
import keno.ThemeLoader;
import keno.interfaces.GameOverListener;
import keno.interfaces.PickNumberListener;
import keno.ThemeSong;
import keno.interfaces.ThemeListener;
import keno.widgets.AmountPanel;
import keno.widgets.ControlButton;

/**
 *
 * @author mark
 */
public class MainPanel extends JPanel implements GameOverListener, PickNumberListener {

    private Image image;
    private PayoutTablePanel payoutTablePanel = new PayoutTablePanel();
    private NumberPicksPanel numberPicksPanel = new NumberPicksPanel();
    private NumberBoardPanel numberBoardPanel = new NumberBoardPanel(numberPicksPanel, payoutTablePanel);
    private WestPanel westPanel = new WestPanel();
    private JPanel southPanel = new JPanel();
    private InfoBar infoBar = new InfoBar();
    private AmountPanel winAmountPanel = new AmountPanel();
    private ControlsPanel controlsPanel = new ControlsPanel();
    private ControlButton drawButton = getControlsPanel().getButton(ControlsPanel.DRAW_BUTTON);
    private ControlButton eraseButton = getControlsPanel().getButton(ControlsPanel.ERASE_BUTTON);
    private ControlButton quickPickButton = getControlsPanel().getButton(ControlsPanel.QUICK_PICK_BUTTON);
    private ControlButton themesButton = getControlsPanel().getButton(ControlsPanel.THEMES_BUTTON);
    private AmountPanel creditsPanel = getControlsPanel().getCreditsPanel();
    private Marquee marquee = new Marquee();  // Marquee is the top banner.
    private ThemeSong themeSong;
    private boolean hasSound = true;
    private ArrayList<ThemeListener> themeListeners = new ArrayList<ThemeListener>();

    public MainPanel() {
        // BorderLayout is the Java default.  Here for clarity.
        setLayout(new BorderLayout(0, 0));

        initThemeListeners();

        setFont(new Font("Helvetica", Font.PLAIN, 16));

        westPanel.add(payoutTablePanel);
        westPanel.add(Box.createVerticalGlue());
        westPanel.add(winAmountPanel);
        westPanel.add(Box.createVerticalStrut(12));
        westPanel.validate();

        add(marquee, BorderLayout.PAGE_START);
        add(westPanel, BorderLayout.WEST);
        add(numberPicksPanel, BorderLayout.CENTER);
        add(numberBoardPanel, BorderLayout.EAST);

        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setOpaque(false);
        southPanel.add(controlsPanel);
        southPanel.add(infoBar);

        add(southPanel, BorderLayout.PAGE_END);


        revalidate();
        //repaint();
        try {
            themeSong = new ThemeSong();
        } catch (Exception e) {
            hasSound = false;
        }
        hasSound = false;

        creditsPanel.setTitle("Credits");
        creditsPanel.setName("creditsAmountPanel");
        winAmountPanel.setTitle("Win");
        winAmountPanel.setName("winAmountPanel");
        payoutTablePanel.setName("payTable");
        numberPicksPanel.addDrawListener(payoutTablePanel);
        payoutTablePanel.addGameOverListener(winAmountPanel);
        payoutTablePanel.addGameOverListener(creditsPanel);
        payoutTablePanel.addGameOverListener(this);
        numberPicksPanel.addDrawListener(numberBoardPanel.getStatusPanel());
        creditsPanel.setAmount(100);
        winAmountPanel.setAmount(0);
        //mainPanel.doLayout();
        drawButton.setEnabled(false);
        eraseButton.setEnabled(false);

        drawButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButtonActionPerformed(evt);
            }
        });

        eraseButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eraseButtonActionPerformed(evt);
            }
        });

        quickPickButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickPickButtonActionPerformed(evt);
            }
        });

        try {
            System.getProperties().load(this.getClass().getResourceAsStream(
                    gp("keno.theme.path") +
                    gp("keno.theme.name") +
                    ".properties"));
            reloadBackgroundImage();
            notifyThemeListenersThemeChanged();
        } catch (IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    private void initThemeListeners() {
        for (SoundEffects ef : payoutTablePanel.getSoundEffects()) {
            addThemeListener(ef);
        }

        for (SoundEffects ef : controlsPanel.getCreditsPanel().getSoundEffects()) {
            addThemeListener(ef);
        }

        addThemeListener(winAmountPanel);
        addThemeListener(creditsPanel);
        addThemeListener(payoutTablePanel);
        addThemeListener(numberBoardPanel.getStatusPanel());
        addThemeListener(getInfoBar());
        for (ThemeListener tl : numberBoardPanel.getTiles()) {
            addThemeListener(tl);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    @Override
    public void gameOver(int picks, int pay) {
        if (hasSound) {
            themeSong.pause();
        }
        // Add the just picked numbers to theme listener in case the user changes
        // theme in between games.
        for (ThemeListener tl : numberPicksPanel.getPickedNumbers()) {
            addThemeListener(tl);
        }
        if (creditsPanel.getAmount() <= 0) {
            Object[] options = {
                "Add 100 Credits",
                "Quit Game"};
            int n = JOptionPane.showOptionDialog(this,
                    "Add 100 more credits?",
                    "No more credits",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title
            switch (n) {
                case 0:
                    creditsPanel.creditAmount(100);
                    break;
                case 1:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
        themesButton.setEnabled(true);
        eraseButton.setEnabled(true);
        quickPickButton.setEnabled(true);
        drawButton.setEnabled(creditsPanel.getAmount() > 0);
    }

    @Override
    public void isPlayable(boolean playable) {
        drawButton.setEnabled(playable && (creditsPanel.getAmount() > 0));
    }

    @Override
    public boolean picked(int num) {
        eraseButton.setEnabled(true);
        return false;
    }

    @Override
    public void cleared(int num) {
    }

    private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {
        creditsPanel.debitAmount(1);
        eraseButton.setEnabled(false);
        quickPickButton.setEnabled(false);
        themesButton.setEnabled(false);
        // Remove the just picked numbers from theme listener because we are
        // about to delete them all.
        for (ThemeListener tl : numberPicksPanel.getPickedNumbers()) {
            removeThemeListener(tl);
        }
        numberPicksPanel.doDraw();
        drawButton.setEnabled(false);
        winAmountPanel.setAmount(0);
        //statusPanel.repaint();
        if (hasSound) {
            themeSong.play();
        }
    }

    private void quickPickButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int picks = payoutTablePanel.getPickCount();
        if (picks == 0) {
            picks = 6;
        }
        eraseButtonActionPerformed(null);
        eraseButton.setEnabled(true);
        for (int i = 0; i < picks; i++) {
            numberBoardPanel.pickRandom();
        }
    }

    private void eraseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        numberPicksPanel.clearPicks();
        eraseButton.setEnabled(false);
    }

    public void addThemeListener(ThemeListener l) {
        themeListeners.add(l);
    }

    public void removeThemeListener(ThemeListener l) {
        themeListeners.remove(l);
    }

    public void notifyThemeListenersThemeChanged() {
        //System.out.println("\n\nTheme Changed:");
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (ThemeListener l : themeListeners) {
            l.themeChanged();
        }
    }

    /**
     * @return the controlsPanel
     */
    public ControlsPanel getControlsPanel() {
        return controlsPanel;
    }

    public void setTheme(ThemeDescriptor td) {
        try {
            URL url = td.getJarUrl();

            // null URL is a base resource.
            if (url != null) {
                if (!KenoUtils.checkURL(url)) {
                    return;
                }
                //System.out.println("Load Jar URL: " + url.toString());
                new ThemeLoader(url);
            }

            //System.out.println("Set theme name to: " + td.getName() + " :: " + td.getId());
            System.setProperty("keno.theme.name", td.getId());
            String resource =
                    gp("keno.theme.path") +
                    gp("keno.theme.name") +
                    ".properties";
            InputStream is = this.getClass().getResourceAsStream(resource);
            if (is == null) {
                System.out.println("Could not load properties file: " + resource);
                return;
            }
            System.getProperties().load(is);
            reloadBackgroundImage();
            notifyThemeListenersThemeChanged();
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reloadBackgroundImage() {
        String fileName =
                gp("keno.theme.path") +
                gp("keno.theme.name") + "/" +
                gp("keno.background.0");
        try {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (Exception e) {
            System.err.println("Warning: Could not find or load background image.");
            System.err.println("    Image: " + fileName);
        }

    }

    private String gp(String key) {
        return System.getProperty(key);
    }

    /**
     * @return the infoBar
     */
    public InfoBar getInfoBar() {
        return infoBar;
    }

}
