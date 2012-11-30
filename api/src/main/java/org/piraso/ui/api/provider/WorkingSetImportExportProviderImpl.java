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

package org.piraso.ui.api.provider;

import org.piraso.api.JacksonUtils;
import org.piraso.api.converter.ObjectConverterRegistry;
import org.piraso.api.converter.TypeConverter;
import org.piraso.api.entry.ObjectEntry;
import org.piraso.api.io.PirasoObjectLoaderRegistry;
import org.piraso.ui.api.ExportHandler;
import org.piraso.ui.api.ImportExportProvider;
import org.piraso.ui.api.ImportHandler;
import org.piraso.ui.api.WorkingSetSettings;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Stack trace filter import export provider
 */
@ServiceProvider(service=ImportExportProvider.class)
public class WorkingSetImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(WorkingSetSettings.class, new TypeConverter<WorkingSetSettings>(WorkingSetSettings.class));
    }

    private static final ObjectMapper MAPPER = JacksonUtils.MAPPER;

    public static final String OPTION = "Working Set";

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
                WorkingSetSettings model = (WorkingSetSettings) PirasoObjectLoaderRegistry.INSTANCE.loadObject(entry.getClassName(), entry.getStrValue());

                SingleModelManagers.WORKING_SET.save(model);
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
            try {
                WorkingSetSettings model = SingleModelManagers.WORKING_SET.get();
                return MAPPER.writeValueAsString(new ObjectEntry(model, WorkingSetSettings.class));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}

