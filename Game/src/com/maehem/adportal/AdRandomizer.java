/*
 * Ad Randomizer
 */
package com.maehem.adportal;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Timer;

/**
 *
 * @author mark
 */
public class AdRandomizer extends AnimatedBanner {
    //Component component;

    public static int RANDOM = -1;
    BannerAdBundle bundle;
    Timer changeTimer = new Timer(500000, this);

    public AdRandomizer(String urlString) throws MalformedURLException {
        super();

        URL url = new URL(urlString);
        this.bundle = new BannerAdBundle(url);

        if (bundle.size() > 0) {
            setAd(bundle.getBannerAd(0));
            setBanner(RANDOM);

            dwellInitial();

            changeTimer.start();
        }

    }

    public void setBanner(int index) {
        if (index >= bundle.size()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == RANDOM) {
            index = (int) (Math.random() * bundle.size());
        }
        setAd(bundle.getBannerAd(index));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == changeTimer) {
            System.out.println("Change Banner");
            setBanner(RANDOM);
        }
    }
}
