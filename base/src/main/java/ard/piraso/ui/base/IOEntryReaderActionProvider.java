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

import ard.piraso.ui.base.cookie.IOEntryReaderStartCookie;
import ard.piraso.ui.base.cookie.IOEntryReaderStopCookie;
import ard.piraso.ui.base.cookie.StartCookie;
import ard.piraso.ui.base.cookie.StopCookie;
import ard.piraso.ui.io.IOEntryEvent;
import ard.piraso.ui.io.IOEntryLifecycleListener;
import ard.piraso.ui.io.IOEntryReader;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author adeleon
 */
public class IOEntryReaderActionProvider {
    
    private IOEntryReader reader;
    
    private InstanceContent content;
    
    private StartCookie startCookie;
    
    private StopCookie stopCookie;
    
    public IOEntryReaderActionProvider(IOEntryReader reader, InstanceContent content) {
        this.reader = reader;
        this.content = content;
        
        this.startCookie = new IOEntryReaderStartCookie(reader);
        this.stopCookie = new IOEntryReaderStopCookie(reader);
        
        initEvents();
    }
    
    private void initEvents() {
        reader.addLiveCycleListener(new IOEntryLifecycleListener() {
            @Override
            public void started(IOEntryEvent evt) {
                content.remove(startCookie);
                content.add(stopCookie);
            }

            @Override
            public void stopped(IOEntryEvent evt) {
                content.add(startCookie);
                content.remove(stopCookie);
            }
        });
        
        content.add(startCookie);
    }

    public StartCookie getStartCookie() {
        return startCookie;
    }

    public StopCookie getStopCookie() {
        return stopCookie;
    }
}
