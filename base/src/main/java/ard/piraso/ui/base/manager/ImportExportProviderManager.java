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

package ard.piraso.ui.base.manager;

import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import ard.piraso.ui.api.ImportHandler;
import org.openide.util.Lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Retrieve all options
 */
public enum ImportExportProviderManager {
    INSTANCE;

    public List<ExportHandler> getExportHandlers() {
        List<ExportHandler> options = new ArrayList<ExportHandler>();
        Collection<? extends ImportExportProvider> providers = Lookup.getDefault().lookupAll(ImportExportProvider.class);

        for(ImportExportProvider provider : providers) {
            options.add(provider.getExportHandler());
        }

        return options;
    }

    public List<ImportHandler> getImportHandlers() {
        List<ImportHandler> options = new ArrayList<ImportHandler>();
        Collection<? extends ImportExportProvider> providers = Lookup.getDefault().lookupAll(ImportExportProvider.class);

        for(ImportExportProvider provider : providers) {
            options.add(provider.getImportHandler());
        }

        return options;
    }
}
