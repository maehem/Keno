/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keno;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import keno.panels.Marquee;
import thirdparty.BareBonesBrowserLaunch;

/**
 *
 * @author mark
 */
public class KenoUtils {

    public static Color parseHexColor(String text) {
        int startAt = 0;
        if (text.startsWith("0x")) {
            startAt = 2;
        }

        return new Color((int) Long.parseLong(text.substring(startAt), 16), true);
    }

    public static Color parseHexColorSystemProperty(String prop, String def) {
        //System.out.println(prop + " :: " + System.getProperty(prop, def));
        return (parseHexColor(System.getProperty(prop, def)));
    }

    public static boolean checkURL(URL url) {

        try {
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.connect();
                int response = httpConnection.getResponseCode();
                if (response != 200) {
                    System.out.println("Response: " + response);
                }
                if (response == 404) {
                    System.out.println("Resource URL not found: " + url.toExternalForm());
                    return false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(KenoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     *
     * @return array of version breakdown.  i.e 1.6.0_15 ==> [1,6,0,15]
     *
     */
    public static int[] getJavaVersion() {
        String sysVersion = System.getProperty("java.version");
        StringTokenizer t = new StringTokenizer(sysVersion, ".");
        ArrayList<String> tokens = new ArrayList<String>();
        while (t.hasMoreTokens()) {
            String nt = t.nextToken();
            if (nt.contains("_")) {
                tokens.add(nt.substring(0, nt.indexOf('_')));
                tokens.add(nt.substring(nt.indexOf('_') + 1));
            } else {
                tokens.add(nt);
            }
        }

        //System.out.println( "Tokens: " + tokens.toString());

        int[] tl = new int[tokens.size()];
        int i = 0;
        for (String nn : tokens) {
            tl[i] = Integer.parseInt(nn);
            i++;
        }
        return tl;
    }

    public static boolean browseURL(URL url) {
        try {
            int[] jv = KenoUtils.getJavaVersion();
            if (jv[0] >= 1 && jv[1] >= 6 && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(url.toURI());
            } else {
                BareBonesBrowserLaunch.openURL(url.toExternalForm());
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Marquee.class.getName()).log(Level.SEVERE, null, ex);
        }

        // If we got here, something when wrong.
        return false;
    }

    public static Icon getIconFromURL(String imageURL) {
        try {
            URL url = new URL(imageURL);
            return new ImageIcon(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(KenoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ImageIcon getIconFromClassPath(Object o, String path) {
        return new ImageIcon(o.getClass().getResource(path));
    }

    public static ImageIcon getIconFromClassPath(Object o, String path, int w, int h) {
        return  getScaledIcon(getIconFromClassPath(o, path), w, h);
    }

    public static JLabel getWebLabelImage(String imageURL, final String pageURL) {
        return getWebLabelImage(getIconFromURL(imageURL), pageURL);
    }

    public static JLabel getWebLabelImage(Icon icon, final String pageURL) {
        JLabel l = new JLabel();
        l.setIcon(icon);
        l.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    KenoUtils.browseURL(new URL(pageURL));
                } catch (MalformedURLException ex) {
                    // Don't go there.
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
        });

        return l;

    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    public static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public static ImageIcon getScaledIcon( ImageIcon icon, int w, int h) {
        return new ImageIcon(getScaledImage(icon.getImage(), w, h));
    }
}
