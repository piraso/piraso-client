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
package ard.piraso.ui.api.views;

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.EntryTabView;
import ard.piraso.ui.api.extension.AbstractEntryViewTopComponent;
import ard.piraso.ui.api.manager.EntryTabViewProviderManager;
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
                if(components.size() > 1) {
                    toolbar.removeAll();
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
                    toolbar2.removeAll();

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
        toolbar = new javax.swing.JToolBar();
        toolbar2 = new javax.swing.JToolBar();

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
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

                tabView.getComponent().addToolbarComponents(selectedToolbar);
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
