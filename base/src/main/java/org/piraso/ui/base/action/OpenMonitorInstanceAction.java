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

package org.piraso.ui.base.action;

import org.piraso.ui.base.OpenMonitorInstanceDialog;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category = "File", id = "org.piraso.ui.base.action.OpenMonitorInstanceAction")
@ActionRegistration(iconBase = "org/piraso/ui/base/icons/open.png", displayName = "#CTL_OpenAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1600),
    @ActionReference(path = "Toolbars/File", position = 320)
})
@Messages("CTL_OpenAction=Open Monitor Instance...")
public final class OpenMonitorInstanceAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        new OpenMonitorInstanceDialog().setVisible(true);
    }
}
