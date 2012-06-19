package ard.piraso.ui.api.formatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

/**
 * HTML Formatter
 */
public class HTMLFormatter {
    public static final List<String> IGNORE_BASE_TAGS = Arrays.asList(
            "#root",
            "html",
            "head",
            "body"
    );

    public static String prettyPrint(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        doc.outputSettings().prettyPrint(true).indentAmount(2);

        return doc.body().html();
    }

    /**
     * return true if the String passed in is something like HTML
     *
     * @param inHTMLStr a string that might be HTML
     * @return true of the string is HTML, false otherwise
     */
    public static boolean isHTMLLike(String inHTMLStr) {
        Document doc = Jsoup.parseBodyFragment(inHTMLStr);
        Elements els = doc.getAllElements();

        for(int i = 0; i < els.size(); i++) {
            if(!IGNORE_BASE_TAGS.contains(els.get(i).tagName())) {
                return true;
            }
        }

        return false;
    }
}
