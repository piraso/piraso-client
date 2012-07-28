/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private boolean showType = true;

    private boolean showJSONRawView = false;

    private String workingSetName = null;

    public GeneralSettingsModel() {
        fontSize = FontProviderManager.INSTANCE.getDefaultFont().getSize();
    }

    public boolean isShowJSONRawView() {
        return showJSONRawView;
    }

    public void setShowJSONRawView(boolean showJSONRawView) {
        this.showJSONRawView = showJSONRawView;
    }

    public String getWorkingSetName() {
        return workingSetName;
    }

    public void setWorkingSetName(String workingSetName) {
        this.workingSetName = workingSetName;
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

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public void toggleElapseTime() {
        showElapseTime = !showElapseTime;
    }

    public void toggleJSONRawView() {
        showJSONRawView = !showJSONRawView;
    }

    public void toggleType() {
        showType = !showType;
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
