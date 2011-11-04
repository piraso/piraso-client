/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import java.util.EventListener;

/**
 *
 * @author adleon
 */
public interface IOEntryListener extends EventListener {
    
    public void receviedEntry(IOEntryEvent evt);
}
