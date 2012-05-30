/*
 * Copyright 2012 adeleon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ard.piraso.ui.base;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adeleon
 */
public class URLFilterPanel extends javax.swing.JPanel {

    /**
     * Creates new form URLFilterPanel
     */
    public URLFilterPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chkEnableFiltering = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtURL = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();
        btnAdd2 = new javax.swing.JButton();

        chkEnableFiltering.setText(org.openide.util.NbBundle.getMessage(URLFilterPanel.class, "URLFilterPanel.chkEnableFiltering.text")); // NOI18N
        chkEnableFiltering.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableFilteringActionPerformed(evt);
            }
        });

        jScrollPane1.setEnabled(false);

        table.setModel(tableModel);
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);

        txtURL.setText(org.openide.util.NbBundle.getMessage(URLFilterPanel.class, "URLFilterPanel.txtURL.text")); // NOI18N
        txtURL.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtURL.setBounds(new java.awt.Rectangle(1, 1, 1, 1));
        txtURL.setEnabled(false);

        btnAdd.setText(org.openide.util.NbBundle.getMessage(URLFilterPanel.class, "URLFilterPanel.btnAdd.text")); // NOI18N
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnAdd1.setText(org.openide.util.NbBundle.getMessage(URLFilterPanel.class, "URLFilterPanel.btnAdd1.text")); // NOI18N
        btnAdd1.setEnabled(false);
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        btnAdd2.setText(org.openide.util.NbBundle.getMessage(URLFilterPanel.class, "URLFilterPanel.btnAdd2.text")); // NOI18N
        btnAdd2.setEnabled(false);
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(txtURL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btnAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btnAdd1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btnAdd2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(chkEnableFiltering))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(chkEnableFiltering)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnAdd))
                .add(1, 1, 1)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(btnAdd1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnAdd2)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkEnableFilteringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnableFilteringActionPerformed
        //refreshCheckFiltering();
    }//GEN-LAST:event_chkEnableFilteringActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        //tableModel.addRow(new Object[] {Boolean.TRUE, txtURL.getText()});
        txtURL.setText("");
        txtURL.requestFocus();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JCheckBox chkEnableFiltering;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable table;
    protected DefaultTableModel tableModel = new DefaultTableModel();
    private javax.swing.JTextField txtURL;
    // End of variables declaration//GEN-END:variables
}
