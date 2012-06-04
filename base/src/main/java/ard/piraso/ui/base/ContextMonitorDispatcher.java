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

/**
 *
 * @author adleon
 */
public final class ContextMonitorDispatcher {

    public static void forwardByMonitorName(String name) {
        NewContextMonitorModel model = ModelManagers.MONITORS.get(name);
        processModels(Arrays.asList(model));
    }

    public static void forwardByProfileName(String profile) {
        List<NewContextMonitorModel> results = new ArrayList<NewContextMonitorModel>();
        ProfileModel model =  ModelManagers.PROFILES.get(profile);

        if(CollectionUtils.isNotEmpty(model.getMonitors())) {
            for(String monitorName : model.getMonitors()) {
                NewContextMonitorModel newContextMonitor = ModelManagers.MONITORS.get(monitorName);

                if(newContextMonitor != null) {
                    results.add(newContextMonitor);
                }
            }
        }

        processModels(results);
    }

    private static void processModels(List<NewContextMonitorModel> results) {
        ConnectingDialog dialog = new ConnectingDialog();
        dialog.startTests(results);

        if(CollectionUtils.isNotEmpty(dialog.getValidResults())) {
            for(HttpEntrySource source : dialog.getValidResults()) {
                forward(source);
            }
        }

        if(MapUtils.isNotEmpty(dialog.getFailures())) {
            new FailureDialog().show(dialog.getFailures());
        }
    }

    public static void forward(NewContextMonitorModel model) {
        forward(new HttpEntrySource(model.getPreferences(), model.getLoggingUrl(), model.getWatchedAddr()));        
    }
    
    public static void forward(File file) throws IOException {
        forward(new FileEntrySource(file));
    }
    
    public static void forward(IOEntrySource source) {
        IOEntryReader reader = new IOEntryReader(source);
        ContextMonitorTopComponent editor = new ContextMonitorTopComponent(reader, source.getName());

        editor.open();
        editor.requestActive();
    }
}
