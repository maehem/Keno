/*
 * ArrayList to hold the BundleDescriptor objects.
 */

package com.maehem.adportal;

import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author mark
 */
public class BannerAdBundle extends ArrayList<BannerDescriptor> {
    public BannerAdBundle() {}

    public BannerAdBundle( URL url ) {
        new BannerBundleXMLHandler(url, this);
    }

    public BannerAd getBannerAd(int index) throws IndexOutOfBoundsException {
        return new BannerAd(get(index));
    }


}
