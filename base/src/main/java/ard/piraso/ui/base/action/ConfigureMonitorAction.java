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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(category="Tools", id="ard.piraso.ui.base.action.ConfigureMonitorAction")
@ActionRegistration(iconBase="ard/piraso/ui/base/icons/monitor.png", iconInMenu=true, displayName="Configure Monitors")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 600),
    @ActionReference(path = "Toolbars/Tools", position = 600)
})
public final class ConfigureMonitorAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ContextMonitorDialog dialog = new ContextMonitorDialog();        
        dialog.setVisible(true);
    }
}
