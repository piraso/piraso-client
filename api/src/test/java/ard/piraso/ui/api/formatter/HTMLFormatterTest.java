package ard.piraso.ui.api.formatter;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * check for html
 */
public class HTMLFormatterTest {

    @Test
    public void testPrettyPrint() throws Exception {
        System.out.println(HTMLFormatter.prettyPrint("<div>alvin</div>"));
    }

    @Test
    public void testIsHTMLLike() throws Exception {
        assertTrue(HTMLFormatter.isHTMLLike("<div>alvin</div>"));
        assertFalse(HTMLFormatter.isHTMLLike("alvin"));
    }
}
