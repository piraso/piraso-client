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

package org.piraso.ui.api.views;

import org.piraso.api.JacksonUtils;
import org.piraso.api.entry.Entry;
import org.piraso.api.entry.JSONAwareEntry;
import org.piraso.ui.api.formatter.JsonFormatter;
import org.piraso.ui.api.manager.FontProviderManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.ErrorManager;

/**
 * For json view.
 */
public class JsonTabView extends FilteredSyntaxPaneTabView<Entry> {
    
    private static final ObjectMapper MAPPER = JacksonUtils.MAPPER;
    
    /**
     * Creates new form StackTraceTabView
     *
     * @param entry       the entry
     */
    public JsonTabView(Entry entry) {
        super("JSON", entry, "JSON is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        btnFilter.setVisible(false);
        btnCopy.setEnabled(true);

        try {
            txtEditor.setEditable(false);
            txtEditor.setContentType("text/json");
            txtEditor.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());

            if(JSONAwareEntry.class.isInstance(entry)) {
                txtEditor.setText(JsonFormatter.prettyPrint(((JSONAwareEntry) entry).getJsonString()));
            } else {
                txtEditor.setText(JsonFormatter.prettyPrint(MAPPER.writeValueAsString(entry)));
            }
        } catch (Exception e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }
}
