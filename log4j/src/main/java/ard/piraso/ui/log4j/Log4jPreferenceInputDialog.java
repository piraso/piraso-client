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

import ard.piraso.ui.api.extension.AbstractDialog;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author adeleon
 */
public final class Log4jPreferenceInputDialog extends AbstractDialog {

    private boolean cancelled;
    
    /**
     * Creates new form Log4jPreferenceInputDialog
     */
    public Log4jPreferenceInputDialog() {
        super();
        initComponents();

        addButtonRefreshListeners();
        getRootPane().setDefaultButton(btnOk);
        setLocationRelativeTo(getOwner());
    }

    @Override
    protected void refreshButtons() {
        btnOk.setEnabled(StringUtils.isNotBlank(txtName.getText()) && StringUtils.isNotBlank(txtValue.getText()));
    }

    protected void addButtonRefreshListeners() {
        txtName.getDocument().addDocumentListener(REFRESH_BUTTON_DOCUMENT_LISTENER);
        txtValue.getDocument().addDocumentListener(REFRESH_BUTTON_DOCUMENT_LISTENER);
    }

    public boolean isCancelled() {
        return cancelled;
    }
    
    public String getLogger() {
        return txtName.getText();
    }

    public void setLogger(String logger) {
        txtName.setText(logger);
    }
    
    public String getDescription() {
        return txtValue.getText();
    }

    public void setDescription(String description) {
        txtValue.setText(description);
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
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.jLabel1.text")); // NOI18N

        txtName.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.txtName.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.jLabel2.text")); // NOI18N

        txtValue.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.txtValue.text")); // NOI18N

        btnOk.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.btnOk.text")); // NOI18N
        btnOk.setEnabled(false);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceInputDialog.class, "Log4jPreferenceInputDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap(219, Short.MAX_VALUE)
                        .add(btnOk)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnCancel))
                    .add(layout.createSequentialGroup()
                        .add(15, 15, 15)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(txtName)
                            .add(txtValue))))
                .add(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnOk)
                    .add(btnCancel))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cancelled = true;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        cancelled = false;
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
