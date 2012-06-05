package ard.piraso.ui.api.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.api.io.PirasoObjectLoaderRegistry;
import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import ard.piraso.ui.api.ImportHandler;
import ard.piraso.ui.api.StackTraceFilterModel;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Stack trace filter import export provider
 */
@ServiceProvider(service=ImportExportProvider.class)
public class StackTraceFilterImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(StackTraceFilterModel.class, new TypeConverter<StackTraceFilterModel>(StackTraceFilterModel.class));
    }

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

    public static final String OPTION = "Stack Trace Filters";

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
                StackTraceFilterModel model = (StackTraceFilterModel) PirasoObjectLoaderRegistry.INSTANCE.loadObject(entry.getClassName(), entry.getStrValue());

                SingleModelManagers.STACK_TRACE_FILTER.save(model);
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
                StackTraceFilterModel model = SingleModelManagers.STACK_TRACE_FILTER.get();
                return MAPPER.writeValueAsString(new ObjectEntry(model, StackTraceFilterModel.class));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}

