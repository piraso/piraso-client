/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.base.formatter;

import ard.piraso.ui.api.formatter.JsonFormatter;
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
