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
