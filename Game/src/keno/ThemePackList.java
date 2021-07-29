/*
 * Theme-Pack List
 */
package keno;

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

public class ThemePackList extends DefaultHandler {

    //File file;
    InputStream is;
    private ArrayList<ThemeDescriptor> themes = new ArrayList<ThemeDescriptor>();
    private Stack stack = new Stack();
    private ThemeDescriptor currentTheme = null;

    public ThemePackList(URL  url) throws IOException {
        themes.add(getDefaultTheme());

        if ( !KenoUtils.checkURL(url) )
            throw new IOException("Invalid URL: " + url.toExternalForm());


        is = url.openStream();

        parseXmlFile(false);
    }

    private ThemeDescriptor getDefaultTheme() {
        ThemeDescriptor td = new ThemeDescriptor();
        td.setName("Default Theme");
        td.setId("base");
        td.setDescription("Built-in theme for this game.");
        td.setImageUrl(getClass().getResource("/keno/theme/base-thumb.jpg"));
        td.setJarUrl(null);

        return td;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //System.out.println("     qName: " + qName);
        if (qName.equals("theme")) {
            currentTheme = new ThemeDescriptor();
            getThemes().add(currentTheme);

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


        if (currentTheme != null) {
            String tag = (String) stack.peek();
            if (tag.equals("name")) {
                currentTheme.setName(characters);
            } else if (tag.equals("id")) {
                currentTheme.appendId(characters);
            } else if (tag.equals("description")) {
                currentTheme.appendDescription(characters);
            } else if (tag.equals("dateChanged")) {
                currentTheme.appendDateChanged(characters);
            } else if (tag.equals("jarUrl")) {
                try {
                    currentTheme.setJarUrl(new URL(characters));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ThemePackList.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (tag.equals("imageUrl")) {
                try {
                    currentTheme.setImageUrl(new URL(characters));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ThemePackList.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (tag.equals("theme")) {
            } else if (tag.equals("author")) {
                if ( currentTheme.getAuthor().equals("Unknown")) currentTheme.setAuthor("");
                currentTheme.appendAuthor(characters);
            } else if (tag.equals("authorURL")) {
                try {
                    currentTheme.setAuthorUrl(new URL(characters));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ThemePackList.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        if ( qName.equals("theme"))  currentTheme = null;
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
    public ArrayList<ThemeDescriptor> getThemes() {
        return themes;
    }
}
