package ard.piraso.ui.log4j.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.api.io.PirasoObjectLoaderRegistry;
import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import ard.piraso.ui.api.ImportHandler;
import ard.piraso.ui.log4j.SingleModelManagers;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Stack trace filter import export provider
 */
@ServiceProvider(service=ImportExportProvider.class)
public class Log4jPreferenceImportExportProviderImpl implements ImportExportProvider {

    public static final String OPTION = "Log4j Preferences";static {
        ObjectConverterRegistry.register(Log4jPreferencesModel.class, new TypeConverter<Log4jPreferencesModel>(Log4jPreferencesModel.class));
    }

    private final ExportHandler exportHandler = new Export();

    private final ImportHandler importHandler = new Import();

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

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
        @SuppressWarnings("unchecked")
        public void handle(String settingStr) {
            try {
                ObjectEntry entry = MAPPER.readValue(settingStr, ObjectEntry.class);
                Log4jPreferencesModel model = (Log4jPreferencesModel) PirasoObjectLoaderRegistry.INSTANCE.loadObject(entry.getClassName(), entry.getStrValue());

                SingleModelManagers.LOG4J_PREFERENCES.save(model);
            } catch (Exception e) {
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
                Log4jPreferencesModel model = SingleModelManagers.LOG4J_PREFERENCES.get();
                return MAPPER.writeValueAsString(new ObjectEntry(model, Log4jPreferencesModel.class));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}

