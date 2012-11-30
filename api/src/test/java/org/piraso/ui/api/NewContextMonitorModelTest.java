/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.piraso.ui.api;

import org.piraso.api.AbstractJacksonTest;
import org.piraso.api.GeneralPreferenceEnum;
import org.piraso.api.Preferences;
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
