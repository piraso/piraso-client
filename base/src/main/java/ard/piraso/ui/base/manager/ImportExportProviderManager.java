package ard.piraso.ui.base.manager;

import ard.piraso.ui.api.ExportHandler;
import ard.piraso.ui.api.ImportExportProvider;
import org.openide.util.Lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Retrieve all options
 */
public enum ImportExportProviderManager {
    INSTANCE;

    public List<ExportHandler> getExportHandlers() {
        List<ExportHandler> options = new ArrayList<ExportHandler>();
        Collection<? extends ImportExportProvider> providers = Lookup.getDefault().lookupAll(ImportExportProvider.class);

        for(ImportExportProvider provider : providers) {
            options.add(provider.getExportHandler());
        }

        return options;
    }
}
