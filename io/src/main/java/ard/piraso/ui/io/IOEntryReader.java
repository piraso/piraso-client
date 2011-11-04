/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import ard.piraso.api.io.EntryReadEvent;
import ard.piraso.api.io.EntryReadListener;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;

/**
 *
 * @author adleon
 */
public class IOEntryReader implements EntryReadListener {
    
    private static final Logger LOG = Logger.getLogger(IOEntryReader.class.getName());
    
    private static final int DEFAULT_PAGE_SIZE = 200;
    
    private IOEntryManager manager;
    
    private int pageSize;
    
    private IOEntrySource source;
    
    private long lastEntryTime;
    
    private List<IOEntryListener> listeners = Collections.synchronizedList(new LinkedList<IOEntryListener>());

    public IOEntryReader(IOEntrySource source) {
        this(DEFAULT_PAGE_SIZE, source);
    }
    
    public IOEntryReader(int pageSize, IOEntrySource source) {
        Validate.notNull(source, "Source should not be null.");
        
        this.pageSize = pageSize;
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
            manager = new IOEntryManager(source.getId(), pageSize);
        }
        
        try {
            IOEntry entry = manager.addEntry(evt);
            
            fireEntryReadEvent(new IOEntryEvent(this, entry));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public void fireEntryReadEvent(IOEntryEvent evt) {
        List<IOEntryListener> tmp = new ArrayList<IOEntryListener>(listeners);
        for(IOEntryListener listener : tmp) {
            listener.receviedEntry(evt);
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
