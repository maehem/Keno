/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maehem.adportal;

import keno.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class BannerBundleXMLHandler extends DefaultHandler {

    //File file;
    InputStream is;
    private BannerAdBundle bundle;
    private Stack stack = new Stack();
    private BannerDescriptor currentBanner = null;

    public BannerBundleXMLHandler(URL  url) throws IOException {
        this( url, new BannerAdBundle());
    }
    
    public BannerBundleXMLHandler(URL url, BannerAdBundle target) {
        this.bundle = target;

        try {
            if (!KenoUtils.checkURL(url)) {
                throw new IOException("Invalid URL: " + url.toExternalForm());
            }
            is = url.openStream();
            parseXmlFile(false);
        } catch (IOException ex) {
            Logger.getLogger(BannerBundleXMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //System.out.println("     qName: " + qName);
        if (qName.equals("banner")) {
            currentBanner = new BannerDescriptor();
            getBanners().add(currentBanner);

        }

        // Push element name into stack.
        stack.push(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String characters = String.valueOf(ch).substring(start, start + length);

        // TODO:
        //  Should always append each call chars because this gets called
        // for each line in the tag's data. User may add linefeeds when formating
        // the XMLfile.


        if (currentBanner != null) {
            String tag = (String) stack.peek();
            if (tag.equals("name")) {
                currentBanner.appendName(characters);
            } else if (tag.equals("id")) {
                currentBanner.appendId(characters);
            } else if (tag.equals("altText")) {
                currentBanner.appendAltText(characters);
            } else if (tag.equals("linkURL")) {
                try {
                    currentBanner.setLinkURL(new URL(characters));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(BannerBundleXMLHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (tag.equals("imageURL")) {
                try {
                    currentBanner.setImageURL(new URL(characters));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(BannerBundleXMLHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (tag.equals("banner")) {
                // Do nothing
            } else {
               // System.out.println( "*** unknown tag: " + tag );
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        //System.out.println("End <" + qName + ">");

        // Pop element name off stack.
        if ( qName.equals("banner"))  currentBanner = null;
        stack.pop();
    }

    // Parses an XML file using a SAX parser.
    // If validating is true, the contents is validated against the DTD
    // specified in the file.
    private void parseXmlFile(boolean validating) {
        try {
            // Create a builder factory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(validating);

            // Create the builder and parse the file
            factory.newSAXParser().parse(is, this);
        } catch (SAXException e) {
            // A parsing error occurred; the xml input is not valid
            } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        }
    }

    /**
     * @return the themes
     */
    public ArrayList<BannerDescriptor> getBanners() {
        return bundle;
    }
}
