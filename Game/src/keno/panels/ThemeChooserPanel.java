/*
 * Theme Chooser Panel
 */
package keno.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import keno.ThemeDescriptor;
import keno.ThemePackList;

/**
 *
 * @author mark
 */
public class ThemeChooserPanel extends JPanel implements MouseListener {

    private JPanel itemPanel = new JPanel();
    private int selectedThemeIndex = 0;

    public ThemeChooserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        add(scrollPane, BorderLayout.CENTER);

        ArrayList<ThemeDescriptor> tpl = getThemePackList();
        for (ThemeDescriptor td : tpl) {
            ThemeDetailsPanel tdp = new ThemeDetailsPanel(td);
            itemPanel.add(tdp);
            tdp.clear();
            tdp.addMouseListener(this);
        }


        selectItem(selectedThemeIndex);
        validate();
        scrollPane.getVerticalScrollBar().setUnitIncrement(getSelectedItemPanel().getCreatedHeight());
    }

    private ArrayList<ThemeDescriptor> getThemePackList() {
        try {
            URL listURL = new URL(System.getProperty("keno.theme.plugins.url"));
            ThemePackList themePack = new ThemePackList(listURL);
            // Debug
//            for (ThemeDescriptor d : themePack.getThemes()) {
//                System.out.println(d.toString());
//            }
            return themePack.getThemes();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ThemeChooserPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThemeChooserPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Something has gone wrong.  Return empty theme pack list.
        return new ArrayList<ThemeDescriptor>();
    }

    public JPanel getItemPanel() {
        return itemPanel;
    }

    private void clearItem(int index) {
        getItemPanel(index).clear();
    }

    private void selectItem(int index) {
        getItemPanel(index).set();
    }

    public ThemeDescriptor getSelectedThemeDescriptor() {
        return getSelectedItemPanel().getThemeDescriptor();
    }

    public ThemeDetailsPanel getItemPanel(int index) {
        return (ThemeDetailsPanel) itemPanel.getComponent(index);
    }

    public ThemeDetailsPanel getSelectedItemPanel() {
        return getItemPanel(selectedThemeIndex);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clearItem(selectedThemeIndex);
        this.selectedThemeIndex = ((ThemeDetailsPanel) e.getSource()).getIndex();
        selectItem(selectedThemeIndex);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
