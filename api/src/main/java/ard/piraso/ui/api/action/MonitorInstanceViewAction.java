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
import org.openide.util.actions.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category = "View", id = "ard.piraso.ui.api.MonitorInstanceViewAction")
@ActionRegistration(displayName = "Monitor Instance")
@ActionReferences({
    @ActionReference(path = "Menu/View", position = 300)
})
public final class MonitorInstanceViewAction extends AbstractAction implements Presenter.Menu {

    public MonitorInstanceViewAction() {
        putValue(Action.SHORT_DESCRIPTION, "Monitor Instance");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public JMenuItem getMenuPresenter() {
        JMenu menu = new JMenu("Monitor Instance");

        final JCheckBoxMenuItem showElapseTime = new JCheckBoxMenuItem("Show Elapse Time");
        final JCheckBoxMenuItem showType = new JCheckBoxMenuItem("Show Type");
        final JCheckBoxMenuItem showRequestId = new JCheckBoxMenuItem("Show Request ID");
        final JCheckBoxMenuItem showMessageGroup = new JCheckBoxMenuItem("Show Message Group");
        final JCheckBoxMenuItem showJSONRawView = new JCheckBoxMenuItem("Show JSON Raw View");

        showType.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowType());
        showRequestId.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowRequestId());
        showElapseTime.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime());
        showMessageGroup.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowMessageGroup());
        showJSONRawView.setState(SingleModelManagers.GENERAL_SETTINGS.get().isShowJSONRawView());

        showElapseTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleElapseTime();
                showElapseTime.setState(model.isShowElapseTime());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        showType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleType();
                showType.setState(model.isShowType());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        showRequestId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleRequestId();
                showRequestId.setState(model.isShowRequestId());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        showMessageGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleShowGroupMessage();
                showMessageGroup.setState(model.isShowMessageGroup());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        showJSONRawView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralSettingsModel model = SingleModelManagers.GENERAL_SETTINGS.get();
                model.toggleJSONRawView();
                showJSONRawView.setState(model.isShowJSONRawView());
                SingleModelManagers.GENERAL_SETTINGS.save(model);
            }
        });

        menu.add(showElapseTime);
        menu.add(showType);
        menu.add(showRequestId);
        menu.add(showMessageGroup);
        menu.add(showJSONRawView);

        return menu;
    }
}
