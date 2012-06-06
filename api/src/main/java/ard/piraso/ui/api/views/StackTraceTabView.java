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
import ard.piraso.api.entry.StackTraceAwareEntry;
import ard.piraso.api.entry.StackTraceElementEntry;
import ard.piraso.ui.api.StackTraceFilterModel;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.openide.ErrorManager;

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
        super(entry, "Stack trace is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        StackTraceFilterModel model = SingleModelManagers.STACK_TRACE_FILTER.get();
        StackTraceAwareEntry stackTraceAwareEntry = (StackTraceAwareEntry) entry;
        try {
            btnCopy.setEnabled(true);
            txtEditor.setText("");
            insertBoldBlueCode(txtEditor, "STACK TRACE:\n");
            boolean insertedEllipsis = false;
            for(StackTraceElementEntry st : stackTraceAwareEntry.getStackTrace()) {
                String value = st.toString();
                if(btnFilter.isSelected()) {
                    if(model.isBold(value)) {
                        insertBoldCode(txtEditor, "\n    " + value);
                        insertedEllipsis = false;
                    } else if(model.isMatch(value)) {
                        insertCode(txtEditor, "\n    " + value);
                        insertedEllipsis = false;
                    } else {
                        if(!insertedEllipsis) {
                            insertGrayCode(txtEditor, "\n    ...");
                            insertedEllipsis = true;
                        }
                    }
                } else {
                    if(model.isBold(value)) {
                        insertBoldCode(txtEditor, "\n    " + value);
                    } else if(model.isMatch(value)) {
                        insertCode(txtEditor, "\n    " + value);
                    } else {
                        insertGrayCode(txtEditor, "\n    " + value);
                    }

                    insertedEllipsis = false;
                }
            }

            start(txtEditor);
        } catch (BadLocationException e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {
        refreshView((Entry) entry);
    }
}
