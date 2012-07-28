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

import ard.piraso.api.entry.StackTraceAwareEntry;
import ard.piraso.api.entry.StackTraceElementEntry;
import ard.piraso.ui.api.StackTraceFilterModel;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.apache.commons.lang.StringUtils;

import javax.swing.text.BadLocationException;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * @author adeleon
 */
public class StackTraceTabView extends FilteredJTextPaneTabView<StackTraceAwareEntry> {
    /**
     * Creates new form StackTraceTabView
     *
     * @param entry the entry
     */
    public StackTraceTabView(StackTraceAwareEntry entry) {
        super("Stacktrace", entry, "Stack trace is now copied to clipboard.");
    }

    public void insertNewLine() throws BadLocationException {
        if(StringUtils.isNotBlank(txtEditor.getText())) {
            insertBoldCode(txtEditor, "\n");
        }
    }
    
    @Override
    protected void populateMessage(StackTraceAwareEntry entry) throws Exception {
        StackTraceFilterModel model = SingleModelManagers.STACK_TRACE_FILTER.get();
        StackTraceAwareEntry stackTraceAwareEntry = (StackTraceAwareEntry) entry;

        boolean insertedEllipsis = false;
        for(StackTraceElementEntry st : stackTraceAwareEntry.getStackTrace()) {
            String value = st.toString();
            if(btnFilter.isSelected()) {
                if(model.isBold(value)) {
                    insertNewLine();
                    insertBoldCode(txtEditor, value);
                    insertedEllipsis = false;
                } else if(model.isMatch(value)) {
                    insertNewLine();
                    insertCode(txtEditor, value);
                    insertedEllipsis = false;
                } else {
                    if(!insertedEllipsis) {
                        insertNewLine();
                        insertGrayCode(txtEditor, "...");
                        insertedEllipsis = true;
                    }
                }
            } else {
                if(model.isBold(value)) {
                    insertNewLine();
                    insertBoldCode(txtEditor, value);
                } else if(model.isMatch(value)) {
                    insertNewLine();
                    insertCode(txtEditor, value);
                } else {
                    insertNewLine();
                    insertGrayCode(txtEditor, value);
                }

                insertedEllipsis = false;
            }
        }
    }
}
