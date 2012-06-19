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

package ard.piraso.ui.base;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.RequestEntry;
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.manager.ModelEvent;
import ard.piraso.ui.api.manager.ModelOnChangeListener;
import ard.piraso.ui.api.manager.SingleModelManagers;
import ard.piraso.ui.api.util.JTableUtils;
import ard.piraso.ui.api.util.SingleClassInstanceContent;
import ard.piraso.ui.api.util.WindowUtils;
import ard.piraso.ui.base.manager.EntryViewProviderManager;
import ard.piraso.ui.base.model.IOEntryComboBoxModel;
import ard.piraso.ui.base.model.IOEntryTableModel;
import ard.piraso.ui.io.IOEntryEvent;
import ard.piraso.ui.io.IOEntryListener;
import ard.piraso.ui.io.IOEntryReader;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Top component which displays something.
 */
public final class ContextMonitorTopComponent extends TopComponent implements ListSelectionListener, ContextMonitorDelegate {
    public static final String INACTIVE_ICON_PATH = "ard/piraso/ui/base/icons/status-inactive.png";

    public static final String STOPPED_ICON_PATH = "ard/piraso/ui/base/icons/status-stopped.png";

    public static final String STARTED_ICON_PATH = "ard/piraso/ui/base/icons/status-active.png";

    private final ModelOnChangeListener GENERAL_SETTINGS_LISTENER = new ModelOnChangeListener() {
        @Override
        public void onChange(ModelEvent evt) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(fontSize != FontProviderManager.INSTANCE.getEditorDefaultFont().getSize()) {
                        table.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
                        fontSize = table.getFont().getSize();
                    }

                    if(showElapseTime != SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime() ||
                            showType != SingleModelManagers.GENERAL_SETTINGS.get().isShowType()) {
                        tableModel.fireTableStructureChanged();
                        initColumns();
                    }

                    table.invalidate();
                    table.repaint();
                }
            });
        }
    };

    private final IOEntryListener ICON_CHANGER = new IOEntryListener() {
        @Override
        public void started(IOEntryEvent evt) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    setIcon(ImageUtilities.loadImage(STARTED_ICON_PATH, true));
                }
            });
        }
        @Override
        public void stopped(IOEntryEvent evt) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(isOpened()) {
                        setIcon(ImageUtilities.loadImage(STOPPED_ICON_PATH, true));
                    }
                }
            });
        }

        @Override
        public void receivedEntry(IOEntryEvent evt) {
        }
    };

    private IOEntryReaderActionProvider actionProvider;

    private SingleClassInstanceContent<Entry> entryContent;

    private SingleClassInstanceContent<ContextMonitorDelegate> thisContent;

    private final ContextMonitorTableCellRenderer CELL_RENDERER = new ContextMonitorTableCellRenderer();

    private boolean showElapseTime;

    private boolean showType;

    private int fontSize = FontProviderManager.INSTANCE.getEditorDefaultFont().getSize();

    private RequestTreeTopComponent.ContextMonitorHandler treeHandler;

    private IOEntryReader reader;

    public ContextMonitorTopComponent(IOEntryReader reader, String name) {
        setName(name);
        setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "HINT_ContextMonitorTopComponent"));
        setIcon(ImageUtilities.loadImage(INACTIVE_ICON_PATH, true));

        InstanceContent content = new InstanceContent();
        associateLookup(new AbstractLookup(content));

        this.reader = reader;
        this.entryContent = new SingleClassInstanceContent<Entry>(content);
        this.thisContent = new SingleClassInstanceContent<ContextMonitorDelegate>(content);
        this.actionProvider = new IOEntryReaderActionProvider(reader, content);
        this.tableModel = new IOEntryTableModel(reader);
        this.comboBoxModel = tableModel.getComboBoxModel();

        // added icon listeners
        reader.addListener(ICON_CHANGER);
        iniTreeRequest();
        
        initComponents();
        initTable();
        initComboBox();
        refreshUIStates();
    }

    public void iniTreeRequest() {
        RequestTreeTopComponent component = RequestTreeTopComponent.get();
        if(component != null && treeHandler == null) {
            treeHandler = component.createHandler(this);
            reader.addListener(treeHandler);
        }
    }

    private void initComboBox() {
        cboUrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestEntry entry = (RequestEntry) comboBoxModel.getSelectedItem();
                tableModel.setCurrentRequestId(entry.getRequestId());
            }
        });
    }
    
    @Override
    public RequestEntry getSelectedRequest() {
        return (RequestEntry) comboBoxModel.getSelectedItem();
    }

    @Override
    public void selectRequest(RequestEntry request) {
        requestVisible();
        btnAutoScroll.setSelected(false);
        refreshUIStates();
        tableModel.setCurrentRequestId(request.getRequestId());
        JTableUtils.scrollTo(table, 0);
    }

    private void initColumns() {
        TableColumn numColumn = table.getColumnModel().getColumn(0);
        TableColumn messageColumn;
        TableColumn elapseColumn;

        numColumn.setHeaderValue("");
        numColumn.setMaxWidth(43);
        numColumn.setCellRenderer(CELL_RENDERER);

        showType = SingleModelManagers.GENERAL_SETTINGS.get().isShowType();
        if(showType) {
            TableColumn typeColumn = table.getColumnModel().getColumn(1);
            messageColumn = table.getColumnModel().getColumn(2);

            typeColumn.setHeaderValue("Type");
            typeColumn.setMaxWidth(125);
            typeColumn.setCellRenderer(CELL_RENDERER);
        } else {
            messageColumn = table.getColumnModel().getColumn(1);
        }

        showElapseTime = SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime();
        if(showElapseTime) {
            if(showType) {
                elapseColumn = table.getColumnModel().getColumn(3);
            } else {
                elapseColumn = table.getColumnModel().getColumn(2);
            }

            elapseColumn.setHeaderValue("Elapse");
            elapseColumn.setMaxWidth(100);
            elapseColumn.setCellRenderer(CELL_RENDERER);
        }

        messageColumn.setHeaderValue("Message");
        messageColumn.setPreferredWidth(700);
        messageColumn.setCellRenderer(CELL_RENDERER);
    }

    private void initTable() {
        initColumns();

        table.setAutoscrolls(true);
        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getSelectionModel().addListSelectionListener(this);
        tableModel.setOwningTable(table);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        if(e.getSource() != table.getSelectionModel()) return;
        if(table.getSelectedRow() < 0) return;

        Entry entry = tableModel.getEntryAt(table.getSelectedRow()).getEntry();
        Class<? extends TopComponent> viewClass = EntryViewProviderManager.INSTANCE.getViewClass(entry);

        entryContent.add(entry);

        if(viewClass != null) {
            WindowUtils.selectWindow(viewClass);
        }
    }

    private void refreshUIStates() {
        if(btnAutoScroll.isSelected()) {
            btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/arrow-switch-270.png")));
            btnAutoScroll.setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.toolTipText"));            
        } else {
            btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/arrow-switch-minus-270.png")));
            btnAutoScroll.setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.unselected.toolTipText"));
        }

        cboUrl.setEnabled(!btnAutoScroll.isSelected());
        tableModel.setAllowScrolling(btnAutoScroll.isSelected());
    }

    @Override
    protected void componentActivated() {
        thisContent.add(this);
    }

    @Override
    public void componentClosed() {
        actionProvider.getStopCookie().stop();
        entryContent.clear();
        if(treeHandler != null) {
            treeHandler.close();
            reader.removeListener(treeHandler);
        }
        SingleModelManagers.GENERAL_SETTINGS.removeModelOnChangeListener(GENERAL_SETTINGS_LISTENER);
    }

    @Override
    protected void componentOpened() {
        actionProvider.getStartCookie().start();
        SingleModelManagers.GENERAL_SETTINGS.addModelOnChangeListener(GENERAL_SETTINGS_LISTENER);
    }
    
    @Override
    public int getPersistenceType() {
        return PERSISTENCE_NEVER;
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
        btnAutoScroll = new javax.swing.JToggleButton();
        cboUrl = new javax.swing.JComboBox();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        toolbar.setBackground(new java.awt.Color(226, 226, 226));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(jSeparator1);

        btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/arrow-switch-270.png"))); // NOI18N
        btnAutoScroll.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(btnAutoScroll, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.text")); // NOI18N
        btnAutoScroll.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.toolTipText")); // NOI18N
        btnAutoScroll.setFocusable(false);
        btnAutoScroll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAutoScroll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAutoScroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoScrollActionPerformed(evt);
            }
        });
        toolbar.add(btnAutoScroll);

        cboUrl.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        cboUrl.setModel(comboBoxModel);
        toolbar.add(cboUrl);

        add(toolbar, java.awt.BorderLayout.NORTH);

        tableScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableScrollPane.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N

        table.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
        table.setModel(tableModel);
        table.setGridColor(new java.awt.Color(204, 0, 0));
        table.setRowMargin(0);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        tableScrollPane.setViewportView(table);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAutoScrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoScrollActionPerformed
        refreshUIStates();
    }//GEN-LAST:event_btnAutoScrollActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JToggleButton btnAutoScroll;
    protected javax.swing.JComboBox cboUrl;
    protected IOEntryComboBoxModel comboBoxModel;
    protected javax.swing.JToolBar.Separator jSeparator1;
    protected javax.swing.JTable table;
    protected IOEntryTableModel tableModel;
    protected javax.swing.JScrollPane tableScrollPane;
    protected javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

}
