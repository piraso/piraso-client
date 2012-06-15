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
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.util.ClipboardUtils;
import ard.piraso.ui.api.util.NotificationUtils;

/**
 *
 * @author adeleon
 */
public abstract class FilteredJTextPaneTabView<T> extends AbstractTabView<T> {

    private String copyMessage;
    
    /** Creates new form StackTraceTabView
     *
     * @param entry the entry
     * @param copyMessage the copy message
     */
    public FilteredJTextPaneTabView(T entry, String copyMessage) {
        super(entry);
        initComponents();
        
        this.copyMessage = copyMessage;
        refreshView((Entry) entry);
    }

    protected abstract void btnFilterClickHandle();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnFilter = new javax.swing.JToggleButton();
        btnCopy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEditor = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setBackground(new java.awt.Color(226, 226, 226));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/api/icons/filter.png"))); // NOI18N
        btnFilter.setText(org.openide.util.NbBundle.getMessage(FilteredJTextPaneTabView.class, "FilteredJTextPaneTabView.btnFilter.text")); // NOI18N
        btnFilter.setToolTipText(org.openide.util.NbBundle.getMessage(FilteredJTextPaneTabView.class, "FilteredJTextPaneTabView.btnFilter.toolTipText")); // NOI18N
        btnFilter.setFocusable(false);
        btnFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFilter);

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/api/icons/copy.png"))); // NOI18N
        btnCopy.setText(org.openide.util.NbBundle.getMessage(FilteredJTextPaneTabView.class, "FilteredJTextPaneTabView.btnCopy.text")); // NOI18N
        btnCopy.setToolTipText(org.openide.util.NbBundle.getMessage(FilteredJTextPaneTabView.class, "FilteredJTextPaneTabView.btnCopy.toolTipText")); // NOI18N
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

        add(jToolBar1, java.awt.BorderLayout.WEST);

        txtEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        txtEditor.setEditable(false);
        txtEditor.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jScrollPane1.setViewportView(txtEditor);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        btnFilterClickHandle();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        if (entry != null) {
            ClipboardUtils.copy(txtEditor.getText());
            NotificationUtils.info(copyMessage);
        }
    }//GEN-LAST:event_btnCopyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnCopy;
    protected javax.swing.JToggleButton btnFilter;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JToolBar jToolBar1;
    protected javax.swing.JTextPane txtEditor;
    // End of variables declaration//GEN-END:variables
}
