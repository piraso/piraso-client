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

import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 * Top component which displays something.
 */
public final class ContextMonitorTopComponent extends TopComponent {

    public ContextMonitorTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ContextMonitorTopComponent.class, "CTL_ContextMonitorTopComponent"));
        setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "HINT_ContextMonitorTopComponent"));
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

        cboUrl.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        cboUrl.setModel(new DefaultComboBoxModel());
        toolbar.add(cboUrl);

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

        table.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        table.setModel(new DefaultTableModel());
        tableScrollPane.setViewportView(table);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void btnLockUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLockUrlActionPerformed
//        synchronized(getTreeLock()) {
//            if(btnLockUrl.isSelected() && !cboUrl.isEnabled()) {
//                setEnableComponents(true);
//            }
//        }
}//GEN-LAST:event_btnLockUrlActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnLockUrl;
    private javax.swing.JComboBox cboUrl;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void componentClosed() {
        DefaultTableModel model = new DefaultTableModel();
    }

    @Override
    protected void componentOpened() {
    }

    @Override
    public int getPersistenceType() {
        return PERSISTENCE_NEVER;
    }

}