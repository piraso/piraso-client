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

package ard.piraso.ui.base.views;

import ard.piraso.api.entry.CookieEntry;
import ard.piraso.api.entry.HeaderEntry;
import ard.piraso.api.entry.HttpResponseEntry;
import ard.piraso.ui.api.EntryTabView;
import ard.piraso.ui.api.extension.AbstractEntryViewTopComponent;
import ard.piraso.ui.api.manager.EntryTabViewProviderManager;
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.util.ClipboardUtils;
import ard.piraso.ui.api.util.NotificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.ErrorManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * Top component which displays something.
 */
@ActionID(category = "Window", id = "ard.piraso.ui.base.ResponseViewTopComponent")
@ActionReference(path = "Menu/Window", position = 345)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.base//ResponseView//EN", autostore = false)
@TopComponent.Description(preferredID = "ResponseViewTopComponent", iconBase="ard/piraso/ui/base/icons/location_http.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ResponseViewAction", preferredID = "ResponseViewTopComponent")
public final class ResponseViewTopComponent extends AbstractEntryViewTopComponent<HttpResponseEntry> {

    public ResponseViewTopComponent() {
        super(HttpResponseEntry.class);
        initComponents();
        setName(NbBundle.getMessage(ResponseViewTopComponent.class, "CTL_ResponseViewTopComponent"));
        setToolTipText(NbBundle.getMessage(ResponseViewTopComponent.class, "HINT_ResponseViewTopComponent"));
    }

    @Override
    protected void refreshView() {
        List<EntryTabView> components = new ArrayList<EntryTabView>();

        jTabbedPane.removeAll();

        if(currentEntry != null) {
            components.add(new EntryTabView(jPanel1, "Response"));
            components.addAll(EntryTabViewProviderManager.INSTANCE.getTabView(ResponseViewTopComponent.class, currentEntry));
        }

        refreshRequestView();

        if(CollectionUtils.isNotEmpty(components)) {
            for(EntryTabView tabView : components) {
                jTabbedPane.addTab(tabView.getTitle(), tabView.getComponent());
            }
        }

        repaint();
        revalidate();
    }

    private void refreshRequestView() {
        try {
            txtMessage.setText("");
            btnCopy.setEnabled(currentEntry != null);

            if(currentEntry != null) {
                insertBoldCode(txtMessage, "Status: ");

                if(currentEntry.getStatusReason() != null) {
                    insertCode(txtMessage, currentEntry.getStatusReason());
                } else  {
                    if(currentEntry.getStatus() == 0) {
                        insertCode(txtMessage, "200");
                    } else {
                        insertCode(txtMessage, String.valueOf(currentEntry.getStatus()));
                    }
                }

                if(MapUtils.isNotEmpty(currentEntry.getDateHeader()) || 
                        MapUtils.isNotEmpty(currentEntry.getIntHeader()) ||
                        MapUtils.isNotEmpty(currentEntry.getStringHeader())) {
                    insertBoldCode(txtMessage, "\n\nHeaders: ");
                    
                    if(MapUtils.isNotEmpty(currentEntry.getStringHeader())) {
                        for(Map.Entry<String, HeaderEntry<String>> header : currentEntry.getStringHeader().entrySet()) {
                            if("cookie".equalsIgnoreCase(header.getKey())) {
                                continue;
                            }
                            
                            HeaderEntry<String> h = header.getValue();
                            
                            insertCode(txtMessage, "\n    ");
                            insertUnderline(txtMessage, h.getName());
                            insertCode(txtMessage, ": " + h.getValue());
                        }                        
                    }
                    
                    if(MapUtils.isNotEmpty(currentEntry.getIntHeader())) {
                        for(Map.Entry<String, HeaderEntry<Integer>> header : currentEntry.getIntHeader().entrySet()) {
                            HeaderEntry<Integer> h = header.getValue();
                            
                            insertCode(txtMessage, "\n    ");
                            insertUnderline(txtMessage, h.getName());
                            insertCode(txtMessage, ": " + h.getValue());
                        }                        
                    }
                    
                    if(MapUtils.isNotEmpty(currentEntry.getDateHeader())) {
                        for(Map.Entry<String, HeaderEntry<Long>> header : currentEntry.getDateHeader().entrySet()) {
                            HeaderEntry<Long> h = header.getValue();
                            
                            insertCode(txtMessage, "\n    ");
                            insertUnderline(txtMessage, h.getName());
                            insertCode(txtMessage, ": " + new Date(h.getValue()));
                        }                        
                    }
                }

                if(CollectionUtils.isNotEmpty(currentEntry.getCookies())) {
                    insertBoldCode(txtMessage, "\n\nCookies: ");

                    for(CookieEntry cookie : currentEntry.getCookies()) {
                        insertCode(txtMessage, "\n    ");
                        insertUnderline(txtMessage, cookie.getName());
                        insertCode(txtMessage, ": " + cookie.getValue());
                    }
                }
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

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/copy.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCopy, org.openide.util.NbBundle.getMessage(ResponseViewTopComponent.class, "ResponseViewTopComponent.btnCopy.text")); // NOI18N
        btnCopy.setToolTipText(org.openide.util.NbBundle.getMessage(ResponseViewTopComponent.class, "ResponseViewTopComponent.btnCopy.toolTipText")); // NOI18N
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
            NotificationUtils.info("Response information is now copied to clipboard.");
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
