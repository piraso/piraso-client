/*
 * Copyright
 */
package ard.piraso.ui.base;

import ard.piraso.ui.base.model.NewContextMonitorModel;
import ard.piraso.ui.io.IOEntryReader;
import ard.piraso.ui.io.IOEntrySource;
import ard.piraso.ui.io.impl.FileEntrySource;
import ard.piraso.ui.io.impl.HttpEntrySource;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author adleon
 */
public final class ContextMonitorDispatcher {
    public static void forward(NewContextMonitorModel model) {
        forward(new HttpEntrySource(model.getPreferences(), model.getLoggingUrl(), model.getWatchedAddr()));        
    }
    
    public static void forward(File file) throws IOException {
        forward(new FileEntrySource(file));
    }
    
    public static void forward(IOEntrySource source) {
        IOEntryReader reader = new IOEntryReader(source);
        ContextMonitorTopComponent editor = new ContextMonitorTopComponent(reader);
        
        editor.open();
        editor.requestActive();            
    }
}
