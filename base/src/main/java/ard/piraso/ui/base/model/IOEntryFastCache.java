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
import ard.piraso.ui.base.manager.MessageProviderManager;
import ard.piraso.ui.base.manager.PreferenceProviderManager;
import ard.piraso.ui.io.IOEntry;
import ard.piraso.ui.io.IOEntryReader;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Table Model Cache
 */
public class IOEntryFastCache {

    private static final Logger LOG = Logger.getLogger(IOEntryTableModel.class.getName());

    public static final int MAX_CACHE_SIZE = 1000;

    private Map<Long, Element> cache = new HashMap<Long, Element>(MAX_CACHE_SIZE);

    private Long currentRequestId;

    private IOEntryReader reader;

    public IOEntryFastCache(IOEntryReader reader) {
        this.reader = reader;
    }

    public void setCurrentRequestId(Long currentRequestId) {
        if(this.currentRequestId == null) {
            this.currentRequestId = currentRequestId;
        } else if(!this.currentRequestId.equals(currentRequestId)) {
            clear();
            this.currentRequestId = currentRequestId;
        }
    }

    public void clear() {
        synchronized (this) {
            cache.clear();
        }
    }

    public Element getElementAt(int rowIndex) {
        synchronized (this) {
            Long rowNum = (long) rowIndex;
            if(cache.containsKey(rowNum)) {
                Element el = cache.get(rowNum);
                el.touch();

                return el;
            }
        }

        try {
            IOEntry entry = reader.getManager().getEntryAt(currentRequestId, rowIndex);
            return addElement(currentRequestId, entry);
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    public IOEntry getEntryAt(final int rowIndex) {
        Element el = getElementAt(rowIndex);

        if(el != null) {
            el.touch();
            return el.ioEntry;
        }

        return null;
    }
    
    public Element addElement(Long currentRequestId, IOEntry entry) {
        setCurrentRequestId(currentRequestId);

        synchronized (this) {
            if(cache.containsKey(entry.getRowNum())) {
                return cache.get(entry.getRowNum());
            }
        }

        Element el = new Element(entry);
        ensureCapacitySize();
        cache.put(el.rowNum, el);

        return el;
    }

    private void ensureCapacitySize() {
        synchronized (this) {
            if(cache.size() >= MAX_CACHE_SIZE) {
                List<Element> elements = new ArrayList<Element>(cache.values());
                Element min = null;

                for(Element element : elements) {
                    if(min == null) {
                        min = element;
                        continue;
                    }

                    if(min.lastUsed > element.lastUsed) {
                        min = element;
                    }
                }

                if(min != null) {
                    cache.remove(min.rowNum);
                }
            }
        }
    }

    public static class Element {

        protected IOEntry ioEntry;

        protected Long rowNum;

        protected String shortName;

        protected String message;

        protected String elapse;
        
        protected Long lastUsed;

        public Element(IOEntry ioEntry) {
            this.ioEntry = ioEntry;
            Entry entry = ioEntry.getEntry();
            this.elapse = getElapsePrettyPrint(entry);
            this.message = getGroup(entry) + getMessage(entry);
            this.rowNum = ioEntry.getRowNum();
            this.shortName = PreferenceProviderManager.INSTANCE.getShortName(entry);
            lastUsed = System.currentTimeMillis();
        }

        public void touch() {
            lastUsed = System.currentTimeMillis();
        }

        private String getMessage(Entry entry) {
            return MessageProviderManager.INSTANCE.getMessage(entry);
        }

        private String getElapsePrettyPrint(Entry entry) {
            if(!ElapseTimeAware.class.isInstance(entry)) {
                return null;
            }

            ElapseTimeAware aware = ((ElapseTimeAware) entry);
            if(aware.getElapseTime() == null) {
                return null;
            }

            return aware.getElapseTime().prettyPrint();
        }

        private String getGroup(Entry entry) {
            if(entry.getGroup() == null) {
                return "";
            }

            if(CollectionUtils.isNotEmpty(entry.getGroup().getGroups())) {
                return "[" + entry.getGroup().getGroups().iterator().next() + "] ";
            }

            return "";
        }
    }
}