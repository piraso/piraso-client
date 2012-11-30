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

import org.piraso.api.entry.MessageEntry;
import org.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.apache.commons.collections.CollectionUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.piraso.ui.api.util.JTextPaneUtils;

import javax.swing.*;

@ActionID(category = "Window", id = "org.piraso.ui.base.MessageEntryViewTopComponent")
@ActionReference(path = "Menu/Window", position = 334)
@ConvertAsProperties(dtd = "-//org.piraso.ui.base//MessageEntryView//EN", autostore = false)
@TopComponent.Description(preferredID = "MessageEntryViewTopComponent", iconBase= "org/piraso/ui/base/icons/message.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_MessageViewAction", preferredID = "MessageEntryViewTopComponent")
public class MessageEntryViewTopComponent extends BaseEntryViewTopComponent<MessageEntry> {
    public MessageEntryViewTopComponent() {
        super(MessageEntry.class, "Message");

        setName(NbBundle.getMessage(MethodCallViewTopComponent.class, "CTL_MessageViewTopComponent"));
        setToolTipText(NbBundle.getMessage(MethodCallViewTopComponent.class, "HINT_MessageViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, MessageEntry entry) throws Exception {
        if(currentEntry.getGroup() != null && CollectionUtils.isNotEmpty(currentEntry.getGroup().getGroups())) {
            JTextPaneUtils.insertKeyword(txtMessage, "Group: ");
            JTextPaneUtils.insertCode(txtMessage, String.valueOf(currentEntry.getGroup().getGroups()));
        }

        JTextPaneUtils.insertKeyword(txtMessage, "\nMessage: ");
        JTextPaneUtils.insertCode(txtMessage, currentEntry.getMessage());

        if(entry.getElapseTime() != null) {
            JTextPaneUtils.insertKeyword(txtMessage, "\nElapse Time: ");
            JTextPaneUtils.insertCode(txtMessage, currentEntry.getElapseTime().prettyPrint());
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
