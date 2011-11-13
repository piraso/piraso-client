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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.base;

import ard.piraso.api.entry.RequestEntry;
import ard.piraso.ui.base.model.IOEntryComboBoxModel;
import ard.piraso.ui.base.model.IOEntryTableModel;
import ard.piraso.ui.io.IOEntryReader;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Top component which displays something.
 */
public final class ContextMonitorTopComponent extends TopComponent {
    private static final String ICON_PATH = "/ard/piraso/ui/base/icons/remote_logger.png";

    private IOEntryReader reader;

    public ContextMonitorTopComponent() {}
    public ContextMonitorTopComponent(IOEntryReader reader) {
        setName(NbBundle.getMessage(ContextMonitorTopComponent.class, "CTL_ContextMonitorTopComponent"));
        setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "HINT_ContextMonitorTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        this.reader = reader;
        tableModel = new IOEntryTableModel(reader);
        comboBoxModel = tableModel.getComboBoxModel();
        
        initComponents();
        initTable();
        initComboBox();
        refreshUIStates();
    }

    private void initComboBox() {
        cboUrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestEntry entry = (RequestEntry) comboBoxModel.getSelectedItem();
                tableModel.setCurrentRequestId(entry.getRequestId());
            }
        });

//        cboUrl.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                RequestEntry entry = (RequestEntry) e.getItem();
//                tableModel.setCurrentRequestId(entry.getRequestId(), true);
//            }
//        });
    }

    private void initTable() {
        TableColumn numColumn = table.getColumnModel().getColumn(0);
        TableColumn typeColumn = table.getColumnModel().getColumn(1);
        TableColumn messageColumn = table.getColumnModel().getColumn(2);
        TableColumn elapseColumn = table.getColumnModel().getColumn(3);

        ContextMonitorTableCellRenderer renderer = new ContextMonitorTableCellRenderer();
        numColumn.setHeaderValue("");
        numColumn.setMaxWidth(35);
        numColumn.setCellRenderer(renderer);

        typeColumn.setHeaderValue("Type");
        typeColumn.setMaxWidth(125);
        typeColumn.setCellRenderer(renderer);

        messageColumn.setHeaderValue("Message");
        messageColumn.setPreferredWidth(700);
        messageColumn.setCellRenderer(renderer);

        elapseColumn.setHeaderValue("Elapse");
        elapseColumn.setMaxWidth(100);
        elapseColumn.setCellRenderer(renderer);

        table.setShowHorizontalLines(false);
        table.setAutoscrolls(true);
        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        // set table model variables.
        tableModel.setOwningTable(table);
    }

    private void refreshUIStates() {
        if(btnLockUrl.isSelected()) {
            btnLockUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/lock.png")));
        } else {
            btnLockUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/lock_pressed.png")));
        }

        cboUrl.setEnabled(btnLockUrl.isSelected());
        tableModel.setAllowScrolling(!btnLockUrl.isSelected());
    }

    @Override
    public void componentClosed() {
        reader.stop();
    }

    @Override
    protected void componentOpened() {
        new Thread(getStartRunnable()).start();
    }

    public Runnable getStartRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                reader.start();
            }
        };
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbar = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cboUrl = new javax.swing.JComboBox();
        btnLockUrl = new javax.swing.JToggleButton();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        toolbar.setBackground(new java.awt.Color(226, 226, 226));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(jSeparator1);

        cboUrl.setFont(new java.awt.Font("Monospaced", 0, 12));
        cboUrl.setModel(comboBoxModel);
        toolbar.add(cboUrl);

        btnLockUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/lock_pressed.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnLockUrl, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnLockUrl.text")); // NOI18N
        btnLockUrl.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnLockUrl.toolTipText")); // NOI18N
        btnLockUrl.setFocusable(false);
        btnLockUrl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLockUrl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLockUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLockUrlActionPerformed(evt);
            }
        });
        toolbar.add(btnLockUrl);

        add(toolbar, java.awt.BorderLayout.NORTH);

        tableScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableScrollPane.setFont(new java.awt.Font("Monospaced", 0, 10));

        table.setFont(new java.awt.Font("Monospaced", 0, 12));
        table.setModel(tableModel);
        tableScrollPane.setViewportView(table);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLockUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLockUrlActionPerformed
        refreshUIStates();
    }//GEN-LAST:event_btnLockUrlActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JToggleButton btnLockUrl;
    protected javax.swing.JComboBox cboUrl;
    protected IOEntryComboBoxModel comboBoxModel;
    protected javax.swing.JToolBar.Separator jSeparator1;
    protected javax.swing.JTable table;
    protected IOEntryTableModel tableModel;
    protected javax.swing.JScrollPane tableScrollPane;
    protected javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

    @Override
    public int getPersistenceType() {
        return PERSISTENCE_NEVER;
    }
}
