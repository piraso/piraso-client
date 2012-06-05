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

import ard.piraso.api.JacksonUtils;
import ard.piraso.ui.api.ImportHandler;
import ard.piraso.ui.api.ObjectEntrySettings;
import ard.piraso.ui.api.extension.AbstractDialog;
import ard.piraso.ui.api.util.NotificationUtils;
import ard.piraso.ui.base.manager.ImportExportProviderManager;
import org.apache.commons.collections.MapUtils;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adeleon
 */
public class ImportDialog extends AbstractDialog {
    
    private DefaultTableModel tableModel = new DefaultTableModel(0, 2) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    private ObjectEntrySettings settings;

    private Map<String, ImportHandler> handlers;

    private File selectedFile;

    /**
     * Creates new form ImportDialog
     */
    public ImportDialog() {
        super();
        setTitle("Import");

        initComponents();
        initTable();
        addButtonRefreshListeners();
        setLocationRelativeTo(getOwner());
        getRootPane().setDefaultButton(btnImport);
    }

    private void refresh() {
        if(settings != null && MapUtils.isEmpty(settings.getModels())) {
            return;
        }

        handlers = new HashMap<String, ImportHandler>();
        List<ImportHandler> options = ImportExportProviderManager.INSTANCE.getImportHandlers();
        for(ImportHandler handler : options) {
            handlers.put(handler.getOption(), handler);
        }

        tableModel.setRowCount(0);

        for(Map.Entry<String, String> entry : settings.getModels().entrySet()) {
            if(!handlers.containsKey(entry.getKey())) {
                continue;
            }

            tableModel.addRow(new Object[]{Boolean.FALSE, entry.getKey()});
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
        btnImport.setEnabled(StringUtils.isNotBlank(txtSourceFile.getText()) && hasSelectedOption());
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

        boldOption.setHeaderValue("Option");
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
        txtSourceFile = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.jLabel1.text")); // NOI18N

        jtable.setModel(tableModel);
        jScrollPane1.setViewportView(jtable);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.jLabel2.text")); // NOI18N

        txtSourceFile.setEditable(false);
        txtSourceFile.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.txtSourceFile.text")); // NOI18N

        btnBrowse.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.btnBrowse.text")); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnImport.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.btnImport.text")); // NOI18N
        btnImport.setEnabled(false);
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnCancel.setText(org.openide.util.NbBundle.getMessage(ImportDialog.class, "ImportDialog.btnCancel.text")); // NOI18N
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
                                .add(txtSourceFile)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBrowse))
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .add(12, 12, 12))))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(369, Short.MAX_VALUE)
                .add(btnImport)
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
                    .add(txtSourceFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnBrowse))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnImport))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        File home = new File(System.getProperty("user.home"));
        File pirasoDir = new File(home, "piraso");
        if (!pirasoDir.isDirectory()) {
            pirasoDir.mkdirs();
        }

        JFileChooser browserFileChooser = new FileChooserBuilder("piraso-dir")
                .setTitle(NbBundle.getMessage(ExportDialog.class, "ImportDialog.browser.title"))
                .setDefaultWorkingDirectory(pirasoDir)
                .createFileChooser();

        browserFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int result = browserFileChooser.showDialog(this, NbBundle.getMessage(ExportDialog.class, "ImportDialog.browser.approveText"));

        if (JFileChooser.APPROVE_OPTION == result) {
            selectedFile = browserFileChooser.getSelectedFile();
            txtSourceFile.setText(browserFileChooser.getSelectedFile().getAbsolutePath());

            try {
                settings = JacksonUtils.createMapper().readValue(selectedFile, ObjectEntrySettings.class);
            } catch (IOException e) {
                ErrorManager.getDefault().notify(e);
                return;
            }

            refresh();
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        try {
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                if((Boolean) tableModel.getValueAt(i, 0)) {
                    String key = String.valueOf(tableModel.getValueAt(i, 1));
                    ImportHandler handler = handlers.get(key);
                    String settingsStr = settings.getModels().get(key);

                    handler.handle(settingsStr);
                }
            }

            NotificationUtils.info(String.format("Import of file '%s' was successful.", selectedFile.getName()));
            dispose();
        } catch(Exception e) {
            ErrorManager.getDefault().notify(e);
        }
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnImport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    private javax.swing.JTextField txtSourceFile;
    // End of variables declaration//GEN-END:variables
}
