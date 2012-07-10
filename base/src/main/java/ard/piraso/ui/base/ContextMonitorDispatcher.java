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

package ard.piraso.ui.base;

import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.ProfileModel;
import ard.piraso.ui.base.manager.ModelManagers;
import ard.piraso.ui.io.IOEntryReader;
import ard.piraso.ui.io.IOEntrySource;
import ard.piraso.ui.io.impl.FileEntrySource;
import ard.piraso.ui.io.impl.HttpEntrySource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adleon
 */
public final class ContextMonitorDispatcher {

    public static void forwardByMonitorName(String name) {
        NewContextMonitorModel model = ModelManagers.MONITORS.get(name);
        processModels(Arrays.asList(model));
    }

    public static void forwardByProfileName(String profileName) {
        final List<NewContextMonitorModel> models = new ArrayList<NewContextMonitorModel>();
        ProfileModel profile =  ModelManagers.PROFILES.get(profileName);

        if(CollectionUtils.isNotEmpty(profile.getMonitors())) {
            for(String monitorName : profile.getMonitors()) {
                NewContextMonitorModel newContextMonitor = ModelManagers.MONITORS.get(monitorName);

                if(newContextMonitor != null) {
                    models.add(newContextMonitor);
                }
            }
        }

        processModels(models);
    }

    private static void processModels(final List<NewContextMonitorModel> models) {
        new ConnectingDialog(models).start().setVisible(true);
    }

    public static void handleResults(List<HttpEntrySource> validResults, Map<NewContextMonitorModel, String> failures) {
        if(CollectionUtils.isNotEmpty(validResults)) {
            for(HttpEntrySource source : validResults) {
                forward(source);
            }
        }

        if(MapUtils.isNotEmpty(failures)) {
            new FailureDialog().show(failures);
        }
    }

    public static void forward(NewContextMonitorModel model) {
        forward(new HttpEntrySource(model.getPreferences(), model.getLoggingUrl(), model.getWatchedAddr()));        
    }
    
    public static void forward(File file) throws IOException {
        forward(new FileEntrySource(file));
    }
    
    public static void forward(IOEntrySource source) {
        forward(new IOEntryReader(source), source);
    }
    
    public static void forward(IOEntryReader reader, IOEntrySource source) {
        ContextMonitorTopComponent editor = new ContextMonitorTopComponent(reader, source.getName());

        editor.open();
        editor.requestActive();
    }
}
