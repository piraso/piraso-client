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

package ard.piraso.ui.base.action;

import ard.piraso.ui.base.cookie.StopCookie;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category = "Tools", id = "ard.piraso.ui.base.action.StopAction")
@ActionRegistration(iconBase="ard/piraso/ui/base/icons/stop.png", iconInMenu=true, displayName = "#CTL_StopAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 105),
    @ActionReference(path = "Toolbars/Tools", position = 105)
})
@Messages("CTL_StopAction=Stop")
public final class StartAction implements ActionListener {

    private final StopCookie context;

    public StartAction(StopCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.stop();
    }
}