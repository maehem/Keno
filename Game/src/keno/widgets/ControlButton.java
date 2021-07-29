/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

/**
 *
 * @author mark
 */
public class ControlButton extends JButton{
    public ControlButton(String text) {
        //super(text);
        setName(text);
        setBackground(new Color(0x00ffffdd, false));
        setFont(getFont().deriveFont(Font.BOLD, 18.0f));
        setText(text);
        int h = 20;
        setMargin(new Insets(h, 4, h, 4));
        
        setAlignmentY(BOTTOM_ALIGNMENT);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
//        setPreferredSize(new Dimension(text.length()*18*4/5, 72));
    }

    
 }
