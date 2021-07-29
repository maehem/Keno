package keno.panels;

import com.maehem.adportal.AdRandomizer;
import com.maehem.adportal.BannerAdBundle;
import java.awt.Dimension;
import java.net.MalformedURLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class Marquee extends JPanel {

    private BannerAdBundle ads;
    private AdRandomizer adr;

    public Marquee() {
        setPreferredSize(new Dimension(800, 108));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        try {
            adr = new AdRandomizer(System.getProperty("keno.ad.bundle.url"));
            initBannerAds();
        } catch (MalformedURLException ex) {
            // No banner ads.
            System.out.println("Bad Ad Bundle URL." +
                    System.getProperty("keno.ad.bundle.url"));
        }
        
    }
    
    private void initBannerAds() throws MalformedURLException {
 
        add(Box.createHorizontalStrut(120));
        add(adr);
        add(Box.createHorizontalGlue());
    }
}
