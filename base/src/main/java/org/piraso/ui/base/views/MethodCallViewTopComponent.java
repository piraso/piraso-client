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

package org.piraso.ui.base.views;

import org.piraso.api.entry.MethodCallEntry;
import org.piraso.api.entry.ObjectEntry;
import org.piraso.api.entry.ObjectEntryUtils;
import org.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;

import static org.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * Method call view component
 */
@ActionID(category = "Window", id = "org.piraso.ui.base.MethodCallViewTopComponent")
@ActionReference(path = "Menu/Window", position = 333)
@ConvertAsProperties(dtd = "-//org.piraso.ui.base//MethodCallView//EN", autostore = false)
@TopComponent.Description(preferredID = "MethodCallViewTopComponent", iconBase= "org/piraso/ui/base/icons/method.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_MethodViewAction", preferredID = "MethodCallViewTopComponent")
public class MethodCallViewTopComponent extends BaseEntryViewTopComponent<MethodCallEntry> {

    public MethodCallViewTopComponent() {
        super(MethodCallEntry.class, "Method");

        setName(NbBundle.getMessage(MethodCallViewTopComponent.class, "CTL_MethodViewTopComponent"));
        setToolTipText(NbBundle.getMessage(MethodCallViewTopComponent.class, "HINT_MethodViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, MethodCallEntry entry) throws Exception {
        insertHeaderCode(txtMessage, currentEntry.getGenericString());
        insertCode(txtMessage, "\n");

        if(currentEntry.getArguments() != null && currentEntry.getArguments().length > 0) {
            for(int i = 0; i < currentEntry.getArguments().length; i++) {
                ObjectEntry argument = currentEntry.getArguments()[i];
                insertKeyword(txtMessage, String.format("\nArgument[%d]: ", i));
                insertCode(txtMessage, ObjectEntryUtils.toString(argument));
            }
        }

        if(!currentEntry.getReturnClassName().equals("void")) {
            insertKeyword(txtMessage, "\nReturn: ");
            insertCode(txtMessage, ObjectEntryUtils.toString(currentEntry.getReturnedValue()));
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
