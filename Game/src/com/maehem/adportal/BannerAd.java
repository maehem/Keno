package com.maehem.adportal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import keno.KenoUtils;

/**
 *
 * @author mark
 */
public class BannerAd extends JPanel implements MouseListener {
    private BannerDescriptor descriptor = new BannerDescriptor();
    Image image;

    public BannerAd() {
        setPreferredSize(new Dimension(600, 108));
        //setBackground(Color.red);
        setOpaque(false);
        addMouseListener(this);
    }

    public BannerAd(BannerDescriptor d) {
        this();
        this.descriptor = d;
        try {
            //System.out.println("Image URL: " + descriptor.getImageURL().toExternalForm());
            // Get the image for d.imageURL and add it to the panel.
            image = ImageIO.read(descriptor.getImageURL());
            setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
        } catch (IOException ex) {
            Logger.getLogger(BannerAd.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        KenoUtils.browseURL(descriptor.getLinkURL());
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

}
