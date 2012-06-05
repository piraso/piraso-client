package ard.piraso.ui.base.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import ard.piraso.ui.api.MonitorSettings;
import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.manager.ModelVisitor;
import ard.piraso.ui.base.manager.ModelManagers;
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

    private final ExportHandler handler = new Handler();

    @Override
    public ExportHandler getExportHandler() {
        return handler;
    }

    public class Handler implements ExportHandler {
        @Override
        public String getOption() {
            return "Monitors";
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
