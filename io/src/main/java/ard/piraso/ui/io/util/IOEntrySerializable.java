/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io.util;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.io.EntryReadEvent;
import java.io.IOException;
import java.util.Date;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author adleon
 */
public class IOEntrySerializable {
   
    private Class classType;
        
    private Long id;
    
    private Date date;
    
    private String entryValue;
    
    private ObjectMapper mapper;
    
    private Long rowNum;
    
    public IOEntrySerializable(EntryReadEvent evt) throws IOException {
        this.id = evt.getRequestId();
        this.date = evt.getDate();
        this.classType = evt.getEntry().getClass();
        
        mapper = new ObjectMapper();
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        this.entryValue = mapper.writeValueAsString(evt.getEntry());
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Entry toEntry() throws IOException {
        return (Entry) mapper.readValue(entryValue, classType);
    }
}
