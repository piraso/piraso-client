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

package org.piraso.ui.base.model;

import org.piraso.api.entry.ElapseTimeAware;
import org.piraso.api.entry.Entry;
import org.piraso.api.entry.RequestEntry;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.piraso.ui.api.util.JTableUtils;
import org.piraso.ui.io.IOEntry;
import org.piraso.ui.io.IOEntryEvent;
import org.piraso.ui.io.IOEntryReader;
import org.piraso.ui.io.IOEntryReceivedListener;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Table model for handling {@link org.piraso.ui.io.IOEntry} class.
 * 
 * @author adleon
 */
public class IOEntryTableModel extends AbstractTableModel implements IOEntryReceivedListener {

    private static final Logger LOG = Logger.getLogger(IOEntryTableModel.class.getName());

    private Long currentRequestId = null;

    private List<IOEntry> entries = Collections.synchronizedList(new ArrayList<IOEntry>());

    private boolean allowScrolling = true;
    
    private final IOEntryTableModel self;

    private IOEntryComboBoxModel comboBoxModel;
    
    private IOEntryReader reader;

    private JTable owningTable;

    private IOEntryFastCache cache;

    public IOEntryTableModel(IOEntryReader reader) {
        this.reader = reader;
        comboBoxModel = new IOEntryComboBoxModel();
        reader.addReceivedListener(this);
        self = this;
        cache = new IOEntryFastCache(reader);
    }

    public void setOwningTable(JTable owningTable) {
        this.owningTable = owningTable;
    }

    public void autoScrollTable(int rowNum) {
        if(owningTable != null) {
            JTableUtils.scrollTo(owningTable, rowNum);
        }
    }

    public IOEntryComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }

    public void setCurrentRequestId(Long newRequestId) {
        setCurrentRequestId(newRequestId, false);
    }

    public void setCurrentRequestId(Long newRequestId, boolean silent) {
        synchronized (self) {
            Long oldRequestId = currentRequestId;
            if((currentRequestId == null && newRequestId != null) ||
                    newRequestId != null && !newRequestId.equals(currentRequestId)) {
                selectRequestItem(oldRequestId, newRequestId);
                cache.setCurrentRequestId(currentRequestId);

                if(!silent) {
                    fireTableDataChanged();
                    autoScrollTable(1);
                }
            }
        }
    }

    public boolean isAllowScrolling() {
        return allowScrolling;
    }

    public void setAllowScrolling(boolean allowScrolling) {
        this.allowScrolling = allowScrolling;
    }

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

        SwingUtilities.invokeLater(new EntryReceivedRunnable(process));
    }

    @Override
    public void receivedEntry(IOEntryEvent evt) {
        entries.add(evt.getEntry());

        try {
            doOnTimeout();
        } catch(Exception e) {
            LOG.warning(e.getMessage());
        }
    }

    @Override
    public int getRowCount() {
        if(reader.getManager() == null || currentRequestId == null) {
            return 0;
        } else {
            return reader.getManager().size(currentRequestId);
        }
    }

    @Override
    public int getColumnCount() {
        int columnSize = 4;

        if(!SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime()) {
            columnSize--;
        }

        if(!SingleModelManagers.GENERAL_SETTINGS.get().isShowType()) {
            columnSize--;
        }

        return columnSize;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Long getElapseMilliseconds(Entry entry) {
        if(!ElapseTimeAware.class.isInstance(entry)) {
            return 0l;
        }

        ElapseTimeAware aware = ((ElapseTimeAware) entry);

        if(aware.getElapseTime() == null) {
            return 0l;
        }

        return aware.getElapseTime().getElapseTime();
    }

    public IOEntry getEntryAt(int rowIndex) {
        return cache.getEntryAt(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IOEntryFastCache.Element el = cache.getElementAt(rowIndex);

        if(el == null) {
            return null;
        }

        switch(columnIndex) {
            case 0: return el.rowNum;
            case 1: return SingleModelManagers.GENERAL_SETTINGS.get().isShowType() ? el.shortName : el.getActualMessage();
            case 2: return !SingleModelManagers.GENERAL_SETTINGS.get().isShowType() && SingleModelManagers.GENERAL_SETTINGS.get().isShowElapseTime() ? el.elapse : el.getActualMessage();
            case 3: return el.elapse;
        }

        return null;
    }

    public void selectRequestItem(Long oldValue, Long newValue) {
        currentRequestId = newValue;
        comboBoxModel.setSelectedItem(reader.getManager().getRequest(newValue).getEntry());
        cache.setCurrentRequestId(currentRequestId);
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
                        selectRequestItem(oldRequestId, entry.getId());
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
                    cache.addElement(currentRequestId, entry);
                }
            }
            
            // otherwise since same then this is just a simple insert on same request id
            int totalRow = reader.getManager().size(currentRequestId);
            fireTableRowsInserted(totalRow - allowedEntries.size(), totalRow);            
        }

        @Override
        public void run() {
            if(CollectionUtils.isEmpty(entries)) {
                return;
            }

            synchronized (self) {
                if(currentRequestId == null) {
                    selectRequestItem(null, entries.get(0).getId());
                }
            }

            IOEntry last = null;
            // ensure to add all request to combo box model
            for(IOEntry entry : entries) {
                if(RequestEntry.class.isInstance(entry.getEntry())) {
                    comboBoxModel.addRequest((RequestEntry) entry.getEntry());
                }
                last = entry;
            }

            if(isAllowScrolling()) {
                doScroll();

                if(last != null) {
                    autoScrollTable(last.getRowNum().intValue());
                }
            } else {
                insertCurrentRequestEntries();
            }
        }
    }
}
