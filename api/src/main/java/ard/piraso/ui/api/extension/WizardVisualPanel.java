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
package ard.piraso.ui.api.extension;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author adleon
 */
public abstract class WizardVisualPanel<M extends WizardModel> extends JPanel {
    
    private final DocumentListener DOCUMENT_CHANGE_LISTENER = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            fireChangeEvent();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fireChangeEvent();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            fireChangeEvent();
        }
    };
    
    private final ActionListener ACTION_CHANGE_LISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            fireChangeEvent();
        }
    };
    
    private final ItemListener ITEM_CHANGE_LISTENER = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            fireChangeEvent();
        }
    };
    
    private ChangeListener listener;
    
    protected void addChangeListener(JTextComponent component) {
        component.getDocument().addDocumentListener(DOCUMENT_CHANGE_LISTENER);
    }
    
    protected void addChangeListener(JComboBox component) {
        component.addActionListener(ACTION_CHANGE_LISTENER);
        component.addItemListener(ITEM_CHANGE_LISTENER);
        
        if(component.isEditable()) {
            Component editor = component.getEditor().getEditorComponent();

            if(JTextComponent.class.isInstance(editor)) {
                addChangeListener((JTextComponent) editor);
            }
        }
    }
    
    protected void addChangeListener(JRadioButton component) {
        component.addActionListener(ACTION_CHANGE_LISTENER);
    }

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }
    
    protected void fireChangeEvent() {
        listener.stateChanged(new ChangeEvent(this));
    }
    
    public void initChangeEvents() {
    }
    
    public abstract void read(M model);
    
    public abstract void store(M model);
    
    public abstract boolean isEntriesValid();
}
