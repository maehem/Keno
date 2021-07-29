/*
 * Banner Ad desriptor.
 *
 * Data object for a banner ad.
 * 
 */

package com.maehem.adportal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
public class BannerDescriptor {
    private String name = "";
    private String id = "";
    private URL linkURL = null;
    private URL imageURL = null;
    private String altText = "";

    public BannerDescriptor() {
        try {
            this.linkURL = new URL("http://maehem.com");
        } catch (MalformedURLException ex) {
            Logger.getLogger(BannerDescriptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the linkURL
     */
    public URL getLinkURL() {
        return linkURL;
    }

    /**
     * @param linkURL the linkURL to set
     */
    public void setLinkURL(URL linkURL) {
        this.linkURL = linkURL;
    }

    /**
     * @return the imageURL
     */
    public URL getImageURL() {
        return imageURL;
    }

    /**
     * @param imageURL the imageURL to set
     */
    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * @return the altText
     */
    public String getAltText() {
        return altText;
    }

    /**
     * @param altText the altText to set
     */
    public void setAltText(String altText) {
        this.altText = altText;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param characters the characters to append to 'id'
     */
     void appendId(String characters) {
        this.id += characters;
        this.id = id.trim();
    }

    /**
     * @param characters the characters to append to 'name'
     */
    void appendName(String characters) {
        this.name += characters;
        this.name = name.trim();
    }

    /**
     * @param characters the characters to append to 'altText'
     */
    void appendAltText(String characters) {
        this.altText += characters;
        this.altText = altText.trim();
    }


}
