/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NumberPicksPanel.java
 *
 * Created on Oct 3, 2009, 6:51:12 PM
 */

package keno.panels;

import java.awt.Dimension;
import keno.interfaces.DrawNumberListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.Timer;
import keno.widgets.PickNumber;

/**
 *
 * @author mark
 */
public class NumberPicksPanel extends javax.swing.JPanel implements ActionListener {
    private PickNumber lastPick = null;
    private ArrayList<Integer> numbers;
    private ArrayList<PickNumber> pickedNumbers = new ArrayList<PickNumber>();
    private ArrayList<DrawNumberListener> drawListeners = new ArrayList<DrawNumberListener>();
    
    public static final int DRAW_SPEED_SLOW = 500;
    public static final int DRAW_SPEED_MEDIUM = 300;
    public static final int DRAW_SPEED_FAST = 100;

    public static final int DRAW_MAX = 20;

    private int speed = DRAW_SPEED_MEDIUM;
    Timer timer = new Timer(getSpeed(),this);
    int drawCount = 0;

    /** Creates new form NumberPicksPanel */
    public NumberPicksPanel() {
        initComponents();
        initNumbers();
        timer.setInitialDelay(1000);
    }

    private int drawNumber() {
        int r = (int) (Math.random() * numbers.size());
        Integer picked = numbers.get(r);
        numbers.remove(r);
        lastPick = new PickNumber(picked.intValue()+1);
        getPickedNumbers().add(lastPick);
        add(lastPick);
        doLayout();
        validate();
        return picked.intValue();
    }

    public void addDrawListener( DrawNumberListener l ) {
        if ( !drawListeners.contains(l)) drawListeners.add(l);
    }

    public void removeDrawListener( DrawNumberListener l ) {
        if ( drawListeners.contains(l)) drawListeners.remove(l);
    }

    public boolean notifyDrawListenersDrawn( int num ) {
        boolean matched = false;
        for(DrawNumberListener l : drawListeners ) {
            if ( l.numberDrawn(num) ) {
                lastPick.setMatch();
            }
        }
        return matched;
    }

    public void notifyDrawListenersClear() {
        for(DrawNumberListener l : drawListeners )  l.clear();
    }

    public void notifyDrawListenersClearPicks() {
        for(DrawNumberListener l : drawListeners )  l.clearPicks();
    }

    public void clearPicks() {
        notifyDrawListenersClearPicks();
    }

    public void initNumbers() {
        numbers = new ArrayList<Integer>();
        getPickedNumbers().clear();
        this.removeAll();
        // Move the tiles down a little.
        add(Box.createRigidArea(new Dimension(64, 2)));
        notifyDrawListenersClear();
        repaint();
        for( int i=0; i< 80; i++ ) {
            numbers.add(i);
        }
    }

    public void doDraw() {
        initNumbers();
        drawCount = 0;
        notifyDrawListenersGameStarted();
        timer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            notifyDrawListenersDrawn(drawNumber());
            drawCount++;
            if (drawCount == DRAW_MAX ) {
                timer.stop();
                notifyDrawListenersGameOver();
                //System.out.println( "Game Over\n\n");
            }
    }

    private void initComponents() {
        setAlignmentY(0.0F);
        setName("picksPanel");
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(65, 420));
    }
    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private void notifyDrawListenersGameOver() {
        for(DrawNumberListener l : drawListeners )  l.gameOver();
    }

    private void notifyDrawListenersGameStarted() {
        for(DrawNumberListener l : drawListeners )  l.gameStarted();
    }

    /**
     * @return the pickedNumbers
     */
    public ArrayList<PickNumber> getPickedNumbers() {
        return pickedNumbers;
    }
}
