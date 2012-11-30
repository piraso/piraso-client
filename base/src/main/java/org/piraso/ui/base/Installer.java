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

package org.piraso.ui.base;

import org.piraso.api.io.PirasoObjectLoaderRegistry;
import org.piraso.ui.api.PirasoObjectLoaderProvider;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.piraso.ui.base.manager.SVNUpdateManager;
import jsyntaxpane.DefaultSyntaxKit;
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
        
        DefaultSyntaxKit.initKit();
        registerEntryLoaders();
    }

    private void registerEntryLoaders() {
        // register all piraso entry loader
        Collection<? extends PirasoObjectLoaderProvider> providers = Lookup.getDefault().lookupAll(PirasoObjectLoaderProvider.class);
        for(PirasoObjectLoaderProvider loaderProvider : providers) {
            LOG.info("Added loader by: " + loaderProvider.getClass().getName());
            PirasoObjectLoaderRegistry.INSTANCE.addEntryLoader(loaderProvider.getLoader());
        }

        LOG.info("Loading SVN if needed...");
        try {
            if(SingleModelManagers.SVN_SETTINGS.get().isLoadOnStartup()) {
                SVNUpdateManager.create().updateSettings(false);
            } else {
                LOG.info("SVN Update settings is not load on startup.");
            }
        } catch (Exception e) {
            LOG.warning(String.valueOf(e));
        }
    }

    @Override
    public boolean closing() {
        LOG.info("Module Closing.");

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
