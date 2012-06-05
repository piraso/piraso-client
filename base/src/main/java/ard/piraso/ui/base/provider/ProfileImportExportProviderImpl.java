package ard.piraso.ui.base.provider;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.converter.ObjectConverterRegistry;
import ard.piraso.api.converter.TypeConverter;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.ui.api.*;
import ard.piraso.ui.api.manager.ModelVisitor;
import ard.piraso.ui.base.manager.ModelManagers;
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

    private final ExportHandler handler = new Handler();

    @Override
    public ExportHandler getExportHandler() {
        return handler;
    }

    public class Handler implements ExportHandler {
        @Override
        public String getOption() {
            return "Profiles";
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
