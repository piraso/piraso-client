/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import ard.piraso.api.io.EntryReadListener;
import java.util.*;

/**
 *
 * @author adleon
 */
public interface IOEntrySource {
    
    public void start();
    
    public void stop();
    
    public String getId();
    
    public String getWatchedAddr();

    public boolean isStopped();

    public List<EntryReadListener> getListeners();

    public void addListener(EntryReadListener listener);

    public void removeListener(EntryReadListener listener);

    public void clearListeners();
    
}
