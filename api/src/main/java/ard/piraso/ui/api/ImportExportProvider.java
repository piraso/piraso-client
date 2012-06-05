package ard.piraso.ui.api;

import ard.piraso.api.entry.ObjectEntry;

/**
 * Import/Export provider
 */
public interface ImportExportProvider {

    public ExportHandler getExportHandler();
}
