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

package ard.piraso.ui.api.views;

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.formatter.XMLFormatter;
import org.openide.ErrorManager;

import java.lang.reflect.Method;

/**
 * For json view.
 */
public class XMLTabView extends FilteredSyntaxPaneTabView<Entry> {

    /**
     * Creates new form StackTraceTabView
     *
     * @param entry       the entry
     */
    public XMLTabView(Entry entry) {
        super(entry, "XML is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        btnFilter.setVisible(false);
        btnCopy.setEnabled(true);

        try {
            Method method = entry.getClass().getMethod("getMessage");
            txtEditor.setEditable(false);
            txtEditor.setContentType("text/xml");
            txtEditor.setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + XMLFormatter.prettyPrint((String) method.invoke(entry)));
        } catch (Exception e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {
    }
}
