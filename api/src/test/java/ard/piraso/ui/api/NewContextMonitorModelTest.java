package ard.piraso.ui.api;

import ard.piraso.api.AbstractJacksonTest;
import ard.piraso.api.GeneralPreferenceEnum;
import ard.piraso.api.Preferences;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test for {@link NewContextMonitorModel} class.
 */
public class NewContextMonitorModelTest extends AbstractJacksonTest {
    @Test
    public void testJackson() throws Exception {
        NewContextMonitorModel expected = new NewContextMonitorModel();

        expected.setLoggingUrl("http://127.0.0.1/piraso/logging");
        expected.setWatchedAddr("127.0.0.1");

        Preferences preferences = new Preferences();
        preferences.addProperty(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), true);
        preferences.addProperty(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName(), true);

        expected.setPreferences(preferences);

        String json = mapper.writeValueAsString(expected);
        NewContextMonitorModel actual = mapper.readValue(json, NewContextMonitorModel.class);

        assertEquals(expected, actual);
    }
}
