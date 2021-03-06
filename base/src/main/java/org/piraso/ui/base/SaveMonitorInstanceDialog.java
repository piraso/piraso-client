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

import org.piraso.api.entry.RequestEntry;
import org.piraso.ui.api.extension.AbstractDialog;
import org.piraso.ui.api.util.NotificationUtils;
import org.piraso.io.IOEntryReader;
import org.piraso.io.impl.FileEntrySave;
import org.piraso.io.impl.PirasoFileFilter;
import org.piraso.io.util.IOEntryRequest;
import org.apache.commons.lang.StringUtils;
import org.openide.ErrorManager;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.File;

/**
 *
 * @author adeleon
 */
public class SaveMonitorInstanceDialog extends AbstractDialog {

    private DefaultTableModel tableModel = new DefaultTableModel(0, 2) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    private IOEntryReader reader;

    private String name;

    /**
     * Creates new form SaveMonitorInstanceDialog
     */
    public SaveMonitorInstanceDialog(String name, IOEntryReader reader) {
        super();
        this.reader = reader;
        this.name = name;
        setTitle("Save Monitor Instance");
        initComponents();
        initTable();

        addButtonRefreshListeners();
        setLocationRelativeTo(getOwner());
        getRootPane().setDefaultButton(btnSave);

        refresh();
    }

    private void refresh() {
        tableModel.setRowCount(0);

        for(IOEntryRequest request : reader.getManager().getRequests()) {
            tableModel.addRow(new Object[]{Boolean.TRUE, request.getRequest().getEntry()});
        }
    }

    protected void addButtonRefreshListeners() {
        jtable.getSelectionModel().addListSelectionListener(REFRESH_BUTTON_LIST_SELECTION_LISTENER);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                refreshButtons();
            }
        });
    }

    @Override
    protected void refreshButtons() {
        btnSave.setEnabled(StringUtils.isNotBlank(txtTargetFile.getText()) && hasSelectedOption());
    }    
    
    protected boolean hasSelectedOption() {
        for(int i = 0; i < tableModel.getRowCount(); i++) {
            if((Boolean) tableModel.getValueAt(i, 0)) {
                return true;
            }
        }

        return false;
    }
    
    private void initTable() {
        TableColumn selectionColumn = jtable.getColumnModel().getColumn(0);
        TableColumn boldOption = jtable.getColumnModel().getColumn(1);

        selectionColumn.setHeaderValue("");
        selectionColumn.setPreferredWidth(30);
        selectionColumn.setMaxWidth(30);
        selectionColumn.setCellEditor(jtable.getDefaultEditor(Boolean.class));
        selectionColumn.setCellRenderer(jtable.getDefaultRenderer(Boolean.class));

        boldOption.setHeaderValue("Request URL");
        boldOption.setPreferredWidth(200);

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
        jLabel2 = new javax.swing.JLabel();
        txtTargetFile = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.jLabel1.text")); // NOI18N

        jtable.setModel(tableModel);
        jScrollPane1.setViewportView(jtable);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.jLabel2.text")); // NOI18N

        txtTargetFile.setEditable(false);
        txtTargetFile.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.txtTargetFile.text")); // NOI18N

        btnBrowse.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.btnBrowse.text")); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnSave.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.btnSave.text")); // NOI18N
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.btnCancel.text")); // NOI18N
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
                .add(12, 12, 12)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(txtTargetFile)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBrowse))
                            .add(jScrollPane1))
                        .add(12, 12, 12))))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(387, Short.MAX_VALUE)
                .add(btnSave)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(7, 7, 7)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtTargetFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowse))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnSave))
                .addContainerGap())
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
                .setTitle(NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.browser.title"))
                .setFileFilter(new PirasoFileFilter())
                .setDefaultWorkingDirectory(pirasoSaveDir)
                .createFileChooser();

        String replaceName = StringUtils.replaceChars(name, "[]", "");
        if(!replaceName.endsWith(String.format(".%s", PirasoFileFilter.EXTENSION))) {
            replaceName = String.format("%s.%s", replaceName, PirasoFileFilter.EXTENSION);
        }

        browserFileChooser.setSelectedFile(new File(pirasoSaveDir, replaceName));
        int result = browserFileChooser.showDialog(this, NbBundle.getMessage(SaveMonitorInstanceDialog.class, "SaveMonitorInstanceDialog.browser.approveText"));

        if (JFileChooser.APPROVE_OPTION == result) {
            txtTargetFile.setText(browserFileChooser.getSelectedFile().getAbsolutePath());
            refreshButtons();
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        FileEntrySave entrySave = new FileEntrySave(reader);
        for(int i = 0; i < tableModel.getRowCount(); i++) {
            if((Boolean) tableModel.getValueAt(i, 0)) {
                RequestEntry entry = (RequestEntry) tableModel.getValueAt(i, 1);
                entrySave.addRequest(entry.getRequestId());
            }
        }

        try {
            File file = new File(txtTargetFile.getText());
            entrySave.save(file);
            NotificationUtils.info(String.format("Save to file '%s' was successful.", file.getName()));
            dispose();
        } catch (Exception e) {
            ErrorManager.getDefault().notify(e);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    private javax.swing.JTextField txtTargetFile;
    // End of variables declaration//GEN-END:variables
}
