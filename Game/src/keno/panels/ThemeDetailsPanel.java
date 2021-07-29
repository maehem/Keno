/*
 * Theme Details Panel
 */
package keno.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import keno.ThemeDescriptor;

/**
 *
 * @author mark
 */
public class ThemeDetailsPanel extends JPanel implements MouseListener {

    private ThemeDescriptor td;
    private static int indexCount = -1;
    private int index = 0;
    private int picHeight = 128;
    private int gapV = 5;
    private int gapH = 5;

    private static final Color COLOR_CLEAR = new Color(0xffffff, false);
    private static final Color COLOR_SET   = new Color(0x6666ff, false);

    @SuppressWarnings("static-access")
    public ThemeDetailsPanel(ThemeDescriptor td) {
        this.td = td;
        this.indexCount++;
        this.index = indexCount;

        setLayout(new BorderLayout(gapH, gapV));
        setName(td.getName());
        
        ImageIcon icon = new ImageIcon(td.getImageUrl());
        icon.setImage(getScaledImage(icon.getImage(), 5*picHeight/4, picHeight));

        JLabel imageLabel = new JLabel(icon);

        JLabel nameLabel = new JLabel(td.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 24.0f));
        nameLabel.setOpaque(false);

        URL authorURL = td.getAuthorUrl();
        String website = "";
        if ( authorURL != null ) website = "Website: " + authorURL.toExternalForm();
        String textAreaContent =
                td.getDescription() + "\n\n" +
                "Created By: " + td.getAuthor() + "     " + website + "\n" +
                "Date: " + td.getDateChanged();
        // Create the text area for misc item details.
        JTextArea ta = new JTextArea(textAreaContent, 5, 50);
        // We listen to the text field and pass mouse clicks up to parent.
        // Otherwise, the textField gobbles them up.
        ta.addMouseListener(this);
        ta.setName(td.getName());
        ta.setEditable(false);
        ta.setOpaque(false);


        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);        
        p.add(nameLabel, BorderLayout.NORTH);
        p.add(ta, BorderLayout.CENTER);


        add(p, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.WEST);
        validate();
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public void clear() {
        setBackground(COLOR_CLEAR);
    }

    public void set() {
        setBackground(COLOR_SET);
    }

    // We listen to the text field and pass mouse clicks up to parent.
    // Otherwise, the textField gobbles them up.
    @Override
    public void mouseClicked(MouseEvent e) {
        e.setSource(this);
        this.dispatchEvent(e);
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

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    public ThemeDescriptor getThemeDescriptor() {
        return td;
    }

    int getCreatedHeight() {
        return picHeight + gapV;
    }
}
