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
import ard.piraso.ui.io.util.IOEntryRequest;
import ard.piraso.ui.io.util.IOEntryUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adleon
 */
public class IOEntryManager {
    private static final Logger LOG = Logger.getLogger(IOEntryManager.class.getName());
    
    private String id;
    
    private int pageSize;
    
    private File dir;
    
    private Map<Long, IOEntryRequest> requests;
    
    public IOEntryManager(String id, int pageSize) {
        this.id = id;
        this.pageSize = pageSize;
        this.dir = IOEntryUtils.createBaseDirectory(id);
        this.requests = new LinkedHashMap<Long, IOEntryRequest>();
        
        LOG.log(Level.INFO, "Using baseFolder: {0}", dir.getAbsoluteFile());
    }
    
    private IOEntryRequest createOrGetRequest(Long requestId) {
        IOEntryRequest request = requests.get(requestId);
        
        if(request == null) {
            request = new IOEntryRequest(dir, requestId);
            requests.put(requestId, request);
        }
        
        return request;
    }
    
    public synchronized IOEntry addEntry(EntryReadEvent evt) throws IOException {
        IOEntryRequest request = createOrGetRequest(evt.getRequestId());
        
        return request.addEntry(evt);
    }

    public String getId() {
        return id;
    }
    
    public void visit(List<Long> requestIds, IOEntryVisitor visitor) throws IOException {
        List<IOEntryRequest> tmp = new ArrayList<IOEntryRequest>(requests.values());
        
        for(IOEntryRequest request : tmp) {
            if(!requestIds.contains(request.getId())) {
                continue;
            }
            
            request.visit(visitor);
        }
    }
    
    public IOPageEntries getPage(Long requestId, int page) throws IOException {
        if(!requests.containsKey(requestId)) {
            throw new IllegalArgumentException(String.format("Request with id '%d' not found.", requestId));
        }
        
        return createOrGetRequest(requestId).getPage(page, pageSize);
    }
    
    public int getTotalPages(Long requestId) {
        if(!requests.containsKey(requestId)) {
            throw new IllegalArgumentException(String.format("Request with id '%d' not found.", requestId));
        }
        
        return createOrGetRequest(requestId).getTotalPages(pageSize);
    }
    
    public int getTotalEntries(Long requestId) {
        if(!requests.containsKey(requestId)) {
            throw new IllegalArgumentException(String.format("Request with id '%d' not found.", requestId));
        }
        
        return createOrGetRequest(requestId).getTotalEntries();
    }
}
