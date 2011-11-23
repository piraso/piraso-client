package ard.piraso.ui.sql;

import ard.piraso.api.AbstractJacksonTest;
import ard.piraso.api.GeneralPreferenceEnum;
import ard.piraso.api.Preferences;
import ard.piraso.api.sql.SQLPreferenceEnum;
import ard.piraso.ui.api.NewContextMonitorModel;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Used for profile creation
 */
public class ProfileTest extends AbstractJacksonTest {

    @Test
    public void testJackson() throws Exception {
        NewContextMonitorModel expected = new NewContextMonitorModel();

        expected.setLoggingUrl("http://127.0.0.1/piraso/logging");
        expected.setDesc("Profile for checking SQL Queries and retrieved Data.");

        Preferences preferences = new Preferences();

        preferences.addProperty(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), true);
        //preferences.addProperty(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName(), true);
        preferences.addProperty(SQLPreferenceEnum.CONNECTION_ENABLED.getPropertyName(), true);
        //preferences.addProperty(SQLPreferenceEnum.CONNECTION_METHOD_CALL_ENABLED.getPropertyName(), true);
        preferences.addProperty(SQLPreferenceEnum.PREPARED_STATEMENT_ENABLED.getPropertyName(), true);
        preferences.addProperty(SQLPreferenceEnum.VIEW_SQL_ENABLED.getPropertyName(), true);
        //preferences.addProperty(SQLPreferenceEnum.PREPARED_STATEMENT_METHOD_CALL_ENABLED.getPropertyName(), true);
        preferences.addProperty(SQLPreferenceEnum.RESULTSET_ENABLED.getPropertyName(), true);

        expected.setPreferences(preferences);

        String str = mapper.writeValueAsString(expected);
        NewContextMonitorModel actual = mapper.readValue(str, NewContextMonitorModel.class);

        assertEquals(expected, actual);

        System.out.println("JSON: " + str);
        System.out.println("REPLACE: " + str.replaceAll("\"", "&quot;"));
    }
}
