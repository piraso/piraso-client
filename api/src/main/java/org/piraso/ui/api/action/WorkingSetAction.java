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

package org.piraso.ui.api.action;

import org.piraso.ui.api.GeneralSettingsModel;
import org.piraso.ui.api.WorkingSetSettings;
import org.piraso.ui.api.manager.ModelEvent;
import org.piraso.ui.api.manager.ModelOnChangeListener;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category = "View", id = "org.piraso.ui.base.action.WorkingSetAction")
@ActionRegistration(displayName = "#CTL_WorkingSet")
@ActionReferences({
    @ActionReference(path = "Menu/View", position = 405, separatorAfter = 475)
})
@Messages("CTL_WorkingSet=Working Set")
public final class WorkingSetAction extends AbstractAction implements Presenter.Menu {

    public WorkingSetAction() {
        putValue(Action.SHORT_DESCRIPTION, "Working Set");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();

        model.setWorkingSetName(null);
        SingleModelManagers.GENERAL_SETTINGS.save(model);
    }

    @Override
    public JMenuItem getMenuPresenter() {
        JMenu menu = new JMenu("Working Set");

        addMenuItems(menu);
        SingleModelManagers.WORKING_SET.addModelOnChangeListener(new SyncMenuItemsHandler(menu));

        return menu;
    }

    private void addMenuItems(final JMenu menu) {
        GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
        WorkingSetSettings workingSet = SingleModelManagers.WORKING_SET.get();

        menu.removeAll();

        ButtonGroup group = new ButtonGroup();

        JCheckBoxMenuItem all = new JCheckBoxMenuItem("All");
        all.addActionListener(this);
        all.setState(model.getWorkingSetName() == null);

        menu.add(all);
        group.add(all);

        for(WorkingSetSettings.Child child : workingSet.getPreferences()) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(child.getName());

            item.addActionListener(new WorkingSetHandler(child, item));
            item.setState(child.getName().equals(model.getWorkingSetName()));

            group.add(item);
            menu.add(item);
        }
    }

    private class WorkingSetHandler implements ActionListener {

        private WorkingSetSettings.Child workingSetName;

        private JCheckBoxMenuItem item;

        private WorkingSetHandler(WorkingSetSettings.Child workingSetName, JCheckBoxMenuItem item) {
            this.workingSetName = workingSetName;
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();

            item.setState(true);
            model.setWorkingSetName(workingSetName.getName());
            SingleModelManagers.GENERAL_SETTINGS.save(model);
        }
    }

    private class SyncMenuItemsHandler implements ModelOnChangeListener {
        private JMenu component;

        private SyncMenuItemsHandler(JMenu component) {
            this.component = component;
        }

        @Override
        public void onChange(ModelEvent evt) {
            component.removeAll();
            addMenuItems(component);
        }
    }
}
