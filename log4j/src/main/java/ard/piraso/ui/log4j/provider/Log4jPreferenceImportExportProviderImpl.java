package ard.piraso.ui.log4j.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import ard.piraso.ui.log4j.Log4jPreferencesModel;
import ard.piraso.ui.log4j.SingleModelManagers;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Stack trace filter import export provider
 */
@ServiceProvider(service=ImportExportProvider.class)
public class Log4jPreferenceImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(Log4jPreferencesModel.class, new TypeConverter<Log4jPreferencesModel>(Log4jPreferencesModel.class));
    }

    private final ExportHandler handler = new Handler();

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

    @Override
    public ExportHandler getExportHandler() {
        return handler;
    }

    public class Handler implements ExportHandler {
        @Override
        public String getOption() {
            return "Log4j Preferences";
        }

        @Override
        public String getExportEntry() {
            try {
                Log4jPreferencesModel model = SingleModelManagers.LOG4J_PREFERENCES.get();
                return MAPPER.writeValueAsString(new ObjectEntry(model, Log4jPreferencesModel.class));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}

