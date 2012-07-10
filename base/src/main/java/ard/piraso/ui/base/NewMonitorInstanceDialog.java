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

import ard.piraso.ui.api.GeneralSettingsModel;
import ard.piraso.ui.api.WorkingSetSettings;
import ard.piraso.ui.api.extension.AbstractDialog;
import ard.piraso.ui.api.manager.SingleModelManagers;
import ard.piraso.ui.base.manager.ModelManagers;

import javax.swing.*;

/**
 *
 * @author adeleon
 */
public final class NewMonitorInstanceDialog extends AbstractDialog {
    
    private DefaultComboBoxModel cboModel = new DefaultComboBoxModel();

    /**
     * Creates new form NewContextMonitorDialog
     */
    public NewMonitorInstanceDialog() {
        super();
        setTitle("New Context Monitor");
        initComponents();
        
        getRootPane().setDefaultButton(btnSave);
        setLocationRelativeTo(getOwner());       
        
        refresh();        
    }
    
    private void refresh() {
        GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
        WorkingSetSettings workingSet = SingleModelManagers.WORKING_SET.get();

        cboModel.removeAllElements();
        
        for(String name : ModelManagers.PROFILES.getNames()) {
            if(chkWorkingSet.isSelected() && model.getWorkingSetName() != null) {
                String regex = workingSet.getRegex(model.getWorkingSetName());

                if(!name.matches(regex)) {
                    continue;
                }
            }

            cboModel.addElement(new ComboItem(Type.PROFILE, name));
        }
        
        if(!chkHide.isSelected()) {
            for(String name : ModelManagers.MONITORS.getNames()) {
                if(chkWorkingSet.isSelected() && model.getWorkingSetName() != null) {
                    String regex = workingSet.getRegex(model.getWorkingSetName());

                    if(!name.matches(regex)) {
                        continue;
                    }
                }

                cboModel.addElement(new ComboItem(Type.MONITOR, name));
            }            
        }
            
        refreshButtons();
    }

    @Override
    protected void refreshButtons() {
        if(chkHide.isSelected()) {
            btnSave.setEnabled(!ModelManagers.PROFILES.isEmpty());
        } else {
            btnSave.setEnabled(!ModelManagers.MONITORS.isEmpty());
        }
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
        cboMonitor = new javax.swing.JComboBox();
        chkHide = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblDesc = new javax.swing.JLabel();
        chkWorkingSet = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.jLabel1.text")); // NOI18N

        cboMonitor.setModel(cboModel);

        chkHide.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.chkHide.text")); // NOI18N
        chkHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHideActionPerformed(evt);
            }
        });

        btnSave.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.btnSave.text")); // NOI18N
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblDesc.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.lblDesc.text")); // NOI18N

        chkWorkingSet.setSelected(true);
        chkWorkingSet.setText(org.openide.util.NbBundle.getMessage(NewMonitorInstanceDialog.class, "NewMonitorInstanceDialog.chkWorkingSet.text")); // NOI18N
        chkWorkingSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkWorkingSetActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(lblDesc)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cboMonitor, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(chkHide)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(chkWorkingSet)
                                .add(0, 87, Short.MAX_VALUE)))))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnSave)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .add(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cboMonitor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chkHide)
                    .add(chkWorkingSet))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lblDesc)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnSave)
                    .add(btnCancel))
                .add(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        ComboItem item = (ComboItem) cboMonitor.getSelectedItem();

        dispose();
        if(item.type == Type.PROFILE) {
            ContextMonitorDispatcher.forwardByProfileName(item.value);
        } else {
            ContextMonitorDispatcher.forwardByMonitorName(item.value);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chkHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHideActionPerformed
        refresh();
    }//GEN-LAST:event_chkHideActionPerformed

    private void chkWorkingSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWorkingSetActionPerformed
        refresh();
    }//GEN-LAST:event_chkWorkingSetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboMonitor;
    private javax.swing.JCheckBox chkHide;
    private javax.swing.JCheckBox chkWorkingSet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDesc;
    // End of variables declaration//GEN-END:variables

    private enum Type {
        PROFILE,
        MONITOR
    }

    private class ComboItem {
        private String value;
        
        private Type type;
        
        private ComboItem(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return (type == Type.PROFILE ? "Profile: " : "Monitor: ") + value;
        }        
    }
}