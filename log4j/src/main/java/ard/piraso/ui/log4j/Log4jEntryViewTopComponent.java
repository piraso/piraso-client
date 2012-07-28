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

package ard.piraso.ui.log4j;

import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.util.JTextPaneUtils;
import ard.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.text.SimpleDateFormat;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;


@ActionID(category = "Window", id = "ard.piraso.ui.log4j.Log4jEntryViewTopComponent")
@ActionReference(path = "Menu/Window", position = 336)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.log4j//Log4jEntryView//EN", autostore = false)
@TopComponent.Description(preferredID = "Log4jEntryViewTopComponent", iconBase="ard/piraso/ui/log4j/icons/log4j.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_Log4jViewAction", preferredID = "Log4jEntryViewTopComponent")
public class Log4jEntryViewTopComponent extends BaseEntryViewTopComponent<Log4jEntry> {

    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    public static final SimpleAttributeSet ERROR = new SimpleAttributeSet();

    public static final SimpleAttributeSet WARNING = new SimpleAttributeSet();

    public static final SimpleAttributeSet INFO = new SimpleAttributeSet();

    public static final SimpleAttributeSet DEBUG = new SimpleAttributeSet();

    public static final SimpleAttributeSet TRACE = new SimpleAttributeSet();

    public static final SimpleAttributeSet ALL = new SimpleAttributeSet();

    static {
        Font baseFont = FontProviderManager.INSTANCE.getEditorDefaultFont();

        JTextPaneUtils.setupCode(ERROR, Color.RED, new Color(0xFFC8BD), true, false, baseFont.getSize());
        JTextPaneUtils.setupCode(WARNING, new Color(0xFF801D), new Color(0xF7D7C1), true, false, baseFont.getSize());
        JTextPaneUtils.setupCode(INFO, new Color(0x008000), new Color(0xBAEEBA), true, false, baseFont.getSize());
        JTextPaneUtils.setupCode(DEBUG, new Color(0x999999), new Color(0xEAF2F5), true, false, baseFont.getSize());
        JTextPaneUtils.setupCode(TRACE, new Color(0x5D5D5D), new Color(0xF3F3F3), true, false, baseFont.getSize());
        JTextPaneUtils.setupCode(ALL, new Color(0x999999), new Color(0xEAF2F5), true, false, baseFont.getSize());
    }


    public Log4jEntryViewTopComponent() {
        super(Log4jEntry.class, "Log4j");

        setName(NbBundle.getMessage(Log4jEntryViewTopComponent.class, "CTL_Log4jViewTopComponent"));
        setToolTipText(NbBundle.getMessage(Log4jEntryViewTopComponent.class, "HINT_Log4jViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, Log4jEntry entry) throws Exception {
        insertKeyword(txtMessage, "Level: ");

        if("ERROR".equals(currentEntry.getLogLevel()) || "FATAL".equals(currentEntry.getLogLevel())) {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), ERROR);
        } else if("WARN".equals(currentEntry.getLogLevel())) {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), WARNING);
        } else if("INFO".equals(currentEntry.getLogLevel())) {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), INFO);
        } else if("DEBUG".equals(currentEntry.getLogLevel())) {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), DEBUG);
        } else if("TRACE".equals(currentEntry.getLogLevel())) {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), TRACE);
        } else {
            insertText(txtMessage, String.format(" %s ", currentEntry.getLogLevel()), ALL);
        }

        if(CollectionUtils.isNotEmpty(currentEntry.getGroup().getGroups())) {
            insertKeyword(txtMessage, "\nLogger: ");
            insertBoldCode(txtMessage, currentEntry.getGroup().getGroups().iterator().next());
        }

        insertKeyword(txtMessage, "\nTime: ");
        insertCode(txtMessage, FORMATTER.format(currentEntry.getTime()));

        insertKeyword(txtMessage, "\nThread: ");
        insertIdentifier(txtMessage, "id: ");
        insertCode(txtMessage, String.valueOf(currentEntry.getThreadId()));
        insertCode(txtMessage, ", ");
        insertIdentifier(txtMessage, "name: ");
        insertCode(txtMessage, currentEntry.getThreadName());
        insertCode(txtMessage, ", ");
        insertIdentifier(txtMessage, "priority: ");
        insertCode(txtMessage, String.valueOf(currentEntry.getThreadPriority()));

        if(StringUtils.isNotBlank(currentEntry.getNdc())) {
            insertKeyword(txtMessage, "\nNDC: ");
            insertCode(txtMessage, currentEntry.getNdc());
        }

        if(currentEntry.getMdc() != null && !StringUtils.equalsIgnoreCase(currentEntry.getMdc(), "null")) {
            insertKeyword(txtMessage, "\nMDC: ");
            insertCode(txtMessage, currentEntry.getMdc());
        }

        insertKeyword(txtMessage, "\nMessage: ");
        insertCode(txtMessage, currentEntry.getMessage());
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
