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
import ard.piraso.ui.api.formatter.HTMLFormatter;
import ard.piraso.ui.api.manager.FontProviderManager;
import org.openide.ErrorManager;

/**
 * For html view.
 */
public class HTMLTabView extends FilteredSyntaxPaneTabView<MessageAwareEntry> {

    /**
     * Creates new form HTMLTabView
     *
     * @param entry       the entry
     */
    public HTMLTabView(MessageAwareEntry entry) {
        super("HTML", entry, "HTML is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        MessageAwareEntry m = (MessageAwareEntry) entry;

        btnFilter.setVisible(false);
        btnCopy.setEnabled(true);

        try {
            txtEditor.setEditable(false);
            txtEditor.setContentType("text/xhtml");
            txtEditor.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
            txtEditor.setText(HTMLFormatter.prettyPrint(m.getMessage()));
        } catch (Exception e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }
}
