package ard.piraso.ui.api;

import ard.piraso.ui.api.manager.FontProviderManager;

/**
 * contains general settings
 */
public class GeneralSettingsModel {

    private int fontSize;

    private boolean showElapseTime = false;

    private boolean showRequestId = false;

    private boolean showMessageGroup = true;

    public GeneralSettingsModel() {
        fontSize = FontProviderManager.INSTANCE.getDefaultFont().getSize();
    }

    public boolean isShowMessageGroup() {
        return showMessageGroup;
    }

    public void setShowMessageGroup(boolean showMessageGroup) {
        this.showMessageGroup = showMessageGroup;
    }

    public boolean isShowRequestId() {
        return showRequestId;
    }

    public void setShowRequestId(boolean showRequestId) {
        this.showRequestId = showRequestId;
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

    public void toggleRequestId() {
        showRequestId = !showRequestId;
    }

    public void toggleShowGroupMessage() {
        showMessageGroup = !showMessageGroup;
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
