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
import ard.piraso.api.entry.ThrowableAwareEntry;
import org.openide.ErrorManager;

import javax.swing.text.BadLocationException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static ard.piraso.ui.api.util.JTextPaneUtils.insertCode;
import static ard.piraso.ui.api.util.JTextPaneUtils.start;

/**
 *
 * @author adeleon
 */
public class ExceptionTabView extends FilteredTextTabView<ThrowableAwareEntry> {
    
    public ExceptionTabView(ThrowableAwareEntry entry) {
        super(entry, "Exception information is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        ThrowableAwareEntry throwableAwareEntry = (ThrowableAwareEntry) entry;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        throwableAwareEntry.getThrown().printStackTrace(new PrintStream(out, true));

        try {
            txtEditor.setText("");
            insertCode(txtEditor, out.toString());

            start(txtEditor);
        } catch (BadLocationException e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {
    }
}
