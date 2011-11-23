package ard.piraso.ui.base.formatter;

import org.junit.Test;

/**
 * Test for {@link JsonFormatter} class.
 */
public class JsonFormatterTest {

    @Test
    public void testPrettyPrint() throws Exception {
        String str = JsonFormatter.prettyPrint("{\"preferences\":{\"booleanProperties\":{\"sql.view.enabled\":true,\"sql.prepared.statement.enabled\":true,\"sql.connection.enabled\":true,\"general.scoped.enabled\":true,\"sql.resultset.enabled\":true},\"integerProperties\":null,\"urlPatterns\":null},\"watchedAddr\":null,\"loggingUrl\":\"http://127.0.0.1/piraso/logging\",\"desc\":\"Profile for checking SQL Queries and retrieved Data.\"}");
        System.out.println(str);
    }
}
