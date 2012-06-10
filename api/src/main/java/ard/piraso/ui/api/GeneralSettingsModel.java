package ard.piraso.ui.api;

import ard.piraso.ui.api.manager.FontProviderManager;

/**
 * contains general settings
 */
public class GeneralSettingsModel {

    private int fontSize;

    private boolean showElapseTime = false;

    public GeneralSettingsModel() {
        fontSize = FontProviderManager.INSTANCE.getDefaultFont().getSize();
    }

    public boolean isShowElapseTime() {
        return showElapseTime;
    }

    public void setShowElapseTime(boolean showElapseTime) {
        this.showElapseTime = showElapseTime;
    }

    public void toggleElapseTime() {
        showElapseTime = !showElapseTime;
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
