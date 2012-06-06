package ard.piraso.ui.base.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.api.entry.ObjectEntryUtils;
import ard.piraso.ui.api.*;
import ard.piraso.ui.api.manager.ModelVisitor;
import ard.piraso.ui.base.manager.ModelManagers;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Monitor provider.
 */
@ServiceProvider(service=ImportExportProvider.class)
public class MonitorImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(MonitorSettings.class, new TypeConverter<MonitorSettings>(MonitorSettings.class));
    }

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

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
