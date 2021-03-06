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

import org.piraso.ui.base.ExportDialog;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ActionID(category="File", id="org.piraso.ui.base.action.ExportSettingsAction")
@ActionRegistration(displayName="Export Settings...")
@ActionReferences({
        @ActionReference(path="Menu/File", position=1950, separatorAfter = 2025)
})
public final class ExportSettingsAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        new ExportDialog().setVisible(true);
    }
}
