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

import ard.piraso.api.entry.ElapseTimeAware;
import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.RequestEntry;
import ard.piraso.ui.base.manager.IdleTimeoutAware;
import ard.piraso.ui.base.manager.IdleTimeoutManager;
import ard.piraso.ui.base.manager.MessageProviderManager;
import ard.piraso.ui.io.*;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Table model for handling {@link IOEntry} class.
 * 
 * @author adleon
 */
public class IOEntryTableModel extends AbstractTableModel implements IOEntryListener, IdleTimeoutAware {

    private static final Logger LOG = Logger.getLogger(IOEntryTableModel.class.getName());

    private static final long DEFAULT_IDLE_TIMEOUT = 200l;

    private long idleTimeout = DEFAULT_IDLE_TIMEOUT;

    private IOEntryManager manager;

    private Long currentRequestId = null;

    private List<IOEntry> entries = Collections.synchronizedList(new ArrayList<IOEntry>());

    private long lastReceivedTime;

    private boolean alive;

    private boolean allowScrolling;
    
    private final IOEntryTableModel self;

    private IOEntryComboBoxModel comboBoxModel;

    private List<ChangeRequestListener> listeners = Collections.synchronizedList(new LinkedList<ChangeRequestListener>());

    public IOEntryTableModel(IOEntryReader reader) {
        this(reader, DEFAULT_IDLE_TIMEOUT);
    }

    public IOEntryTableModel(IOEntryReader reader, long idleTimeout) {
        this.manager = reader.getManager();
        this.idleTimeout = idleTimeout;
        this.lastReceivedTime = System.currentTimeMillis();

        comboBoxModel = new IOEntryComboBoxModel();
        reader.addListener(this);
        self = this;
    }

    public IOEntryComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }

    public Long getCurrentRequestId() {
        return currentRequestId;
    }

    public void setCurrentRequestId(Long newRequestId) {
        synchronized (self) {
            Long oldRequestId = currentRequestId;
            if((currentRequestId == null && newRequestId != null) ||
                    newRequestId != null && !newRequestId.equals(currentRequestId)) {
                currentRequestId = newRequestId;
                selectRequestItem(oldRequestId, newRequestId);
            }
        }
    }

    public boolean isAllowScrolling() {
        return allowScrolling;
    }

    public void setAllowScrolling(boolean allowScrolling) {
        this.allowScrolling = allowScrolling;
    }

    public void start() {
        synchronized (self) {
            alive = true;
            IdleTimeoutManager.INSTANCE.add(this);
        }
    }

    public void stop() {
        synchronized (self) {
            alive = false;
            IdleTimeoutManager.INSTANCE.remove(this);
        }
    }
    
    @Override
    public void started(IOEntryEvent evt) {
        start();
    }

    @Override
    public boolean isIdleTimeout() {
        synchronized (self) {
            return CollectionUtils.isNotEmpty(entries) &&
                    System.currentTimeMillis() - lastReceivedTime >= idleTimeout;
        }
    }

    @Override
    public void doOnTimeout() throws InvocationTargetException, InterruptedException {
        List<IOEntry> process;

        synchronized (self) {
            // do nothing when empty
            if(CollectionUtils.isEmpty(entries)) {
                return;
            }

            process = new ArrayList<IOEntry>(entries);
            entries.clear();
        }

        SwingUtilities.invokeAndWait(new EntryReceivedRunnable(process));
    }

    @Override
    public void receivedEntry(IOEntryEvent evt) {
        lastReceivedTime = System.currentTimeMillis();
        if(!alive) {
            return;
        }

        entries.add(evt.getEntry());

        if(isIdleTimeout()) {
            try {
                doOnTimeout();
            } catch(Exception e) {
                LOG.warning(e.getMessage());
            }
        }
    }

    @Override
    public int getRowCount() {
        return manager.getTotalEntries(currentRequestId);
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    private String getGroup(Entry entry) {
        if(entry.getGroup() == null) {
            return null;
        }

        if(CollectionUtils.isNotEmpty(entry.getGroup().getGroups())) {
            return String.valueOf(entry.getGroup().getGroups());
        }

        return null;
    }

    private String getMessage(Entry entry) {
        return MessageProviderManager.INSTANCE.getMessage(entry);
    }

    private String getElapsePrettyPrint(Entry entry) {
        ElapseTimeAware aware = ((ElapseTimeAware) entry);
        if(!ElapseTimeAware.class.isInstance(entry)) {
            return null;
        }

        if(aware.getElapseTime() == null) {
            return null;
        }

        return aware.getElapseTime().prettyPrint();
    }

    public Long getElapseMilliseconds(Entry entry) {
        ElapseTimeAware aware = ((ElapseTimeAware) entry);
        if(!ElapseTimeAware.class.isInstance(entry)) {
            return 0l;
        }

        if(aware.getElapseTime() == null) {
            return 0l;
        }

        return aware.getElapseTime().getElapseTime();
    }

    public IOEntry getEntryAt(int rowIndex) {
        try {
            return manager.getEntryAt(currentRequestId, rowIndex);
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            IOEntry ioEntry = manager.getEntryAt(currentRequestId, rowIndex);
            Entry entry = ioEntry.getEntry();

            switch(columnIndex) {
                case 0: return ioEntry.getRowNum();
                case 1: return entry.getLevel();
                case 2: return getMessage(entry);
                case 3: return getElapsePrettyPrint(entry);
            }
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    public void fireChangeRequestEvent(ChangeRequestEvent evt) {
        List<ChangeRequestListener> tmp = new ArrayList<ChangeRequestListener>(listeners);
        for(ChangeRequestListener listener : tmp) {
            listener.changeRequest(evt);
        }
    }

    public List<ChangeRequestListener> getListeners() {
        return listeners;
    }

    public void addListener(ChangeRequestListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangeRequestListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public void selectRequestItem(Long oldValue, Long newValue) {
        comboBoxModel.setSelectedItem(manager.getRequest(newValue).getEntry());
        fireChangeRequestEvent(new ChangeRequestEvent(this, oldValue, newValue));
    }

    private class EntryReceivedRunnable implements Runnable {

        private List<IOEntry> entries;

        private EntryReceivedRunnable(List<IOEntry> entries) {
            this.entries = entries;
        }

        public void doScroll() {
             Long oldRequestId = currentRequestId;

            // if not on the same request
            // just fire a table data change to do repaint of table
            for(IOEntry entry : entries) {
                if(oldRequestId != null && !entry.getId().equals(oldRequestId)) {
                    synchronized (self) {
                        currentRequestId = entry.getId();
                        selectRequestItem(oldRequestId, currentRequestId);
                        fireTableDataChanged();
                    }

                    return;
                }
            }

            insertCurrentRequestEntries();
        }
        
        public void insertCurrentRequestEntries() {
            List<IOEntry> allowedEntries = new ArrayList<IOEntry>();
            
            for(IOEntry entry : entries) {
                if(entry.getId().equals(currentRequestId)) {
                    allowedEntries.add(entry);
                }
            }
            
            // otherwise since same then this is just a simple insert on same request id
            int totalRow = manager.getTotalEntries(currentRequestId);
            fireTableRowsInserted(totalRow - allowedEntries.size(), totalRow);            
        }

        public void noScroll() {
            synchronized (self) {
                if(currentRequestId == null) {
                    currentRequestId = entries.get(0).getId();
                    selectRequestItem(null, currentRequestId);
                }
            }

            insertCurrentRequestEntries();
        }

        @Override
        public void run() {
            if(!alive || CollectionUtils.isEmpty(entries)) {
                return;
            }

            // ensure to add all request to combo box model
            for(IOEntry entry : entries) {
                if(RequestEntry.class.isInstance(entry.getEntry())) {
                    comboBoxModel.addRequest((RequestEntry) entry.getEntry());
                }
            }

            if(isAllowScrolling()) {
                doScroll();
            } else {
                noScroll();
            }
        }
    }
}
