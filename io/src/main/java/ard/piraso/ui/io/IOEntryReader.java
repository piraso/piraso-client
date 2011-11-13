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

package ard.piraso.ui.io;

import ard.piraso.api.io.EntryReadEvent;
import ard.piraso.api.io.EntryReadListener;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adleon
 */
public class IOEntryReader implements EntryReadListener {
    
    private static final Logger LOG = Logger.getLogger(IOEntryReader.class.getName());

    private IOEntryManager manager;

    private IOEntrySource source;
    
    private long lastEntryTime;
    
    private List<IOEntryListener> listeners = Collections.synchronizedList(new LinkedList<IOEntryListener>());

    public IOEntryReader(IOEntrySource source) {
        Validate.notNull(source, "Source should not be null.");
        
        this.source = source;
    }
    
    public String getId() {
        return source.getId();
    }
    
    public String getWatchedAddr() {
        return source.getWatchedAddr();
    }
    
    public void start() {
        source.addListener(this);
        source.start();
    }
    
    public void stop() {
        source.stop();
    }
    
    public boolean isStopped() {
        return source.isStopped();
    }

    public IOEntryManager getManager() {
        return manager;
    }
    
    public long getIdleTime() {
        return System.currentTimeMillis() - lastEntryTime;
    }
    
    @Override
    public synchronized void readEntry(EntryReadEvent evt) {
        lastEntryTime = System.currentTimeMillis();
        if(manager == null) {
            manager = new IOEntryManager(source.getId());
        }
        
        try {
            IOEntry entry = manager.addEntry(evt);
            
            fireEntryReadEvent(new IOEntryEvent(this, entry));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    

    @Override
    public void started(EntryReadEvent evt) {
        fireStartedEvent(new IOEntryEvent(this, evt.getId(), evt.getWatchedAddr()));
    }
    
    public void fireStartedEvent(IOEntryEvent evt) {
        List<IOEntryListener> tmp = new ArrayList<IOEntryListener>(listeners);
        for(IOEntryListener listener : tmp) {
            listener.started(evt);
        }
    }
    
    public void fireEntryReadEvent(IOEntryEvent evt) {
        List<IOEntryListener> tmp = new ArrayList<IOEntryListener>(listeners);
        for(IOEntryListener listener : tmp) {
            listener.receivedEntry(evt);
        }
    }
    
    public List<IOEntryListener> getListeners() {
        return listeners;
    }

    public void addListener(IOEntryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IOEntryListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }
}
