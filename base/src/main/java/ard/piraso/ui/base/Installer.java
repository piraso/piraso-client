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

package ard.piraso.ui.base;

import ard.piraso.api.io.PirasoEntryLoaderRegistry;
import ard.piraso.ui.api.PirasoEntryLoaderProvider;
import ard.piraso.ui.base.manager.IdleTimeout1Manager;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

public class Installer extends ModuleInstall {
    private static final Logger LOG = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        LOG.info("Module Started.");
        IdleTimeout1Manager.INSTANCE.start();

        // register all piraso entry loader
        Collection<? extends PirasoEntryLoaderProvider> providers = Lookup.getDefault().lookupAll(PirasoEntryLoaderProvider.class);
        for(PirasoEntryLoaderProvider provider : providers) {
            LOG.info("Added loader by: " + provider.getClass().getName());
            PirasoEntryLoaderRegistry.INSTANCE.addEntryLoader(provider.getLoader());
        }
    }

    @Override
    public boolean closing() {
        LOG.info("Module Closing.");
        IdleTimeout1Manager.INSTANCE.stop();

        // ensure that all context monitor are closed
        Set<TopComponent> opened = TopComponent.getRegistry().getOpened();
        for(TopComponent component : opened) {
            if(ContextMonitorTopComponent.class.isInstance(component)) {
                ContextMonitorTopComponent editor = (ContextMonitorTopComponent) component;
                editor.componentClosed();
            }
        }

        return true;
    }
}
