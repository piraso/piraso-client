/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io.impl;

import ard.piraso.api.io.EntryReadListener;
import ard.piraso.api.io.PirasoEntryReader;
import ard.piraso.ui.io.IOEntrySource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author adleon
 */
public class FileEntrySource implements IOEntrySource {
    private static final Logger LOG = Logger.getLogger(FileEntrySource.class.getName());
        
    private PirasoEntryReader reader;
    
    private boolean stopped;
    
    public FileEntrySource(File source) throws FileNotFoundException, IOException {
        reader = new PirasoEntryReader(new GZIPInputStream(new FileInputStream(source)));
    }

    @Override
    public void start() {
        try {
            reader.start();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            stopped = true;
        }
    }

    @Override
    public void stop() {
        reader.stop();
    }

    @Override
    public String getId() {
        return reader.getId();
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public List<EntryReadListener> getListeners() {
        return reader.getListeners();
    }

    @Override
    public void addListener(EntryReadListener listener) {
        reader.addListener(listener);
    }

    @Override
    public void removeListener(EntryReadListener listener) {
        reader.removeListener(listener);
    }

    @Override
    public void clearListeners() {
        reader.clearListeners();
    }

    @Override
    public String getWatchedAddr() {
        return reader.getWatchedAddr();
    }

}
