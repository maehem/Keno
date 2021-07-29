/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NumberBoardPanel.java
 *
 * Created on Oct 4, 2009, 11:32:17 AM
 */
package keno.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import keno.widgets.KenoGrid;
import keno.widgets.KenoGridTile;
import keno.widgets.NumberBoardStatusPanel;

/**
 *
 * @author mark
 */
public class NumberBoardPanel extends javax.swing.JPanel {

    private KenoGrid topGrid, bottomGrid;
    private NumberBoardStatusPanel statusPanel;
    private JPanel contentPanel = new JPanel();

    public NumberBoardPanel() {
        initComponents();
    }

    /** Creates new form NumberBoardPanel */
    public NumberBoardPanel(NumberPicksPanel picksPanel, PayoutTablePanel payoutPanel) {

        topGrid =       new KenoGrid(KenoGrid.TOP, picksPanel, payoutPanel);
        bottomGrid =    new KenoGrid(KenoGrid.BOTTOM, picksPanel, payoutPanel);
        statusPanel =   new NumberBoardStatusPanel();
        initComponents();
        doLayout();
        validate();
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(560, 420));
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(topGrid);
        contentPanel.add(statusPanel);
        contentPanel.add(bottomGrid);
        add(contentPanel, BorderLayout.CENTER);
    }

    public NumberBoardStatusPanel getStatusPanel() {
        return statusPanel;
    }

    public void pickRandom() {
        if (Math.random() > 0.5) {
            topGrid.pickRandom();
        } else {
            bottomGrid.pickRandom();
        }
    }

    public void themeChanged() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<KenoGridTile> getTiles() {
        ArrayList top = topGrid.getTiles();
        ArrayList bottom = bottomGrid.getTiles();

        for (int i = 0; i < bottom.size(); i++)  top.add(bottom.get(i));

        return top;
    }
}
