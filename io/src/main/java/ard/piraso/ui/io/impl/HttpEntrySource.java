/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io.impl;

import ard.piraso.api.Preferences;
import ard.piraso.api.io.EntryReadListener;
import ard.piraso.client.net.HttpPirasoEntryReader;
import ard.piraso.ui.io.IOEntrySource;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author adleon
 */
public class HttpEntrySource implements IOEntrySource {
    
    private static final Logger LOG = Logger.getLogger(HttpEntrySource.class.getName());
        
    private HttpPirasoEntryReader reader;
    
    private HttpClient client;
    
    private HttpContext context;
    
    private boolean stopped;
    
    public HttpEntrySource(Preferences preferences, String uri) {
        this(preferences, uri, null);
    }
    
    public HttpEntrySource(Preferences preferences, String uri, String watchedAddr) {
        this.client = new DefaultHttpClient();
        this.context = new BasicHttpContext();        
        this.reader = new HttpPirasoEntryReader(client, context);
        
        reader.setUri(uri);
        reader.getStartHandler().setPreferences(preferences);
        
        if(watchedAddr != null) {
            reader.getStartHandler().setWatchedAddr(watchedAddr);
        }
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
        try {
            reader.stop();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    public String getId() {
        return reader.getStartHandler().getId();
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public List<EntryReadListener> getListeners() {
        return reader.getStartHandler().getListeners();
    }

    @Override
    public void addListener(EntryReadListener listener) {
        reader.getStartHandler().addListener(listener);
    }

    @Override
    public void removeListener(EntryReadListener listener) {
        reader.getStartHandler().removeListener(listener);
    }

    @Override
    public void clearListeners() {
        reader.getStartHandler().clearListeners();
    }  
    
    @Override
    public String getWatchedAddr() {
        return reader.getStartHandler().getWatchedAddr();
    }
}
