package ard.piraso.ui.api;

/**
 * Import Export
 */
public interface ImportHandler {

    String getOption();

    void handle(String settingStr);
}
