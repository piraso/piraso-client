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

package org.piraso.ui.api.views;

import org.piraso.api.entry.Entry;
import org.piraso.ui.api.EntryTabView;
import org.piraso.ui.api.extension.AbstractEntryViewTopComponent;
import org.piraso.ui.api.manager.EntryTabViewProviderManager;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Top component which displays something.
 */
public abstract class BaseEntryViewTopComponent<T extends Entry> extends AbstractEntryViewTopComponent<T> {

    private String shortName;
    
    private List<EntryTabView> components;
    
    private JToggleButton[] buttons;

    private boolean enableFilter = false;
    
    private EntryTabView selectedTabView;
    
    private String selectedTabTitle = null;
    
    private Component glue = Box.createHorizontalGlue();
        
    protected BaseEntryViewTopComponent(Class<T> typeClass, String shortName) {
        super(typeClass);
        
        this.shortName = shortName;
        initComponents();
    }

    public void setEnableFilter(boolean enableFilter) {
        this.enableFilter = enableFilter;
    }

    public void clear() {
        if(CollectionUtils.isNotEmpty(components)) {
            for (EntryTabView view : components) {
                view.getComponent().removeToolbarComponents(toolbar);
                toolbar.remove(glue);
                toolbar.remove(btnPin);
                remove(view.getComponent());
            }
        }        
    }
    
    @Override
    protected void refreshView() {
        synchronized (getTreeLock()) {
            clear();
            components = new ArrayList<EntryTabView>();

            if(currentEntry != null) {
                components.add(new EntryTabView(new BaseMessageView(currentEntry)));
                components.addAll(EntryTabViewProviderManager.INSTANCE.getTabView(BaseEntryViewTopComponent.class, currentEntry));
            }

            if(CollectionUtils.isNotEmpty(components)) {
                toolbar2.removeAll();
                toolbar.removeAll();

                if(components.size() > 1) {
                    buttons = new JToggleButton[components.size()];

                    ActionListener initialViewAction = null;
                    for(int i = 0; i < components.size(); i++) {
                        EntryTabView view = components.get(i);
                        buttons[i] = new JToggleButton(view.getTitle());

                        buttonGroup1.add(buttons[i]);

                        ActionListener buttonListener = new SwitchEntryView(view, toolbar);
                        buttons[i].addActionListener(buttonListener);

                        if(initialViewAction == null) {
                            initialViewAction = buttonListener;
                            buttons[i].setSelected(true);
                        } else if(selectedTabTitle != null && selectedTabTitle.equals(view.getTitle())) {
                            initialViewAction = buttonListener;
                            buttons[i].setSelected(true);
                        }

                        toolbar.add(buttons[i]);
                    }

                    toolbar.add(jSeparator1);

                    // fire initial action
                    if(initialViewAction != null) {
                        initialViewAction.actionPerformed(new ActionEvent(this, 1, "initial"));
                    }
                    toolbar.setVisible(true);
                    toolbar2.setVisible(false);
                } else {
                    SwitchEntryView action = new SwitchEntryView(components.iterator().next(), toolbar2);
                    action.actionPerformed(null);

                    toolbar2.setVisible(true);
                    toolbar.setVisible(false);
                }
            }
        }
    }
    
    
    protected abstract void populateMessage(JTextPane txtMessage, T entry) throws Exception;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JToolBar.Separator();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btnPin = new javax.swing.JToggleButton();
        toolbar = new javax.swing.JToolBar();
        toolbar2 = new javax.swing.JToolBar();

        btnPin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/piraso/ui/api/icons/pin_small.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnPin, org.openide.util.NbBundle.getMessage(BaseEntryViewTopComponent.class, "BaseEntryViewTopComponent.btnPin.text")); // NOI18N
        btnPin.setToolTipText(org.openide.util.NbBundle.getMessage(BaseEntryViewTopComponent.class, "BaseEntryViewTopComponent.btnPin.toolTipText")); // NOI18N
        btnPin.setFocusable(false);
        btnPin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPinActionPerformed(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        toolbar.setBackground(new java.awt.Color(226, 226, 226));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        add(toolbar, java.awt.BorderLayout.NORTH);

        toolbar2.setBackground(new java.awt.Color(226, 226, 226));
        toolbar2.setFloatable(false);
        toolbar2.setOrientation(1);
        toolbar2.setRollover(true);
        toolbar2.setVisible(false);
        add(toolbar2, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPinActionPerformed
        if(btnPin.isSelected()) {
            selectedTabTitle = selectedTabView.getTitle();
        } else {
            selectedTabTitle = null;
        }
    }//GEN-LAST:event_btnPinActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JToggleButton btnPin;
    protected javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JToolBar.Separator jSeparator1;
    protected javax.swing.JToolBar toolbar;
    protected javax.swing.JToolBar toolbar2;
    // End of variables declaration//GEN-END:variables
    
    private class SwitchEntryView implements ActionListener {
        
        private EntryTabView tabView;

        private JToolBar selectedToolbar;

        private SwitchEntryView(EntryTabView tabView, JToolBar selectedToolbar) {
            this.tabView = tabView;
            this.selectedToolbar = selectedToolbar;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            synchronized (getTreeLock()) {
                clear();
                selectedTabView = tabView;
                btnPin.setSelected(selectedTabTitle != null && selectedTabTitle.equals(tabView.getTitle()));                
                tabView.getComponent().addToolbarComponents(selectedToolbar);                
                
                if(selectedToolbar == toolbar) {
                    selectedToolbar.add(glue);
                    selectedToolbar.add(btnPin);
                }
                
                add(tabView.getComponent(), BorderLayout.CENTER);

                repaint();
                revalidate();
            }
        }
    }
    
    private class BaseMessageView extends FilteredJTextPaneTabView<T> {
        
        public BaseMessageView(T entry) {
            super(shortName, entry, String.format("%s is now copied to clipboard.", shortName));
            btnFilter.setVisible(enableFilter);
        }

        @Override
        protected void populateMessage(T entry) throws Exception {
            BaseEntryViewTopComponent.this.populateMessage(txtEditor, entry);
        }
    }
}
