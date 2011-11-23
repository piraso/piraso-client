package ard.piraso.ui.base.formatter;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

import java.io.StringReader;

/**
 * Json string formatter
 */
public class JsonFormatter {
    public static String prettyPrint(String json) throws TokenStreamException, RecognitionException {
        final JSONParser lParser = new JSONParser(new StringReader(json));
        final JSONValue lMyObject = lParser.nextValue();

        return lMyObject.render(true);
    }
}
