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

package org.piraso.ui.base;

import org.piraso.ui.api.extension.AbstractDialog;
import org.piraso.ui.api.util.NotificationUtils;
import org.piraso.io.impl.PirasoFileFilter;
import org.apache.commons.lang.StringUtils;
import org.openide.ErrorManager;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle;

import javax.swing.*;
import java.io.File;

/**
 *
 * @author adeleon
 */
public class OpenMonitorInstanceDialog extends AbstractDialog {
    
    private File selectedFile;

    /**
     * Creates new form OpenMonitorInstanceDialog
     */
    public OpenMonitorInstanceDialog() {
        super();
        setTitle("Open Monitor Instance");
        initComponents();
        setLocationRelativeTo(getOwner());
        getRootPane().setDefaultButton(btnOpen);
    }
    
    @Override
    protected void refreshButtons() {
        btnOpen.setEnabled(StringUtils.isNotBlank(txtSourceFile.getText()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBrowse = new javax.swing.JButton();
        txtSourceFile = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnOpen = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnBrowse.setText(org.openide.util.NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.btnBrowse.text")); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        txtSourceFile.setEditable(false);
        txtSourceFile.setText(org.openide.util.NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.txtSourceFile.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.jLabel2.text")); // NOI18N

        btnOpen.setText(org.openide.util.NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.btnOpen.text")); // NOI18N
        btnOpen.setEnabled(false);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.btnCancel.text")); // NOI18N
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
                .addContainerGap(298, Short.MAX_VALUE)
                .add(btnOpen)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel2)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(txtSourceFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(btnBrowse)
                    .add(12, 12, 12)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnOpen))
                .add(15, 15, 15))
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .addContainerGap()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel2)
                        .add(txtSourceFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(btnBrowse))
                    .addContainerGap(80, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        File home = new File(System.getProperty("user.home"));
        File pirasoDir = new File(home, "piraso");
        File pirasoSaveDir = new File(pirasoDir, "saved");
        if (!pirasoSaveDir.isDirectory()) {
            pirasoSaveDir.mkdirs();
        }

        JFileChooser browserFileChooser = new FileChooserBuilder("piraso-saved-dir")
                .setTitle(NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.browser.title"))
                .setFileFilter(new PirasoFileFilter())
                .setDefaultWorkingDirectory(pirasoSaveDir)
                .createFileChooser();

        browserFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int result = browserFileChooser.showDialog(this, NbBundle.getMessage(OpenMonitorInstanceDialog.class, "OpenMonitorInstanceDialog.browser.approveText"));

        if (JFileChooser.APPROVE_OPTION == result) {
            selectedFile = browserFileChooser.getSelectedFile();
            txtSourceFile.setText(browserFileChooser.getSelectedFile().getAbsolutePath());
            refreshButtons();
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        try {
            ContextMonitorDispatcher.forward(selectedFile);
            NotificationUtils.info(String.format("Open Monitor Instance from file '%s' was successful.", selectedFile.getName()));
            dispose();
        } catch (Exception e) {
            ErrorManager.getDefault().notify(e);
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOpen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtSourceFile;
    // End of variables declaration//GEN-END:variables
}
