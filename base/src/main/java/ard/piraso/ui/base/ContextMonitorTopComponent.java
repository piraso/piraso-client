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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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

        initReader();
        iniTreeRequest();
        initComponents();
        initTable();
        initComboBox();
        refreshUIStates();
        initKeyboardActions();
    }

    public void reset() {
        boolean ensureStarted = reader.isAlive();
        if(ensureStarted) {
            actionProvider.getStopCookie().stop();
        }

        if(treeHandler != null) {
            treeHandler.reset();
            reader.removeListener(treeHandler);
        }

        reader.removeListener(ICON_CHANGER);

        this.reader = reader.createNew();
        this.actionProvider.setReader(reader);
        initReader();

        reader.addListener(treeHandler);
        table.setModel(tableModel);
        cboUrl.setModel(comboBoxModel);

        initTable();
        refreshUIStates();

        if(ensureStarted) {
            actionProvider.getStartCookie().start();
        }
    }

    public void initReader() {
        this.tableModel = new IOEntryTableModel(reader);
        this.comboBoxModel = tableModel.getComboBoxModel();

        reader.addListener(ICON_CHANGER);
    }

    private void initKeyboardActions() {
        Action findAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSearch.setSelected(!btnSearch.isSelected());
                btnSearchActionPerformed(e);
            }
        };

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_MASK);
        registerKeyboardAction(findAction, stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        txtSearch.registerKeyboardAction(findAction, stroke, JComponent.WHEN_FOCUSED);
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
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        RequestEntry selectedEntry = (RequestEntry) comboBoxModel.getSelectedItem();
                        tableModel.setCurrentRequestId(selectedEntry.getRequestId());

                        if(tableModel.getRowCount() > 0) {
                            Entry firstEntry = tableModel.getEntryAt(0).getEntry();
                            if(selectedEntry.getBaseRequestId().equals(firstEntry.getBaseRequestId())) {
                                JTableUtils.scrollTo(table, 0);
                                table.getSelectionModel().setSelectionInterval(0, 0);
                            } else {
                                comboBoxModel.setSelectedItem(selectedEntry);

                                for(int i = 0; i < tableModel.getRowCount(); i++) {
                                    Entry entry = tableModel.getEntryAt(i).getEntry();

                                    if(entry.getBaseRequestId().equals(selectedEntry.getBaseRequestId())) {
                                        table.getSelectionModel().setSelectionInterval(i, i);
                                        JTableUtils.scrollTo(table, i);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
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
        
        RequestEntry selectedRequest = (RequestEntry) comboBoxModel.getSelectedItem();
        if(request.getBaseRequestId().equals(selectedRequest.getBaseRequestId())) {
            JTableUtils.scrollTo(table, 0);

            if(table.getRowCount() > 0) {
                table.getSelectionModel().setSelectionInterval(0, 0);
            }
        } else {
            comboBoxModel.setSelectedItem(request);

            for(int i = 0; i < tableModel.getRowCount(); i++) {
                Entry entry = tableModel.getEntryAt(i).getEntry();
                
                if(entry.getBaseRequestId().equals(request.getBaseRequestId())) {
                    table.getSelectionModel().setSelectionInterval(i, i);
                    JTableUtils.scrollTo(table, i);
                    break;
                }
            }
        }
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
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnSearch = new javax.swing.JToggleButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAutoScroll = new javax.swing.JToggleButton();
        cboUrl = new javax.swing.JComboBox();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        searchBar = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnPrevious = new javax.swing.JToggleButton();
        btnNext = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        btnTarget = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        toolbar.setBackground(new java.awt.Color(226, 226, 226));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(jSeparator2);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/find.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnSearch, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnSearch.text")); // NOI18N
        btnSearch.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnSearch.toolTipText")); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        toolbar.add(btnSearch);

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/edit_clear.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnClear, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnClear.text")); // NOI18N
        btnClear.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnClear.toolTipText")); // NOI18N
        btnClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        toolbar.add(btnClear);
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

        searchBar.setBackground(new java.awt.Color(226, 226, 226));
        searchBar.setFloatable(false);
        searchBar.setRollover(true);
        searchBar.setVisible(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.jLabel1.text")); // NOI18N
        searchBar.add(jLabel1);

        txtSearch.setText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.txtSearch.text")); // NOI18N
        txtSearch.setMaximumSize(new java.awt.Dimension(200, 2147483647));
        txtSearch.setPreferredSize(new java.awt.Dimension(200, 28));
        searchBar.add(txtSearch);
        searchBar.add(jSeparator3);

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/previous.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnPrevious, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnPrevious.text")); // NOI18N
        btnPrevious.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnPrevious.toolTipText")); // NOI18N
        btnPrevious.setFocusable(false);
        btnPrevious.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrevious.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        searchBar.add(btnPrevious);

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/next.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnNext, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnNext.text")); // NOI18N
        btnNext.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnNext.toolTipText")); // NOI18N
        btnNext.setFocusable(false);
        btnNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNext.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        searchBar.add(btnNext);
        searchBar.add(jSeparator5);

        jCheckBox1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.jCheckBox1.text")); // NOI18N
        jCheckBox1.setContentAreaFilled(false);
        jCheckBox1.setFocusable(false);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(jCheckBox1);

        jCheckBox2.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.jCheckBox2.text")); // NOI18N
        jCheckBox2.setContentAreaFilled(false);
        jCheckBox2.setFocusable(false);
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.jCheckBox3.text")); // NOI18N
        jCheckBox3.setContentAreaFilled(false);
        jCheckBox3.setFocusable(false);
        jCheckBox3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(jCheckBox3);

        btnTarget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ard/piraso/ui/base/icons/close.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnTarget, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnTarget.text")); // NOI18N
        btnTarget.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnTarget.toolTipText")); // NOI18N
        btnTarget.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 2, 7, 2));
        btnTarget.setFocusable(false);
        btnTarget.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnTarget.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTarget.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTargetActionPerformed(evt);
            }
        });
        searchBar.add(Box.createHorizontalGlue());
        searchBar.add(btnTarget);

        add(searchBar, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAutoScrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoScrollActionPerformed
        refreshUIStates();
    }//GEN-LAST:event_btnAutoScrollActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                reset();
                table.invalidate();
                table.repaint();
            }
        });
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchBar.setVisible(btnSearch.isSelected());
        txtSearch.requestFocus();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTargetActionPerformed
        searchBar.setVisible(false);
        btnSearch.setSelected(false);
    }//GEN-LAST:event_btnTargetActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JToggleButton btnAutoScroll;
    protected javax.swing.JButton btnClear;
    protected javax.swing.JToggleButton btnNext;
    protected javax.swing.JToggleButton btnPrevious;
    protected javax.swing.JToggleButton btnSearch;
    protected javax.swing.JButton btnTarget;
    protected javax.swing.JComboBox cboUrl;
    protected IOEntryComboBoxModel comboBoxModel;
    protected javax.swing.JCheckBox jCheckBox1;
    protected javax.swing.JCheckBox jCheckBox2;
    protected javax.swing.JCheckBox jCheckBox3;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JToolBar.Separator jSeparator1;
    protected javax.swing.JToolBar.Separator jSeparator2;
    protected javax.swing.JToolBar.Separator jSeparator3;
    protected javax.swing.JToolBar.Separator jSeparator5;
    protected javax.swing.JToolBar searchBar;
    protected javax.swing.JTable table;
    protected IOEntryTableModel tableModel;
    protected javax.swing.JScrollPane tableScrollPane;
    protected javax.swing.JToolBar toolbar;
    protected javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
