package ard.piraso.ui.api;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
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

    private final ExportHandler handler = new Handler();

    @Override
    public ExportHandler getExportHandler() {
        return handler;
    }

    public class Handler implements ExportHandler {
        @Override
        public String getOption() {
            return "Stack Trace Filters";
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

