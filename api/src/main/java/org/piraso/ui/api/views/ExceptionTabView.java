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

import org.piraso.api.entry.ThrowableAwareEntry;
import org.piraso.ui.api.StackTraceFilterModel;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.piraso.ui.api.util.JTextPaneUtils.*;

/**
 *
 * @author adeleon
 */
public class ExceptionTabView extends FilteredJTextPaneTabView<ThrowableAwareEntry> {
    
    public ExceptionTabView(ThrowableAwareEntry entry) {
        super("Exception", entry, "Exception information is now copied to clipboard.");
    }
    
    @Override
    protected void populateMessage(ThrowableAwareEntry entry) throws Exception {
        StackTraceFilterModel model = SingleModelManagers.STACK_TRACE_FILTER.get();
        ThrowableAwareEntry throwableAwareEntry = (ThrowableAwareEntry) entry;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        throwableAwareEntry.getThrown().printStackTrace(new PrintStream(out, true));

        BufferedReader reader = new BufferedReader(new StringReader(out.toString()));

        String value;
        boolean insertedEllipsis = false;
        boolean firstLine = true;
        while((value = reader.readLine()) != null) {
            value = StringUtils.replace(value, "\t", "    ");

            if(firstLine) {
                insertBoldCode(txtEditor, value);

                firstLine = false;
                continue;
            } else if(StringUtils.startsWith(value.trim(), "Caused by: ")) {
                insertCode(txtEditor, "\n");
                insertBoldCode(txtEditor, value);
                insertedEllipsis = false;

                continue;
            }

            if(btnFilter.isSelected()) {
                if(model.isBold(value)) {
                    insertBoldCode(txtEditor, "\n" + value);
                    insertedEllipsis = false;
                } else if(model.isMatch(value)) {
                    insertCode(txtEditor, "\n" + value);
                    insertedEllipsis = false;
                } else {
                    if(!insertedEllipsis) {
                        insertGrayCode(txtEditor, "\n    ...");
                        insertedEllipsis = true;
                    }
                }
            } else {
                if(model.isBold(value)) {
                    insertBoldCode(txtEditor, "\n" + value);
                } else if(model.isMatch(value)) {
                    insertCode(txtEditor, "\n" + value);
                } else {
                    insertGrayCode(txtEditor, "\n" + value);
                }

                insertedEllipsis = false;
            }
        }
        
    }
}
