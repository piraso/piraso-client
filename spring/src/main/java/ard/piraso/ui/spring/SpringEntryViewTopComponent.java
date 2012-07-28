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

package ard.piraso.ui.spring;

import ard.piraso.api.spring.SpringRemotingEntry;
import ard.piraso.api.spring.SpringRemotingStartEntry;
import ard.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;


@ActionID(category = "Window", id = "ard.piraso.ui.log4j.SpringEntryViewTopComponent")
@ActionReference(path = "Menu/Window", position = 337)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.log4j//SpringEntryView//EN", autostore = false)
@TopComponent.Description(preferredID = "SpringEntryViewTopComponent", iconBase="ard/piraso/ui/spring/icons/spring.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SpringViewAction", preferredID = "SpringEntryViewTopComponent")
public class SpringEntryViewTopComponent extends BaseEntryViewTopComponent<SpringRemotingEntry> {

    public SpringEntryViewTopComponent() {
        super(SpringRemotingEntry.class, "Remoting");

        setName(NbBundle.getMessage(SpringEntryViewTopComponent.class, "CTL_SpringViewTopComponent"));
        setToolTipText(NbBundle.getMessage(SpringEntryViewTopComponent.class, "HINT_SpringViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, SpringRemotingEntry entry) throws Exception {
        if(SpringRemotingStartEntry.class.isInstance(entry)) {
            insertHeaderCode(txtMessage, ((SpringRemotingStartEntry) entry).getMethodSignature());
            insertCode(txtMessage, "\n\n");
        }

        insertKeyword(txtMessage, "Method Name: ");
        insertBoldCode(txtMessage, entry.getMethodName());
        insertKeyword(txtMessage, "\nService Interface: ");
        insertCode(txtMessage, entry.getServiceInterface());
        insertKeyword(txtMessage, "\nURL: ");
        insertCode(txtMessage, entry.getUrl());
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
