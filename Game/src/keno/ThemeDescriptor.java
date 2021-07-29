/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keno;

import java.net.URL;

/**
 *
 * @author mark
 */
public class ThemeDescriptor {
    private String name = "";
    // Todo: Make ID and attribute of Name???
    private String id = "";
    private URL jarUrl = null;
    private URL imageUrl = null;
    private String description = "";
    private String dateChanged = "";
    private String author = "Unknown";
    private URL authorUrl = null;

    public ThemeDescriptor() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param name the name to set
     */
    public void appendName(String name) {
        this.name += name;
        this.name = this.name.trim();
    }

    /**
     * @return the url
     */
    public URL getJarUrl() {
        return jarUrl;
    }

    /**
     * @param url the url to set
     */
    public void setJarUrl(URL url) {
        this.jarUrl = url;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        //System.out.println( "Set Description: " + description);
        this.description = description;
    }

    /**
     * @param description the description to set
     */
    public void appendDescription(String description) {
        //System.out.println( "Append Description: " + description);
        this.description += description;
        this.description = this.description.trim();
    }

    /**
     * @return the dateChanged
     */
    public String getDateChanged() {
        return dateChanged;
    }

    /**
     * @param dateChanged the dateChanged to set
     */
    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    /**
     * @param dateChanged the dateChanged to set
     */
    public void appendDateChanged(String dateChanged) {
        this.dateChanged += dateChanged;
        this.dateChanged = this.dateChanged.trim();
    }

    /**
     * @return the imageUrl
     */
    public URL getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param text the text to append
     */
    public void appendAuthor(String text) {
        this.author += text;
        this.author = this.author.trim();
    }

    /**
     * @return the authorURL
     */
    public URL getAuthorUrl() {
        return authorUrl;
    }

    /**
     * @param authorURL the authorURL to set
     */
    public void setAuthorUrl(URL authorUrl) {
        this.authorUrl = authorUrl;
    }

    @Override
    public String toString() {
        return
                "        name: " + getName() + "\n" +
                "          id: " + getId() + "\n" +
                " description: " + getDescription() + "\n" +
                "     jar URL: " + ((getJarUrl() != null)?getJarUrl().toExternalForm():"null") + "\n" +
                "   image URL: " + ((getImageUrl() != null)?getImageUrl().toExternalForm():"null") + "\n" +
                "      author: " + getAuthor() + "\n" +
                "  Author URL: " + ((getAuthorUrl() != null)?getAuthorUrl().toExternalForm():"null") + "\n" +
                "date changed: " + getDateChanged() + "\n\n";
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
     * @param text the text to append
     */
    public void appendId(String text) {
        this.id += text;
        this.id = this.id.trim();
    }


}
