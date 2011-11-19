/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.base.model;

import ard.piraso.api.Preferences;
import ard.piraso.ui.api.extension.WizardModel;
import ard.piraso.ui.base.manager.PreferenceProviderManager;

/**
 *
 * @author adleon
 */
public class NewContextMonitorModel implements WizardModel {
    private Preferences preferences;
    
    private String watchedAddr;
    
    private String loggingUrl;
    
    public NewContextMonitorModel() {
        this(false);
    }
    
    public NewContextMonitorModel(boolean defaultValues) {
        if(defaultValues) {
            preferences = PreferenceProviderManager.INSTANCE.createPreferences();
        }
    }

    public String getLoggingUrl() {
        return loggingUrl;
    }

    public void setLoggingUrl(String loggingUrl) {
        this.loggingUrl = loggingUrl;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public String getWatchedAddr() {
        return watchedAddr;
    }

    public void setWatchedAddr(String watchedAddr) {
        this.watchedAddr = watchedAddr;
    }
}
