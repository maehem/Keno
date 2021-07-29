/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno.panels;

import java.awt.BorderLayout;

/**
 *
 * @author mark
 */
public class MachinePanel extends SubPanel {

    private ThemeChooserPanel themeChooser = new ThemeChooserPanel();

    public MachinePanel() {
       super("Choose a Theme");
       getReturnButton().setText("Done");
       add(themeChooser, BorderLayout.CENTER);
    }


    public ThemeChooserPanel getChooserPanel() {
        return themeChooser;
    }
}
