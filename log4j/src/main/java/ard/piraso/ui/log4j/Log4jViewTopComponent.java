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

package ard.piraso.ui.log4j;

import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.ui.api.EntryTabView;
import ard.piraso.ui.api.extension.AbstractEntryViewTopComponent;
import ard.piraso.ui.api.manager.EntryTabViewProviderManager;
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.util.ClipboardUtils;
import ard.piraso.ui.api.util.NotificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.ErrorManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.text.BadLocationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * Top component which displays something.
 */
@ActionID(category = "Window", id = "ard.piraso.ui.log4j.Log4jViewTopComponent")
@ActionReference(path = "Menu/Window", position = 336)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.log4j//Log4jView//EN", autostore = false)
@TopComponent.Description(preferredID = "Log4jViewTopComponent", iconBase="ard/piraso/ui/log4j/icons/log4j.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_Log4jViewAction", preferredID = "Log4jViewTopComponent")
public final class Log4jViewTopComponent extends AbstractEntryViewTopComponent<Log4jEntry> {
    
    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    public Log4jViewTopComponent() {
        super(Log4jEntry.class);
        initComponents();
        setName(NbBundle.getMessage(Log4jViewTopComponent.class, "CTL_Log4jViewTopComponent"));
        setToolTipText(NbBundle.getMessage(Log4jViewTopComponent.class, "HINT_Log4jViewTopComponent"));
    }

    @Override
    protected void refreshView() {
        List<EntryTabView> components = new ArrayList<EntryTabView>();

        int selectedIndex = jTabbedPane.getSelectedIndex();

        jTabbedPane.removeAll();

        if(currentEntry != null) {
            components.add(new EntryTabView(jPanel1, "Message"));
            components.addAll(EntryTabViewProviderManager.INSTANCE.getTabView(Log4jViewTopComponent.class, currentEntry));
        }

        refreshLog4jView();

        if(CollectionUtils.isNotEmpty(components)) {
            for(EntryTabView tabView : components) {
                jTabbedPane.addTab(tabView.getTitle(), tabView.getComponent());
            }
        }

        if(selectedIndex >= 0) {
            jTabbedPane.setSelectedIndex(selectedIndex);
        }

        repaint();
        revalidate();
    }

    private void refreshLog4jView() {
        try {
            txtMessage.setText("");
            btnCopy.setEnabled(currentEntry != null);

            if(currentEntry != null) {
                insertBoldCode(txtMessage, "Level: ");
                insertCode(txtMessage, currentEntry.getLogLevel());

                if(CollectionUtils.isNotEmpty(currentEntry.getGroup().getGroups())) {
                    insertBoldCode(txtMessage, "\nLogger: ");
                    insertCode(txtMessage, currentEntry.getGroup().getGroups().iterator().next());
                }
                
                insertBoldCode(txtMessage, "\nTime: ");
                insertCode(txtMessage, FORMATTER.format(currentEntry.getTime()));

                insertBoldCode(txtMessage, "\nThread: ");
                insertUnderline(txtMessage, "id");
                insertCode(txtMessage, String.format(": %d, ", currentEntry.getThreadId()));
                insertUnderline(txtMessage, "name");
                insertCode(txtMessage, String.format(": %s, ", currentEntry.getThreadName()));
                insertUnderline(txtMessage, "priority");
                insertCode(txtMessage, String.format(": %d", currentEntry.getThreadPriority()));
                
                if(StringUtils.isNotBlank(currentEntry.getNdc())) {
                    insertBoldCode(txtMessage, "\nNDC: ");
                    insertCode(txtMessage, currentEntry.getNdc());
                }
                
                if(currentEntry.getMdc() != null && !StringUtils.equalsIgnoreCase(currentEntry.getMdc(), "null")) {
                    insertBoldCode(txtMessage, "\nMDC: ");
                    insertCode(txtMessage, currentEntry.getMdc());
                }
                
                insertBoldCode(txtMessage, "\nMessage: ");
                insertCode(txtMessage, currentEntry.getMessage());                
            }

            start(txtMessage);
        } catch (BadLocationException e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnCopy = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();

        jPanel1.setLayout(new java.awt.BorderLayout());

        txtMessage.setEditable(false);
        txtMessage.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jScrollPane1.setViewportView(txtMessage);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setBackground(new java.awt.Color(226, 226, 226));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/log4j/icons/copy.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCopy, org.openide.util.NbBundle.getMessage(Log4jViewTopComponent.class, "Log4jViewTopComponent.btnCopy.text")); // NOI18N
        btnCopy.setToolTipText(org.openide.util.NbBundle.getMessage(Log4jViewTopComponent.class, "Log4jViewTopComponent.btnCopy.toolTipText")); // NOI18N
        btnCopy.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        btnCopy.setEnabled(false);
        btnCopy.setFocusable(false);
        btnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCopy);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.WEST);

        setLayout(new java.awt.BorderLayout());
        add(jTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        if (currentEntry != null) {
            ClipboardUtils.copy(txtMessage.getText());
            NotificationUtils.info("Log message information is now copied to clipboard.");
        }
    }//GEN-LAST:event_btnCopyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextPane txtMessage;
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
