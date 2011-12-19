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

import ard.piraso.api.entry.StackTraceAwareEntry;
import ard.piraso.api.entry.StackTraceElementEntry;
import org.openide.ErrorManager;

import javax.swing.text.BadLocationException;

import static ard.piraso.ui.api.util.JTextPaneUtils.insertCode;
import static ard.piraso.ui.api.util.JTextPaneUtils.start;

/**
 * @author adeleon
 */
public class StackTraceTabView extends FilteredTextTabView<StackTraceAwareEntry> {
    /**
     * Creates new form StackTraceTabView
     *
     * @param entry the entry
     */
    public StackTraceTabView(StackTraceAwareEntry entry) {
        super(entry, "Stack trace is now copied to clipboard.");

        try {
            for(StackTraceElementEntry st : entry.getStackTrace()) {
                insertCode(txtEditor, "\n    " + st.toString());
            }

            start(txtEditor);
        } catch (BadLocationException e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {

    }
}
