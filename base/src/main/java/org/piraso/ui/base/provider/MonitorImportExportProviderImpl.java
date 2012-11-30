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

package org.piraso.ui.base.provider;

import org.piraso.api.JacksonUtils;
import org.piraso.api.converter.ObjectConverterRegistry;
import org.piraso.api.converter.TypeConverter;
import org.piraso.api.entry.ObjectEntry;
import org.piraso.api.entry.ObjectEntryUtils;
import org.piraso.ui.api.manager.ModelVisitor;
import org.piraso.ui.base.manager.ModelManagers;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;
import org.piraso.ui.api.*;

import java.io.IOException;

/**
 * Monitor provider.
 */
@ServiceProvider(service=ImportExportProvider.class)
public class MonitorImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(MonitorSettings.class, new TypeConverter<MonitorSettings>(MonitorSettings.class));
    }

    private static final ObjectMapper MAPPER = JacksonUtils.MAPPER;

    public static final String OPTION = "Monitors";

    private final ExportHandler exportHandler = new Export();

    private final ImportHandler importHandler = new Import();

    @Override
    public ExportHandler getExportHandler() {
        return exportHandler;
    }

    @Override
    public ImportHandler getImportHandler() {
        return importHandler;
    }

    public class Import implements ImportHandler {

        @Override
        public String getOption() {
            return OPTION;
        }

        @Override
        public void handle(String settingStr) {
            try {
                ObjectEntry entry = MAPPER.readValue(settingStr, ObjectEntry.class);
                MonitorSettings setting = (MonitorSettings) ObjectEntryUtils.toObject(entry);

                if(CollectionUtils.isNotEmpty(setting.getModels())) {
                    for(NewContextMonitorModel monitor : setting.getModels()) {
                        ModelManagers.MONITORS.save(monitor);
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }

    public class Export implements ExportHandler {

        @Override
        public String getOption() {
            return OPTION;
        }

        @Override
        public String getExportEntry() {
            final MonitorSettings settings = new MonitorSettings();

            ModelManagers.MONITORS.visit(new ModelVisitor<NewContextMonitorModel>() {
                @Override
                public void visit(NewContextMonitorModel newContextMonitorModel) {
                    settings.add(newContextMonitorModel);
                }
            });

            try {
                return MAPPER.writeValueAsString(new ObjectEntry(settings));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}
