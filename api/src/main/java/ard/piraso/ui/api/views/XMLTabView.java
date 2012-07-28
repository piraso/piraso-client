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

package ard.piraso.ui.api.views;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MessageAwareEntry;
import ard.piraso.api.entry.XMLAwareEntry;
import ard.piraso.ui.api.formatter.XMLFormatter;
import ard.piraso.ui.api.manager.FontProviderManager;
import org.openide.ErrorManager;

/**
 * For html view.
 */
public class XMLTabView extends FilteredSyntaxPaneTabView<Entry> {

    /**
     * Creates new form XMLTabView
     *
     * @param entry       the entry
     */
    public XMLTabView(Entry entry) {
        super("XML", entry, "XML is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        btnFilter.setVisible(false);
        btnCopy.setEnabled(true);

        try {
            txtEditor.setEditable(false);
            txtEditor.setContentType("text/xml");
            txtEditor.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());

            if(XMLAwareEntry.class.isInstance(entry)) {
                txtEditor.setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + ((XMLAwareEntry) entry).getXmlString());
            } else if(MessageAwareEntry.class.isInstance(entry)) {
                txtEditor.setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + XMLFormatter.prettyPrint(((MessageAwareEntry) entry).getMessage()));
            }
        } catch (Exception e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }
}
