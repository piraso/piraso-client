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

package ard.piraso.ui.api.extension;

import org.openide.windows.WindowManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Base class for dialog
 */
public abstract class AbstractDialog extends JDialog {

    protected final ActionListener disposeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
    };
    
    protected final DocumentListener REFRESH_BUTTON_DOCUMENT_LISTENER = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            refreshButtons();
        }
        @Override
        public void removeUpdate(DocumentEvent de) {
            refreshButtons();
        }
        @Override
        public void changedUpdate(DocumentEvent de) {
            refreshButtons();
        }
    };
    
    protected final ItemListener REFRESH_BUTTON_ITEM_LISTENER = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            refreshButtons();
        }
    };
    
    protected final KeyListener REFRESH_BUTTON_KEY_LISTENER = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            refreshButtons();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            refreshButtons();
        }
        @Override
        public void keyReleased(KeyEvent e) {
            refreshButtons();
        }
    };

    protected final ListSelectionListener REFRESH_BUTTON_LIST_SELECTION_LISTENER = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            refreshButtons();
        }
    };

    protected AbstractDialog() {
        super(WindowManager.getDefault().getMainWindow(), true);
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().registerKeyboardAction(disposeActionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    protected void refreshButtons() {
    }
}
