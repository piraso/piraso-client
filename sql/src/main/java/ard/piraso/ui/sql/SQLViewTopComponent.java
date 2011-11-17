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

package ard.piraso.ui.sql;

import SQLinForm_200.SQLForm;
import ard.piraso.api.sql.SQLParameterEntry;
import ard.piraso.api.sql.SQLViewEntry;
import ard.piraso.ui.api.extension.AlignableTableCellRendererImpl;
import ard.piraso.ui.api.util.ClipboardUtils;
import ard.piraso.ui.api.util.NotificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.*;
import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Top component which displays something.
 */
@ActionID(category = "Window", id = "ard.piraso.ui.sql.SQLViewTopComponent")
@ActionReference(path = "Menu/Window", position = 333)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.sql//SQLView//EN", autostore = false)
@TopComponent.Description(preferredID = "SQLViewTopComponent", iconBase="ard/piraso/ui/sql/icons/sql.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SQLViewAction", preferredID = "SQLViewTopComponent")
public final class SQLViewTopComponent extends TopComponent implements LookupListener {

    private Lookup.Result result = null;
    
    private SQLViewEntry currentEntry;

    public SQLViewTopComponent() {
        initComponents();
        initScrollPane();
        initTable();
        setName(NbBundle.getMessage(SQLViewTopComponent.class, "CTL_SQLViewTopComponent"));
        setToolTipText(NbBundle.getMessage(SQLViewTopComponent.class, "HINT_SQLViewTopComponent"));
    }
    
    private void initScrollPane() {
        jSplitPane1.setLeftComponent(scrollTable);
        jSplitPane1.setRightComponent(scrollSQLPane);
    }
    
    private void initTable() {
        TableColumn numColumn = table.getColumnModel().getColumn(0);
        TableColumn methodColumn = table.getColumnModel().getColumn(1);
        TableColumn typeColumn = table.getColumnModel().getColumn(2);
        TableColumn valueColumn = table.getColumnModel().getColumn(3);

        numColumn.setHeaderValue("");
        numColumn.setMaxWidth(30);
        numColumn.setCellRenderer(new AlignableTableCellRendererImpl(SwingConstants.RIGHT, false, true));

        methodColumn.setHeaderValue("Method");
        methodColumn.setPreferredWidth(300);

        typeColumn.setHeaderValue("Type");
        typeColumn.setPreferredWidth(200);
        
        valueColumn.setHeaderValue("Value");
        valueColumn.setPreferredWidth(200);
    }
    
    private void refresh() {
        if(currentEntry != null) {
            String sql = btnReplaceParameters.isSelected() ? currentEntry.getParameterReplacedSql() : 
                    currentEntry.getSql();
            
            if(StringUtils.isNotEmpty(sql)) {            
                if(btnFormat.isSelected()) {
                    SQLForm form = createSQLInForm();                
                    txtSQL.setDocument(form.formatSQL(sql));
                } else {
                    txtSQL.setText(sql);
                }            
            }
            
            tableModel.setRowCount(0);
            if(MapUtils.isNotEmpty(currentEntry.getParameters())) {
                List<Integer> indices = new ArrayList<Integer>(currentEntry.getParameters().keySet());
                Collections.sort(indices);
                
                for(Integer index : indices) {
                    SQLParameterEntry parameter = currentEntry.getParameters().get(index);
                    Object[] row = {index, parameter.getMethodName(), parameter.getParameterClassNames()[1], parameter.getArguments()[1].getStrValue()};

                    tableModel.addRow(row);
                }
            }
        } else {
            tableModel.setRowCount(0);
            txtSQL.setText("");            
        }
        
        txtSQL.setEditable(false);
        txtSQL.select(0, 0);        
    }
    
    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(SQLViewEntry.class);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
        result = null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void resultChanged(LookupEvent evt) {
        Lookup.Result<SQLViewEntry> r = (Lookup.Result<SQLViewEntry>) evt.getSource();
        Collection<? extends SQLViewEntry> entries = r.allInstances();
        
        if(CollectionUtils.isNotEmpty(entries)) {
            currentEntry = entries.iterator().next();
            refresh();
        }
    }
    
    private SQLForm createSQLInForm() {
        SQLForm form = new SQLForm();
        form.setAlignmentKeyword(true);
        form.setAlignmentOperator(true);
        form.setLowerCase(true);
        form.setSuppressEmptyLine(true);
        form.setSuppressLinebreak(true);
        form.setSuppressSpace(true);
        form.setIndentJoin(true);
        form.setSuppressEmptyLine(true);
        form.setSuppressLinebreak(true);
        form.setSuppressSpace(true);
        form.setLineBreak(true, false, true, false, false, false);

        return form;
    }
    
    void writeProperties(java.util.Properties p) {
        p.setProperty("replaceParameters", String.valueOf(btnReplaceParameters.isSelected()));
        p.setProperty("formatted", String.valueOf(btnFormat.isSelected()));
        p.setProperty("deviderLocation", String.valueOf(jSplitPane1.getDividerLocation()));
    }

    void readProperties(java.util.Properties p) {
        btnReplaceParameters.setSelected(Boolean.parseBoolean(p.getProperty("replaceParameters", "false")));
        btnFormat.setSelected(Boolean.parseBoolean(p.getProperty("formatted", "true")));
        jSplitPane1.setDividerLocation(Integer.parseInt(p.getProperty("deviderLocation", "500")));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollSQLPane = new javax.swing.JScrollPane();
        txtSQL = new javax.swing.JTextPane();
        scrollTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnReplaceParameters = new javax.swing.JToggleButton();
        btnFormat = new javax.swing.JToggleButton();
        btnCopy = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();

        txtSQL.setEditable(false);
        txtSQL.setFont(new java.awt.Font("Monospaced", 0, 12));
        scrollSQLPane.setViewportView(txtSQL);

        table.setFont(new java.awt.Font("Monospaced", 0, 12));
        table.setModel(tableModel);
        scrollTable.setViewportView(table);

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setBackground(new java.awt.Color(226, 226, 226));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnReplaceParameters.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/sql/icons/replace_variables.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnReplaceParameters, org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnReplaceParameters.text")); // NOI18N
        btnReplaceParameters.setToolTipText(org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnReplaceParameters.toolTipText")); // NOI18N
        btnReplaceParameters.setFocusable(false);
        btnReplaceParameters.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReplaceParameters.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReplaceParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplaceParametersActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReplaceParameters);

        btnFormat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/sql/icons/format.png"))); // NOI18N
        btnFormat.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(btnFormat, org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnFormat.text")); // NOI18N
        btnFormat.setToolTipText(org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnFormat.toolTipText")); // NOI18N
        btnFormat.setFocusable(false);
        btnFormat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFormat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormatActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFormat);

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/sql/icons/copy.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCopy, org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnCopy.text")); // NOI18N
        btnCopy.setToolTipText(org.openide.util.NbBundle.getMessage(SQLViewTopComponent.class, "SQLViewTopComponent.btnCopy.toolTipText")); // NOI18N
        btnCopy.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        btnCopy.setFocusable(false);
        btnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCopy);

        add(jToolBar1, java.awt.BorderLayout.LINE_START);
        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        ClipboardUtils.copy(txtSQL.getText());
        NotificationUtils.info("Notification", "SQL copied to clipboard.");
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnReplaceParametersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplaceParametersActionPerformed
        refresh();
    }//GEN-LAST:event_btnReplaceParametersActionPerformed

    private void btnFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormatActionPerformed
        refresh();
    }//GEN-LAST:event_btnFormatActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JToggleButton btnFormat;
    private javax.swing.JToggleButton btnReplaceParameters;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane scrollSQLPane;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable table;
    private DefaultTableModel tableModel = new DefaultTableModel(0, 4);
    private javax.swing.JTextPane txtSQL;
    // End of variables declaration//GEN-END:variables

}
