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

package ard.piraso.ui.io.util;

import ard.piraso.api.IDGenerator;
import ard.piraso.api.entry.RequestEntry;
import ard.piraso.api.entry.ResponseEntry;
import ard.piraso.api.io.EntryReadEvent;
import ard.piraso.ui.io.IOEntry;
import ard.piraso.ui.io.IOEntryVisitor;
import ard.piraso.ui.io.IOPageEntries;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adleon
 */
public class IOEntryRequest {
    
    private static final Logger LOG = Logger.getLogger(IOEntryRequest.class.getName());
    
    private Long id;
    
    private File dir;
    
    private IDGenerator generator;
        
    private int count;
    
    private ObjectMapper mapper;
    
    private IOEntry request;
    
    private IOEntry response;
    
    public IOEntryRequest(File parent, Long id) {
        this.id = id;        
        this.dir = IOEntryUtils.createRequestDirectory(parent, id);
        this.generator = new IDGenerator();
        
        mapper = new ObjectMapper();
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        LOG.log(Level.INFO, "IO Request with id '{0}' created in folder '{1}'", 
                new Object[] {id, parent.getAbsolutePath()});
    }
    
    public void visit(IOEntryVisitor visitor) throws IOException {
        for(File file : dir.listFiles()) {
            visitor.visit(readEntry(file));
        }
    }
    
    public synchronized IOEntry addEntry(EntryReadEvent evt) throws IOException {
        IOEntrySerializable serializable = new IOEntrySerializable(evt);
        
        serializable.setRowNum(generator.next());
        
        IOEntry ioEntry = new IOEntry(serializable, evt.getEntry());
        
        if(RequestEntry.class.isInstance(evt.getEntry())) {
            request = ioEntry;
        } else if(ResponseEntry.class.isInstance(evt.getEntry())) {
            response = ioEntry;
        }
        
        File entryFile = IOEntryUtils.createEntryFile(dir, serializable.getRowNum());
        
        mapper.writeValue(entryFile, serializable);        
        count++;
        
        return ioEntry;
    }
    
    private int getAdjustedPage(int page, int pageSize) {
        // auto adjust page
        if(page < 0) {
            page = 0;
        }
        
        if(page >= getTotalPages(pageSize)) {
            page = getTotalPages(pageSize);
        }        
        
        return page;
    }
    
    public IOPageEntries getPage(int page, int pageSize) throws IOException {
        page = getAdjustedPage(page, pageSize);
        int startRowNum = page * pageSize;
        int endRowNum = startRowNum + pageSize;
        
        if(endRowNum > getTotalEntries()) {
            endRowNum = getTotalEntries();
        }
        
        List<IOEntry> enties = new ArrayList<IOEntry>(pageSize);
        
        for(int i = startRowNum; i < endRowNum; i++) {
            File entryFile = IOEntryUtils.createEntryFile(dir, Long.valueOf(i));
            
            // this should never happen unless someone manually alter the
            // files generated by piraso ui
            if(!entryFile.isFile()) {
                throw new IllegalStateException(String.format("Entry file '%s' not a valid file.", entryFile.getAbsolutePath()));
            }
            
            enties.add(readEntry(entryFile));
        }
        
        return new IOPageEntries(page, enties);
    }

    public IOEntry get(int rowNum) throws IOException {
        File entryFile = IOEntryUtils.createEntryFile(dir, Long.valueOf(rowNum));

        // this should never happen unless someone manually alter the
        // files generated by piraso ui
        if(!entryFile.isFile()) {
            throw new IllegalStateException(String.format("Entry file '%s' not a valid file.", entryFile.getAbsolutePath()));
        }

        return readEntry(entryFile);
    }
    
    private IOEntry readEntry(File entryFile) throws IOException {
        IOEntrySerializable wrapper = mapper.readValue(entryFile, IOEntrySerializable.class);
        
        return new IOEntry(wrapper);
    }

    public Long getId() {
        return id;
    }

    public IOEntry getRequest() {
        return request;
    }

    public IOEntry getResponse() {
        return response;
    }

    public File getDir() {
        return dir;
    }
    
    public int getTotalPages(int pageSize) {
        return Double.valueOf(Math.ceil((double) getTotalEntries() / (double) pageSize)).intValue();
    }
    
    public int getTotalEntries() {
        return count;
    }
    
    public boolean isCompleted() {
        return response != null;
    }
}
