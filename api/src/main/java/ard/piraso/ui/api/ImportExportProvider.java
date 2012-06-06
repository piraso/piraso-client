package ard.piraso.ui.api;

/**
 * Import/Export provider
 */
public interface ImportExportProvider {

    public ExportHandler getExportHandler();

    public ImportHandler getImportHandler();
}
