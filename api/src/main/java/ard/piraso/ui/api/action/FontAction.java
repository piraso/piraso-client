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

package ard.piraso.ui.api.action;

import ard.piraso.ui.api.GeneralSettingsModel;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category = "View", id = "ard.piraso.ui.base.action.FontAction")
@ActionRegistration(displayName = "#CTL_FontAction")
@ActionReferences({
    @ActionReference(path = "Menu/View", position = 400)
})
@Messages("CTL_FontAction=Font")
public final class FontAction extends AbstractAction implements Presenter.Menu {
    
    public FontAction() {
        putValue(Action.SHORT_DESCRIPTION, "Font");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public JMenuItem getMenuPresenter() {
        JMenu menu = new JMenu("Font");
        
        JMenuItem bigger = new JMenuItem("Bigger");
        JMenuItem smaller = new JMenuItem("Smaller");
        
        bigger.addActionListener(new BiggerFontHandler());
        smaller.addActionListener(new SmallerFontHandler());
        
        menu.add(bigger);
        menu.add(smaller);

        return menu;
    }
    
    private class BiggerFontHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();

            model.biggerFontSize();
            SingleModelManagers.GENERAL_SETTINGS.save(model);
        }
    }
    
    private class SmallerFontHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();

            model.smallerFontSize();
            SingleModelManagers.GENERAL_SETTINGS.save(model);        }
    }
}
