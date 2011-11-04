/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import java.util.EventObject;

/**
 *
 * @author adleon
 */
public class IOEntryEvent extends EventObject {
    
    private IOEntry entry;

    public IOEntryEvent(Object source, IOEntry entry) {
        super(source);
        
        this.entry = entry;
    }

    public IOEntry getEntry() {
        return entry;
    }
}
