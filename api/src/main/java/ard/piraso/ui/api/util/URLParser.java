package ard.piraso.ui.api.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a url from a given text
 */
public class URLParser {

    //Pull all links from the body for easy retrieval
    public static List<URI> parseUrls(String text) {
        List<URI> links = new ArrayList<URI>();

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

            try {
                URI url = new URI(urlStr);
                links.add(url);
            } catch (URISyntaxException e) {
            }
        }

        return links;
    }
}
