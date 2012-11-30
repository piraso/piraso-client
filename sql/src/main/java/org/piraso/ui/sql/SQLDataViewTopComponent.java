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

package org.piraso.ui.sql;

import org.piraso.api.sql.SQLDataViewEntry;
import org.piraso.api.sql.SQLParameterUtils;
import org.piraso.ui.api.extension.AbstractEntryViewTopComponent;
import org.piraso.ui.api.manager.FontProviderManager;
import org.piraso.ui.api.util.ClipboardUtils;
import org.piraso.ui.api.util.JTableUtils;
import org.piraso.ui.api.util.NotificationUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.ErrorManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.piraso.ui.sql//SQLDataView//EN", autostore = false)
@ActionID(category = "Window", id = "org.piraso.ui.sql.SQLDataViewTopComponent")
@ActionReference(path = "Menu/Window", position = 335)
@TopComponent.Description(preferredID = "SQLDataViewTopComponent", iconBase= "org/piraso/ui/sql/icons/database.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SQLDataViewAction",preferredID = "SQLDataViewTopComponent")
public final class SQLDataViewTopComponent extends AbstractEntryViewTopComponent<SQLDataViewEntry> {
    public static final int TABLE_COLUMN_TOLERANCE_SIZE = 7;
    
    public static final int DEFAULT_DIVIDER_LOCATION = 300;

    protected int dividerLocation = DEFAULT_DIVIDER_LOCATION;

    public SQLDataViewTopComponent() {
        super(SQLDataViewEntry.class);
        initComponents();
        initSplitPane();
        setName(NbBundle.getMessage(SQLDataViewTopComponent.class, "CTL_SQLDataViewTopComponent"));
        setToolTipText(NbBundle.getMessage(SQLDataViewTopComponent.class, "HINT_SQLDataViewTopComponent"));
    }
    
    private void initSplitPane() {
        if(btnProperties.isSelected()) {
            remove(jScrollPane2);
            add(jSplitPane1, java.awt.BorderLayout.CENTER);
            jSplitPane1.setRightComponent(jScrollPane2);
            jSplitPane1.setLeftComponent(jScrollPane1);
            jSplitPane1.setDividerLocation(dividerLocation);
        } else {
            remove(jSplitPane1);
            add(jScrollPane2, java.awt.BorderLayout.CENTER);
        }        
    }
    
    private void initTables() {
        TableColumn method1Column = jTable2.getColumnModel().getColumn(0);
        TableColumn cColumn = jTable2.getColumnModel().getColumn(1);

        method1Column.setHeaderValue("Column Name/ID");
        method1Column.setPreferredWidth(150);
        method1Column.setMaxWidth(200);
        cColumn.setHeaderValue("Type");
        cColumn.setPreferredWidth(225);

        if(table1Model.getColumnCount() == 2) { // Column Name, Value pair
            TableColumn columnName = jTable1.getColumnModel().getColumn(0);
            TableColumn columnValue = jTable1.getColumnModel().getColumn(1);

            SQLDataTableCellRenderer renderer = new SQLDataTableCellRenderer();
            columnName.setPreferredWidth(200);
            columnName.setCellRenderer(renderer);
            columnValue.setPreferredWidth(800);
            columnValue.setCellRenderer(renderer);
        }
    }

    void writeProperties(java.util.Properties p) {
        if(btnProperties.isEnabled()) {
            dividerLocation = jSplitPane1.getDividerLocation();
        }

        p.setProperty("properties", String.valueOf(btnProperties.isSelected()));
        p.setProperty("dividerLocation", String.valueOf(dividerLocation));
    }

    void readProperties(java.util.Properties p) {
        btnProperties.setSelected(Boolean.parseBoolean(p.getProperty("properties", "true")));

        dividerLocation = Integer.parseInt(p.getProperty("dividerLocation", String.valueOf(DEFAULT_DIVIDER_LOCATION)));
        if(btnProperties.isEnabled()) {
            btnPropertiesActionPerformed(null);
        }

    }

    @Override
    protected void refreshView() {
        jTable1.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jTable2.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        btnCopy.setEnabled(currentEntry != null);

        table2Model.setRowCount(0);
        table1Model.setRowCount(0);
        if(currentEntry != null) {
            table2Model = new DefaultTableModel(
                    SQLParameterUtils.createColumnDefinition(currentEntry),
                    new Vector<String>(Arrays.asList("", ""))
            );            
            table1Model = new DefaultTableModel(
                    SQLParameterUtils.createDataValues(currentEntry, TABLE_COLUMN_TOLERANCE_SIZE),
                    SQLParameterUtils.createHeaders(currentEntry, TABLE_COLUMN_TOLERANCE_SIZE)
            );

            jTable2.setModel(table2Model);
            jTable1.setModel(table1Model);

            initTables();
            JTableUtils.scrollToFirstRow(jTable1, jTable2);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnProperties = new javax.swing.JToggleButton();
        btnCopy = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable2.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jTable2.setModel(table2Model);
        jScrollPane1.setViewportView(jTable2);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jTable1.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        jTable1.setModel(table1Model);
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setBackground(new java.awt.Color(226, 226, 226));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnProperties.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/sql/icons/properties.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnProperties, org.openide.util.NbBundle.getMessage(SQLDataViewTopComponent.class, "SQLDataViewTopComponent.btnProperties.text")); // NOI18N
        btnProperties.setToolTipText(org.openide.util.NbBundle.getMessage(SQLDataViewTopComponent.class, "SQLDataViewTopComponent.btnProperties.toolTipText")); // NOI18N
        btnProperties.setFocusable(false);
        btnProperties.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProperties.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropertiesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProperties);

        btnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/sql/icons/copy.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCopy, org.openide.util.NbBundle.getMessage(SQLDataViewTopComponent.class, "SQLDataViewTopComponent.btnCopy.text")); // NOI18N
        btnCopy.setToolTipText(org.openide.util.NbBundle.getMessage(SQLDataViewTopComponent.class, "SQLDataViewTopComponent.btnCopy.toolTipText")); // NOI18N
        btnCopy.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        btnCopy.setEnabled(false);
        btnCopy.setFocusable(false);
        btnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCopy);

        add(jToolBar1, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        if(currentEntry != null) {
            try {
                ClipboardUtils.copy(currentEntry.getCSVString());
                NotificationUtils.info("SQL Data CSV is now copied to clipboard.");
            } catch (IOException e) {
                ErrorManager.getDefault().notify(e);
            }
        }
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropertiesActionPerformed
        initSplitPane();        
        repaint();
        revalidate();
    }//GEN-LAST:event_btnPropertiesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JToggleButton btnProperties;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private DefaultTableModel table1Model = new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    );
    private javax.swing.JTable jTable2;
    private DefaultTableModel table2Model = new DefaultTableModel(0, 2);
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
