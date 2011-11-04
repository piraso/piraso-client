/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import java.util.List;

/**
 *
 * @author adleon
 */
public class IOPageEntries {
    private int page;
    
    private List<IOEntry> entries;

    public IOPageEntries(int page, List<IOEntry> entries) {
        this.page = page;
        this.entries = entries;
    }

    public List<IOEntry> getEntries() {
        return entries;
    }

    public int getPage() {
        return page;
    }
}
