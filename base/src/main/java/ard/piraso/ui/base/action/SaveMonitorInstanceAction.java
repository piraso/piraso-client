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

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.SaveCookie;
import org.openide.util.NbBundle.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


@ActionID(category = "File", id = "ard.piraso.ui.base.action.SaveMonitorInstanceAction")
@ActionRegistration(iconBase = "ard/piraso/ui/base/icons/save.png", displayName = "#CTL_SaveAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1700),
    @ActionReference(path = "Toolbars/File", position = 370)
})
@Messages("CTL_SaveAction=Save Monitor Instance...")
public final class SaveMonitorInstanceAction implements ActionListener {

    private SaveCookie cookie;

    public SaveMonitorInstanceAction(SaveCookie cookie) {
        this.cookie = cookie;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            cookie.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
