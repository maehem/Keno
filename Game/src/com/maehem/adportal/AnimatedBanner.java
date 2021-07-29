/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maehem.adportal;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 *
 * @author mark
 */
public class AnimatedBanner extends JScrollPane implements ActionListener {

    public static final int POP_INCREMENT = 20;
    public static final int PUSH_INCREMENT = 2;
    public static final int DWELL_INCREMENT = 0;
    public static final int DWELL_COUNT = 30;
    public static final int DWELL_TIME_INITIAL = 1000;
    public static final int PUSH=0, DWELL_POP=1, POP=2, DWELL_PUSH=3, DWELL_INITIAL=4;
    
    private int mode = DWELL_INITIAL;
    private int dwellCounter = 0;
    private int dwellTime = 1000;
    
    public static final int DRAW_SPEED_SLOW = 100;
    public static final int DRAW_SPEED_MEDIUM = 70;
    public static final int DRAW_SPEED_FAST = 50;
    private int speed = DRAW_SPEED_MEDIUM;
    Timer timer = new Timer(speed, this);
    
    JPanel adPanel = new JPanel();

    protected AnimatedBanner() {
        super(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);
        
        adPanel.setOpaque(false);
        adPanel.revalidate();

        setViewportView(adPanel);
        setOpaque(false);
        setBorder(null);
        getViewport().setOpaque(false);
    }
    
    public AnimatedBanner(BannerAd ad) {
        this();
        setAd( ad );
        dwellInitial();
    }

    protected void setAd( BannerAd ad ) {
        Dimension d = new Dimension(600,100);

        //setPreferredSize(d);
        //getViewport().setPreferredSize(d);
        adPanel.removeAll();
        adPanel.setPreferredSize(new Dimension(d.width, d.height*2));
        adPanel.add(Box.createRigidArea(d));
        adPanel.add(ad);
        revalidate();        
    }
    
    public void pop() {
        //System.out.println( "Pop");
        mode = POP;        
        timer.restart();
    }
    
    public void dwellPop() {
        //System.out.println( "Dwell Pop");
        mode = DWELL_POP;
        dwellCounter = 0;
        dwellTime = (int) (Math.random() * 1500 + 3500);
        timer.restart();       
    }

    public void dwellPush() {
        //System.out.println( "Dwell Push");
        mode = DWELL_PUSH;
        dwellCounter = 0;
        dwellTime = (int) (Math.random() * 24000 + 44500);
        timer.restart();
    }
    
    public void dwellInitial() {
        //System.out.println( "Dwell Initial");
        mode = DWELL_INITIAL;
        dwellCounter = 0;
        dwellTime = DWELL_TIME_INITIAL;
        timer.restart();
    }

    public void push() {
        //System.out.println( "Push");
        mode = PUSH;
        timer.restart();       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ignore events from the sub-class timer.
        if ( e.getSource() != timer) return;

        // Move scrollbar by increment
        JScrollBar sb = getVerticalScrollBar();
        int val = 0;
        switch( mode ) {
            case POP:
                val = sb.getValue()+POP_INCREMENT;
                if ( val < 100 ) {
                    //System.out.println("    val: " + val +  "   max: " + sb.getVisibleAmount() );
                    sb.setValue(val);
                } else {
                    sb.setValue(sb.getMaximum());
                    dwellPop();
               }
                break;
            case DWELL_POP:
                dwellCounter += DWELL_COUNT;
                if ( dwellCounter >= dwellTime ) {
                    push();
                }
                break;
            case PUSH:
                val = sb.getValue()-PUSH_INCREMENT;
                if ( val > 0 ) {
                    sb.setValue(val);
                }
                else {
                    sb.setValue(sb.getMinimum());
                    dwellPush();
                }
                break;
            case DWELL_PUSH:
            case DWELL_INITIAL:
                //System.out.println("dwellCounter1: " + dwellCounter +  "   dwellTime: " + dwellTime );
                dwellCounter += DWELL_COUNT;
                //System.out.println("dwellCounter2: " + dwellCounter +  "   dwellTime: " + dwellTime  );
                if ( dwellCounter >= dwellTime ) {
                    pop();
                }
                break;
        }
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
}
