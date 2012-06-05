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
 * Profile import export provider.
 */
@ServiceProvider(service = ImportExportProvider.class)
public class ProfileImportExportProviderImpl implements ImportExportProvider {

    static {
        ObjectConverterRegistry.register(ProfileSettings.class, new TypeConverter<ProfileSettings>(ProfileSettings.class));
    }

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();
    public static final String OPTION = "Profiles";

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
                ProfileSettings setting = (ProfileSettings) ObjectEntryUtils.toObject(entry);

                if(CollectionUtils.isNotEmpty(setting.getModels())) {
                    for(ProfileModel profileModel : setting.getModels()) {
                        ModelManagers.PROFILES.save(profileModel);
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
            final ProfileSettings settings = new ProfileSettings();

            ModelManagers.PROFILES.visit(new ModelVisitor<ProfileModel>() {
                @Override
                public void visit(ProfileModel model) {
                    settings.add(model);
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
