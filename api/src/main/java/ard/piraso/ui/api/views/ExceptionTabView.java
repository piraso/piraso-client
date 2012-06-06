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
import ard.piraso.ui.api.StackTraceFilterModel;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.apache.commons.lang.StringUtils;
import org.openide.ErrorManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 *
 * @author adeleon
 */
public class ExceptionTabView extends FilteredJTextPaneTabView<ThrowableAwareEntry> {
    
    public ExceptionTabView(ThrowableAwareEntry entry) {
        super(entry, "Exception information is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        StackTraceFilterModel model = SingleModelManagers.STACK_TRACE_FILTER.get();
        ThrowableAwareEntry throwableAwareEntry = (ThrowableAwareEntry) entry;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        throwableAwareEntry.getThrown().printStackTrace(new PrintStream(out, true));

        try {
            txtEditor.setText("");

            BufferedReader reader = new BufferedReader(new StringReader(out.toString()));

            insertBoldBlueCode(txtEditor, "EXCEPTION:\n");
            String value;
            boolean insertedEllipsis = false;
            boolean firstLine = true;
            while((value = reader.readLine()) != null) {
                if(firstLine) {
                    insertCode(txtEditor, "\n    ");
                    insertUnderline(txtEditor, value);

                    firstLine = false;
                    continue;
                } else if(StringUtils.startsWith(value.trim(), "Caused by: ")) {
                    insertCode(txtEditor, "\n    ");
                    insertUnderline(txtEditor, value);
                    insertedEllipsis = false;

                    continue;
                }

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
        } catch (Exception e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {
        refreshView((Entry) entry);
    }
}
