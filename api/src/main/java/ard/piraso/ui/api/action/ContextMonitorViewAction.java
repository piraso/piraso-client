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

@ActionID(category = "View", id = "ard.piraso.ui.api.ContextMonitorViewAction")
@ActionRegistration(displayName = "#CTL_ViewElapseTimeAction")
@ActionReferences({
    @ActionReference(path = "Menu/View", position = 300)
})
@Messages("CTL_ViewElapseTimeAction=Show Elapse Time")
public final class ContextMonitorViewAction extends AbstractAction implements Presenter.Menu {

    public ContextMonitorViewAction() {
        putValue(Action.SHORT_DESCRIPTION, "Context Monitor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public JMenuItem getMenuPresenter() {
        JMenu menu = new JMenu("Context Monitor");

        final JCheckBoxMenuItem showElapseTime = new JCheckBoxMenuItem("Show Elapse Time");

        showElapseTime.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime());

        showElapseTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleElapseTime();
                showElapseTime.setState(model.isShowElapseTime());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        menu.add(showElapseTime);

        return menu;
    }
}
