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
package ard.piraso.ui.base.action;

import ard.piraso.ui.base.ContextMonitorDialog;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category="Tools", id="ard.piraso.ui.base.action.ManageProfilesShowAction")
@ActionRegistration(iconBase="ard/piraso/ui/base/icons/gnome_network_idle.png", iconInMenu=true, displayName="Manage Profiles")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 605),
    @ActionReference(path = "Toolbars/Tools", position = 605)
})
public final class ManageProfilesShowAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ContextMonitorDialog dialog = new ContextMonitorDialog();        
        dialog.setVisible(true);
    }
}
