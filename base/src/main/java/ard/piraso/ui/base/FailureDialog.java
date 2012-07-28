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

package ard.piraso.ui.base;

import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.extension.AbstractDialog;
import ard.piraso.ui.api.manager.FontProviderManager;
import org.openide.ErrorManager;

import java.util.Map;

import static ard.piraso.ui.api.util.JTextPaneUtils.insertHeaderCode;
import static ard.piraso.ui.api.util.JTextPaneUtils.insertText;

/**
 *
 * @author adeleon
 */
public class FailureDialog extends AbstractDialog {

    /**
     * Creates new form FailureDialog
     */
    public FailureDialog() {
        super();
        initComponents();

        setLocationRelativeTo(getOwner());
    }
    
    public void show(Map<NewContextMonitorModel, String> failures) {
        txtErrors.setText("");
        
        try {
            for(Map.Entry<NewContextMonitorModel, String> failure : failures.entrySet()) {
                insertHeaderCode(txtErrors, failure.getKey().getName() + ": ");
                insertText(txtErrors, failure.getValue());
                insertText(txtErrors, "\n");
            }
        } catch(Exception e) {
            ErrorManager.getDefault().notify(e);
        }
        
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtErrors = new javax.swing.JTextPane();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/error2.png"))); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(FailureDialog.class, "FailureDialog.jLabel1.text")); // NOI18N

        txtErrors.setEditable(false);
        txtErrors.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jScrollPane1.setViewportView(txtErrors);

        btnClose.setText(org.openide.util.NbBundle.getMessage(FailureDialog.class, "FailureDialog.btnClose.text")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(btnClose))
                    .add(layout.createSequentialGroup()
                        .add(14, 14, 14)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                            .add(jLabel1))))
                .add(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnClose)
                .add(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtErrors;
    // End of variables declaration//GEN-END:variables
}
