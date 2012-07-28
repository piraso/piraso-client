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
import ard.piraso.ui.log4j.provider.Log4jPreferencesModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adeleon
 */
public class Log4jPreferenceDialog extends AbstractDialog {

    private DefaultTableModel tableModel = new DefaultTableModel(0, 2) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    /**
     * Creates new form Log4jPreferenceDialog
     */
    public Log4jPreferenceDialog() {
        super();
        initComponents();
        initTable();

        getRootPane().setDefaultButton(btnSave);
        setLocationRelativeTo(getOwner());
        addButtonRefreshListeners();
        refresh();
    }

    public void refresh() {
        Log4jPreferencesModel model = SingleModelManagers.LOG4J_PREFERENCES.get();

        if(model == null) {
            return;
        }

        if(CollectionUtils.isNotEmpty(model.getPreferences())) {
            for(Log4jPreferencesModel.Child child : model.getPreferences()) {
                tableModel.addRow(new Object[]{child.getLogger(), child.getDescription()});
            }
        }

        refreshButtons();
    }


    protected void addButtonRefreshListeners() {
        jtable.getSelectionModel().addListSelectionListener(REFRESH_BUTTON_LIST_SELECTION_LISTENER);
    }

    @Override
    protected void refreshButtons() {
        btnRemove.setEnabled(jtable.getSelectedRow() >= 0);
        btnEdit.setEnabled(jtable.getSelectedRow() >= 0);
        btnSave.setEnabled(tableModel.getRowCount() > 0);
    }

    private void initTable() {
        TableColumn loggerColumn = jtable.getColumnModel().getColumn(0);
        TableColumn descriptionColumn = jtable.getColumnModel().getColumn(1);

        loggerColumn.setHeaderValue("Logger");
        loggerColumn.setPreferredWidth(120);

        descriptionColumn.setHeaderValue("Description");
        descriptionColumn.setPreferredWidth(300);

        jtable.setShowHorizontalLines(false);
        jtable.setAutoscrolls(true);
        jtable.setColumnSelectionAllowed(false);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.getTableHeader().setReorderingAllowed(false);
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
        jtable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.jLabel1.text")); // NOI18N

        jtable.setModel(tableModel);
        jScrollPane1.setViewportView(jtable);

        btnAdd.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.btnAdd.text")); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.btnRemove.text")); // NOI18N
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnEdit.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.btnEdit.text")); // NOI18N
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(btnRemove, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(btnEdit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(btnAdd)
                .add(1, 1, 1)
                .add(btnRemove)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnEdit)
                .addContainerGap())
        );

        btnSave.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.btnSave.text")); // NOI18N
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(Log4jPreferenceDialog.class, "Log4jPreferenceDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(14, 14, 14)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(362, Short.MAX_VALUE)
                        .add(btnSave)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnSave))
                .add(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Log4jPreferenceInputDialog dialog = new Log4jPreferenceInputDialog();

        dialog.setVisible(true);

        if(!dialog.isCancelled()) {
            // maybe we are adding an existing value
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                if(StringUtils.equals(dialog.getLogger(), String.valueOf(tableModel.getValueAt(i, 0)))) {
                    tableModel.setValueAt(dialog.getLogger(), jtable.getSelectedRow(), 0);
                    tableModel.setValueAt(dialog.getDescription(), jtable.getSelectedRow(), 1);
                    return;
                }
            }

            tableModel.addRow(new Object[]{
                    dialog.getLogger(),
                    dialog.getDescription()
            });
        }

        refreshButtons();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(ArrayUtils.isNotEmpty(jtable.getSelectedRows())) {
            List<String> removed = new ArrayList<String>();
            for(int index : jtable.getSelectedRows()) {
                removed.add(String.valueOf(tableModel.getValueAt(index, 0)));
            }

            for(String logger : removed) {
                removeLogger(logger);
            }
        }

        refreshButtons();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void removeLogger(String logger) {
        for(int i = 0; i < tableModel.getRowCount(); i++) {
            if(StringUtils.equals(logger, String.valueOf(tableModel.getValueAt(i, 0)))) {
                tableModel.removeRow(i);
                return;
            }
        }
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Log4jPreferenceInputDialog dialog = new Log4jPreferenceInputDialog();

        String logger = (String) tableModel.getValueAt(jtable.getSelectedRow(), 0);
        String description = (String) tableModel.getValueAt(jtable.getSelectedRow(), 1);
        dialog.setLogger(logger);
        dialog.setDescription(description);
        dialog.setVisible(true);

        if(!dialog.isCancelled()) {
            tableModel.setValueAt(dialog.getLogger(), jtable.getSelectedRow(), 0);
            tableModel.setValueAt(dialog.getDescription(), jtable.getSelectedRow(), 1);
        }

        refreshButtons();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Log4jPreferencesModel model = new Log4jPreferencesModel();

        for(int i = 0; i < tableModel.getRowCount(); i++) {
            String logger = (String) tableModel.getValueAt(i, 0);
            String description = (String) tableModel.getValueAt(i, 1);

            model.add(logger, description);
        }

        SingleModelManagers.LOG4J_PREFERENCES.save(model);
        dispose();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    // End of variables declaration//GEN-END:variables
}
