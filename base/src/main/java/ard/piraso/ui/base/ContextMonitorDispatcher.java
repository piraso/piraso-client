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

import ard.piraso.ui.base.model.NewContextMonitorModel;
import ard.piraso.ui.io.IOEntryReader;
import ard.piraso.ui.io.IOEntrySource;
import ard.piraso.ui.io.impl.FileEntrySource;
import ard.piraso.ui.io.impl.HttpEntrySource;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author adleon
 */
public final class ContextMonitorDispatcher {
    public static void forward(NewContextMonitorModel model) {
        forward(new HttpEntrySource(model.getPreferences(), model.getLoggingUrl(), model.getWatchedAddr()));        
    }
    
    public static void forward(File file) throws IOException {
        forward(new FileEntrySource(file));
    }
    
    public static void forward(IOEntrySource source) {
        IOEntryReader reader = new IOEntryReader(source);
        ContextMonitorTopComponent editor = new ContextMonitorTopComponent(reader);
        
        editor.open();
        editor.requestActive();            
    }
}
