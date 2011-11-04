/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io.impl;

import ard.piraso.api.io.PirasoEntryWriter;
import ard.piraso.ui.io.IOEntry;
import ard.piraso.ui.io.IOEntryManager;
import ard.piraso.ui.io.IOEntryReader;
import ard.piraso.ui.io.IOEntryVisitor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author adleon
 */
public class FileEntrySave implements IOEntryVisitor {
    
    private static final Logger LOG = Logger.getLogger(FileEntrySave.class.getName());
    
    private IOEntryManager manager;
    
    private IOEntryReader reader;
    
    private List<Long> requests;
    
    private PirasoEntryWriter writer;
    
    public FileEntrySave(IOEntryReader reader) {
        this.reader = reader;
        this.manager = reader.getManager();
        this.requests = new ArrayList<Long>();
    }
    
    public void addRequest(Long id) {
        requests.add(id);
    }
    
    public void save(File file) throws IOException, ParserConfigurationException, TransformerConfigurationException {
        FileOutputStream fo = null;
        GZIPOutputStream go = null;
        OutputStreamWriter io = null;
        PrintWriter pw = null;
        
        try {
             fo = new FileOutputStream(file);
             go = new GZIPOutputStream(fo);
             io = new OutputStreamWriter(go);
             pw = new PrintWriter(io, true);
             
             writer = new PirasoEntryWriter(reader.getId(), reader.getWatchedAddr(), pw);
             
             manager.visit(requests, this);
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(pw);
            IOUtils.closeQuietly(io);
            IOUtils.closeQuietly(go);
            IOUtils.closeQuietly(fo);
        }
    }

    @Override
    public void visit(IOEntry entry) {
        try {
            writer.write(entry.getDate(), entry.getEntry());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);

            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }
}
