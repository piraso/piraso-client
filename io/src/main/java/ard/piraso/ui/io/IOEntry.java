/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io;

import ard.piraso.ui.io.util.IOEntrySerializable;
import ard.piraso.api.entry.Entry;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author adleon
 */
public class IOEntry {
    
    private Long rowNum;
    
    private Long id;
    
    private Date date;
    
    private Entry entry;
    
    public IOEntry(IOEntrySerializable wrapper) throws IOException {
        this(wrapper, null);
    }
    
    public IOEntry(IOEntrySerializable wrapper, Entry entry) throws IOException {
        this.rowNum = wrapper.getRowNum();
        this.id = wrapper.getId();
        this.date = wrapper.getDate();
        
        if(entry == null) {
            this.entry = wrapper.toEntry();
        } else {
            this.entry = entry;
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }
}
