package ard.piraso.ui.api;

import ard.piraso.ui.api.manager.FontProviderManager;

/**
 * contains general settings
 */
public class GeneralSettingsModel {

    private int fontSize;

    public GeneralSettingsModel() {
        fontSize = FontProviderManager.INSTANCE.getDefaultFont().getSize();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void smallerFontSize() {
        fontSize--;
    }

    public void biggerFontSize() {
        fontSize++;
    }
}
