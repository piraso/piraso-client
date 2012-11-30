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

package org.piraso.ui.base.formatter;

import org.piraso.ui.api.NewContextMonitorModel;
import org.piraso.ui.api.formatter.JsonFormatter;
import org.piraso.ui.base.manager.PreferenceProviderManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 * Test for {@link org.piraso.ui.api.formatter.JsonFormatter} class.
 */
public class JsonFormatterTest {

    @Test
    public void testPrettyPrint() throws Exception {
        String str = JsonFormatter.prettyPrint("{\"preferences\":{\"booleanProperties\":{\"sql.view.enabled\":true,\"sql.prepared.statement.enabled\":true,\"sql.connection.enabled\":true,\"general.scoped.enabled\":true,\"sql.resultset.enabled\":true},\"integerProperties\":null,\"urlPatterns\":null},\"watchedAddr\":null,\"loggingUrl\":\"http://127.0.0.1/piraso/logging\",\"desc\":\"Profile for checking SQL Queries and retrieved Data.\"}");
        System.out.println(str);
    }
    
    @Test
    public void testX() throws Exception {
        NewContextMonitorModel model = new NewContextMonitorModel();
        model.setName("local-8080-sqls");
        model.setPreferences(PreferenceProviderManager.INSTANCE.createPreferences());
        model.setLoggingUrl("http://127.0.0.1:8080/piraso/logging");
        
        model.getPreferences().addProperty("sql.connection.enabled", true);
        model.getPreferences().addProperty("sql.prepared.statement.enabled", true);
        model.getPreferences().addProperty("sql.view.enabled", true);
        model.getPreferences().addProperty("sql.resultset.enabled", true);
        
        System.out.println(new ObjectMapper().writeValueAsString(model));
    }
}
