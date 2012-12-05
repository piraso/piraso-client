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

import org.piraso.api.entry.Entry;
import org.piraso.api.entry.RequestEntry;
import org.piraso.ui.api.manager.FontProviderManager;
import org.piraso.ui.api.manager.ModelEvent;
import org.piraso.ui.api.manager.ModelOnChangeListener;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.piraso.ui.api.util.JTableUtils;
import org.piraso.ui.api.util.SingleClassInstanceContent;
import org.piraso.ui.api.util.WindowUtils;
import org.piraso.ui.base.manager.EntryViewProviderManager;
import org.piraso.ui.base.manager.MessageProviderManager;
import org.piraso.ui.base.manager.PreferenceProviderManager;
import org.piraso.ui.base.model.IOEntryComboBoxModel;
import org.piraso.ui.base.model.IOEntryTableModel;
import org.piraso.io.IOEntryEvent;
import org.piraso.io.IOEntryListener;
import org.piraso.io.IOEntryReader;
import org.apache.commons.lang.StringUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Top component which displays something.
 */
public final class ContextMonitorTopComponent extends TopComponent implements ListSelectionListener, ContextMonitorDelegate {
    public static final String INACTIVE_ICON_PATH = "org/piraso/ui/base/icons/status-inactive.png";

    public static final String STOPPED_ICON_PATH = "org/piraso/ui/base/icons/status-stopped.png";

    public static final String STARTED_ICON_PATH = "org/piraso/ui/base/icons/status-active.png";

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
            actionProvider.setSavable(true);
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

    private Searcher searcher;

    public ContextMonitorTopComponent(IOEntryReader reader, String name) {
        setName(name);
        setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "HINT_ContextMonitorTopComponent"));
        setIcon(ImageUtilities.loadImage(INACTIVE_ICON_PATH, true));

        InstanceContent content = new InstanceContent();
        associateLookup(new AbstractLookup(content));

        this.reader = reader;
        this.entryContent = new SingleClassInstanceContent<Entry>(content);
        this.thisContent = new SingleClassInstanceContent<ContextMonitorDelegate>(content);
        this.actionProvider = new IOEntryReaderActionProvider(this, reader, content);
        this.searcher = new Searcher();

        initReader();
        iniTreeRequest();
        initComponents();
        initTable();
        initComboBox();
        refreshUIStates();
        initKeyboardActions();
    }

    public ContextMonitorTopComponent() {
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
        btnAutoScroll.setSelected(true);

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
                searcher.reset();
                btnSearch.setSelected(!btnSearch.isSelected());
                btnSearchActionPerformed(e);
            }
        };

        Action nextAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNextActionPerformed(e);
            }
        };

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_MASK);
        registerKeyboardAction(findAction, stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        txtSearch.registerKeyboardAction(findAction, stroke, JComponent.WHEN_FOCUSED);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        txtSearch.registerKeyboardAction(nextAction, stroke, JComponent.WHEN_FOCUSED);
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

                        if (tableModel.getRowCount() > 0) {
                            Entry firstEntry = tableModel.getEntryAt(0).getEntry();
                            if (selectedEntry.getBaseRequestId().equals(firstEntry.getBaseRequestId())) {
                                JTableUtils.scrollTo(table, 0);
                                table.getSelectionModel().setSelectionInterval(0, 0);
                            } else {
                                comboBoxModel.setSelectedItem(selectedEntry);

                                for (int i = 0; i < tableModel.getRowCount(); i++) {
                                    Entry entry = tableModel.getEntryAt(i).getEntry();

                                    if (entry.getBaseRequestId().equals(selectedEntry.getBaseRequestId())) {
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
            btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/arrow-switch-270.png")));
            btnAutoScroll.setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.toolTipText"));            
        } else {
            btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/arrow-switch-minus-270.png")));
            btnAutoScroll.setToolTipText(NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnAutoScroll.unselected.toolTipText"));
        }

        cboUrl.setEnabled(!btnAutoScroll.isSelected());
        btnClear.setEnabled(reader.isRestartable());
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
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        chkMatchCase = new javax.swing.JCheckBox();
        chkWholeWord = new javax.swing.JCheckBox();
        chkRegex = new javax.swing.JCheckBox();
        btnCloseSearchBar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        toolbar.setBackground(new java.awt.Color(226, 226, 226));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.add(jSeparator2);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/find.png"))); // NOI18N
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

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/edit_clear.png"))); // NOI18N
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

        btnAutoScroll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/arrow-switch-270.png"))); // NOI18N
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

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/previous.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnPrevious, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnPrevious.text")); // NOI18N
        btnPrevious.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnPrevious.toolTipText")); // NOI18N
        btnPrevious.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        btnPrevious.setFocusable(false);
        btnPrevious.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrevious.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        searchBar.add(btnPrevious);

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/next.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnNext, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnNext.text")); // NOI18N
        btnNext.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnNext.toolTipText")); // NOI18N
        btnNext.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
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

        chkMatchCase.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(chkMatchCase, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.chkMatchCase.text")); // NOI18N
        chkMatchCase.setContentAreaFilled(false);
        chkMatchCase.setFocusable(false);
        chkMatchCase.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(chkMatchCase);

        chkWholeWord.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(chkWholeWord, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.chkWholeWord.text")); // NOI18N
        chkWholeWord.setContentAreaFilled(false);
        chkWholeWord.setFocusable(false);
        chkWholeWord.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(chkWholeWord);

        chkRegex.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(chkRegex, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.chkRegex.text")); // NOI18N
        chkRegex.setContentAreaFilled(false);
        chkRegex.setFocusable(false);
        chkRegex.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        searchBar.add(chkRegex);

        btnCloseSearchBar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/base/icons/close.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnCloseSearchBar, org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnCloseSearchBar.text")); // NOI18N
        btnCloseSearchBar.setToolTipText(org.openide.util.NbBundle.getMessage(ContextMonitorTopComponent.class, "ContextMonitorTopComponent.btnCloseSearchBar.toolTipText")); // NOI18N
        btnCloseSearchBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 2, 7, 2));
        btnCloseSearchBar.setFocusable(false);
        btnCloseSearchBar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnCloseSearchBar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCloseSearchBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCloseSearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSearchBarActionPerformed(evt);
            }
        });
        searchBar.add(Box.createHorizontalGlue());
        searchBar.add(btnCloseSearchBar);

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

    private void btnCloseSearchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSearchBarActionPerformed
        searchBar.setVisible(false);
        btnSearch.setSelected(false);
    }//GEN-LAST:event_btnCloseSearchBarActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                searcher.previous();
            }
        });
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                searcher.next();
            }
        });
    }//GEN-LAST:event_btnNextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JToggleButton btnAutoScroll;
    protected javax.swing.JButton btnClear;
    protected javax.swing.JButton btnCloseSearchBar;
    protected javax.swing.JButton btnNext;
    protected javax.swing.JButton btnPrevious;
    protected javax.swing.JToggleButton btnSearch;
    protected javax.swing.JComboBox cboUrl;
    protected IOEntryComboBoxModel comboBoxModel;
    protected javax.swing.JCheckBox chkMatchCase;
    protected javax.swing.JCheckBox chkRegex;
    protected javax.swing.JCheckBox chkWholeWord;
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

    public class Searcher {
        int index = 0;

        public void reset() {
            index = table.getSelectedRow();

            if(index < 0 || table.getSelectionModel().isSelectionEmpty() || 
                    index >= tableModel.getRowCount()) {
                index = 0;
            }
        }

        protected Pattern createPattern(String pattern) {
            if(chkMatchCase.isSelected()) {
                return Pattern.compile(pattern);
            } else {
                return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            }
        }

        public boolean isMatch(Entry entry) {
            String group = MessageProviderManager.INSTANCE.getGroupMessage(entry);
            String message = MessageProviderManager.INSTANCE.getMessage(entry);
            String shortName = PreferenceProviderManager.INSTANCE.getShortName(entry);

            String text = txtSearch.getText();

            Pattern pattern = null;

            if(chkWholeWord.isSelected()) {
                try {
                    text = "\\b"  + text +  "\\b";
                    pattern = createPattern(text);
                } catch (PatternSyntaxException e) {
                    pattern = null;
                }
            }

            if(chkRegex.isSelected()) {
                try {
                    pattern = createPattern(text);
                } catch(PatternSyntaxException e) {
                    pattern = null;
                }
            }

            if(pattern == null) {
                if(chkMatchCase.isSelected()) {
                    return StringUtils.contains(group, text) || StringUtils.contains(message, text) || StringUtils.contains(shortName, text);
                } else {
                    return StringUtils.containsIgnoreCase(group, text) || StringUtils.containsIgnoreCase(message, text) || StringUtils.containsIgnoreCase(shortName, text);
                }
            }

            String searchString = String.format("%s %s %s", group, message, shortName);
            Matcher matcher = pattern.matcher(searchString);
            return matcher.find();
        }

        private boolean select(int i) {
            Entry entry = tableModel.getEntryAt(i).getEntry();

            if(isMatch(entry)) {
                table.getSelectionModel().setSelectionInterval(i, i);
                JTableUtils.scrollTo(table, i);
                index = i;

                return true;
            }

            return false;
        }

        public int computePreviousIndex(int i) {
            return i - 1 >= 0 ? i - 1 : 0;
        }

        public int computeNextIndex(int i) {
            return i + 1 < tableModel.getRowCount() ? i + 1 : 0;
        }

        public void previous() {
            int startIndex = computePreviousIndex(index);
            for(int i = startIndex; i >= 0; i--) {
                if(select(i)) {
                    return;
                }
            }

            for(int i = tableModel.getRowCount() - 1; i >= startIndex; i--) {
                if(select(i)) {
                    return;
                }
            }
        }

        public void next() {
            int startIndex = computeNextIndex(index);
            for(int i = startIndex; i < tableModel.getRowCount(); i++) {
                if(select(i)) {
                    return;
                }
            }

            for(int i = 0; i < startIndex; i++) {
                if(select(i)) {
                    return;
                }
            }
        }
    }
}
