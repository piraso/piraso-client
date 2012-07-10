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

import ard.piraso.api.GeneralPreferenceEnum;
import ard.piraso.api.Preferences;
import ard.piraso.ui.api.GeneralSettingsModel;
import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.PreferenceProvider;
import ard.piraso.ui.api.WorkingSetSettings;
import ard.piraso.ui.api.extension.AbstractDialog;
import ard.piraso.ui.api.manager.SingleModelManagers;
import ard.piraso.ui.base.manager.ModelManagers;
import ard.piraso.ui.base.manager.PreferenceProviderManager;
import org.apache.commons.lang.StringUtils;
import org.openide.ErrorManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adeleon
 */
public final class ContextMonitorDialog extends AbstractDialog {

    private DefaultListModel listModel = new DefaultListModel();
    
    private DefaultComboBoxModel cboModel = new DefaultComboBoxModel();

    private Map<String, PreferencePanel> preferencePanels = new HashMap<String, PreferencePanel>();


    /**
     * Creates new form ContextMonitorDialog
     */
    public ContextMonitorDialog() {
        super();
        setTitle("Manage Monitors");
        initComponents();
        initPreferences();
        addButtonRefreshListeners();

        lstMonitors.setModel(listModel);
        cboHost.setModel(cboModel);

        lstMonitors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstMonitors.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) return;
                initSelection((String) lstMonitors.getSelectedValue());
            }
        });

        refresh();
        setLocationRelativeTo(getOwner());
    }

    private void addButtonRefreshListeners() {
        txtName.getDocument().addDocumentListener(REFRESH_BUTTON_DOCUMENT_LISTENER);
        txtAddr.getDocument().addDocumentListener(REFRESH_BUTTON_DOCUMENT_LISTENER);
        cboHost.addItemListener(REFRESH_BUTTON_ITEM_LISTENER);
        cboHost.getEditor().getEditorComponent().addKeyListener(REFRESH_BUTTON_KEY_LISTENER);
    }

    protected void refreshButtons() {
        boolean enabled = checkEnabled();

        btnSave.setEnabled(enabled);
        if(enabled &&  ModelManagers.MONITORS.contains(txtName.getText())) {
            btnRemove.setEnabled(enabled);
        } else {
            btnRemove.setEnabled(false);
        }
    }

    private boolean checkEnabled() {
        if(StringUtils.isBlank(txtName.getText())) {
            return false;
        }
        if(rdoOthers.isSelected() && StringUtils.isBlank(txtAddr.getText())) {
            return false;
        }
        if(StringUtils.isBlank(String.valueOf(cboHost.getSelectedItem()))) {
            return false;
        }

        return true;
    }
    
    private void initPreferences() {
        List<PreferenceProvider> providers = PreferenceProviderManager.INSTANCE.getProviders();

        for(PreferenceProvider provider : providers) {
            PreferencePanel panel = new PreferencePanel(provider);

            preferencePanels.put(provider.getShortName(), panel);
            jtab.addTab(provider.getShortName(), panel);
        }        
    }

    private void initSelection(String name) {
        NewContextMonitorModel model = ModelManagers.MONITORS.get(name);

        if(model == null) {
            // no selection
            return;
        }

        txtName.setText(model.getName());
        cboHost.setSelectedItem(model.getLoggingUrl());

        if(model.getWatchedAddr() == null) {
            rdoMyAddress.setSelected(true);
            txtAddr.setText("");
            txtAddr.setEnabled(false);
        } else {
            rdoOthers.setSelected(true);
            txtAddr.setText(model.getWatchedAddr());
            txtAddr.setEnabled(true);
        }

        for(PreferencePanel panel : preferencePanels.values()) {
            panel.read(model);
        }
    }

    public void refresh() {
        refresh(null);
    }
        
    public void refresh(String name) {
        GeneralSettingsModel general = SingleModelManagers.GENERAL_SETTINGS.get();
        WorkingSetSettings workingSet = SingleModelManagers.WORKING_SET.get();

        listModel.clear();
        cboModel.removeAllElements();

        List<String> monitorNames =  ModelManagers.MONITORS.getNames();

        for(String monitorName : monitorNames) {
            NewContextMonitorModel model = ModelManagers.MONITORS.get(monitorName);
            
            if(model == null) {
                continue;
            }
            
            if(chkWorkingSet.isSelected() && general.getWorkingSetName() != null) {
                String regex = workingSet.getRegex(general.getWorkingSetName());

                if(!monitorName.matches(regex)) {
                    continue;
                }
            }

            listModel.addElement(model.getName());

            if (cboModel.getIndexOf(model.getLoggingUrl()) == -1) {
                cboModel.addElement(model.getLoggingUrl());
            }
        }

        if(name != null) {
            int index = listModel.indexOf(name);

            if(index > -1) {
                lstMonitors.setSelectedIndex(index);
            }

            return;
        }

        // make the first index as selection
        lstMonitors.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnClose = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstMonitors = new javax.swing.JList();
        chkWorkingSet = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jtab = new javax.swing.JTabbedPane();
        jLabel4 = new javax.swing.JLabel();
        txtAddr = new javax.swing.JTextField();
        rdoOthers = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rdoMyAddress = new javax.swing.JRadioButton();
        cboHost = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnClose.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.btnClose.text")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setDividerSize(5);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.jLabel1.text")); // NOI18N

        lstMonitors.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstMonitors);

        chkWorkingSet.setSelected(true);
        chkWorkingSet.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.chkWorkingSet.text")); // NOI18N
        chkWorkingSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkWorkingSetActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                    .add(jLabel1)
                    .add(chkWorkingSet))
                .add(7, 7, 7))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chkWorkingSet)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel2);

        txtName.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.txtName.text")); // NOI18N

        btnSave.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.btnSave.text")); // NOI18N
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnRemove.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.btnRemove.text")); // NOI18N
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnClear.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.btnClear.text")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnSave, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(btnRemove, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(btnClear, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(btnSave)
                .add(1, 1, 1)
                .add(btnRemove)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnClear)
                .addContainerGap())
        );

        jLabel4.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.jLabel4.text")); // NOI18N

        txtAddr.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.txtAddr.text")); // NOI18N
        txtAddr.setEnabled(false);

        buttonGroup1.add(rdoOthers);
        rdoOthers.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.rdoOthers.text")); // NOI18N
        rdoOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOthersActionPerformed(evt);
            }
        });

        jLabel3.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.jLabel3.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.jLabel2.text")); // NOI18N

        buttonGroup1.add(rdoMyAddress);
        rdoMyAddress.setSelected(true);
        rdoMyAddress.setText(org.openide.util.NbBundle.getMessage(ContextMonitorDialog.class, "ContextMonitorDialog.rdoMyAddress.text")); // NOI18N
        rdoMyAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMyAddressActionPerformed(evt);
            }
        });

        cboHost.setEditable(true);
        cboHost.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(rdoMyAddress)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rdoOthers)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtAddr, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4)
                            .add(jLabel2))
                        .add(18, 18, 18)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cboHost, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(txtName))))
                .add(10, 10, 10)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(9, 9, 9))
            .add(jtab)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(cboHost, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(rdoMyAddress)
                            .add(rdoOthers)
                            .add(txtAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3)))
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jtab, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel3);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(btnClose)
                        .add(14, 14, 14))
                    .add(layout.createSequentialGroup()
                        .add(jSplitPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnClose)
                .add(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        try {
            ModelManagers.MONITORS.remove(txtName.getText());
            refresh();
        } catch (IOException e) {
            ErrorManager.getDefault().notify(e);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtName.setText("");
        cboHost.setSelectedItem("");
        rdoMyAddress.setSelected(true);
        txtAddr.setText("");
        txtAddr.setEnabled(false);

        NewContextMonitorModel model = new NewContextMonitorModel();
        model.setPreferences(new Preferences());
        model.getPreferences().addProperty(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), true);

        for(PreferencePanel panel : preferencePanels.values()) {
            panel.read(model);
        }

        // ensure that there are no selected monitors on clear.
        lstMonitors.getSelectionModel().clearSelection();
        txtName.requestFocus();
    }//GEN-LAST:event_btnClearActionPerformed

    private void rdoMyAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMyAddressActionPerformed
        txtAddr.setEnabled(!rdoMyAddress.isSelected());
        refreshButtons();
    }//GEN-LAST:event_rdoMyAddressActionPerformed

    private void rdoOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOthersActionPerformed
        txtAddr.setEnabled(!rdoMyAddress.isSelected());
        
        if(txtAddr.isEnabled()) {
            txtAddr.requestFocus();
        }

        refreshButtons();
    }//GEN-LAST:event_rdoOthersActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        NewContextMonitorModel model = new NewContextMonitorModel();
        model.setPreferences(new Preferences());

        model.setName(txtName.getText());
        model.setLoggingUrl(String.valueOf(cboHost.getSelectedItem()));

        if(rdoOthers.isSelected()) {
            model.setWatchedAddr(txtAddr.getText());
        }

        for(PreferencePanel panel : preferencePanels.values()) {
            panel.store(model);
        }

        try {
            ModelManagers.MONITORS.save(model);
            refresh(model.getName());
        } catch (IOException e) {
            ErrorManager.getDefault().notify(e);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chkWorkingSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWorkingSetActionPerformed
        refresh();
    }//GEN-LAST:event_chkWorkingSetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboHost;
    private javax.swing.JCheckBox chkWorkingSet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jtab;
    private javax.swing.JList lstMonitors;
    private javax.swing.JRadioButton rdoMyAddress;
    private javax.swing.JRadioButton rdoOthers;
    private javax.swing.JTextField txtAddr;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

}
