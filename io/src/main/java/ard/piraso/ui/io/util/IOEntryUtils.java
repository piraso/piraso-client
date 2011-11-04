/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.io.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author adleon
 */
public final class IOEntryUtils {
    
    private static final Pattern REGEX = Pattern.compile("[a-z0-9\\-_]+(\\d)", Pattern.CASE_INSENSITIVE);
    
    private IOEntryUtils() {}
    
    public static File createRequestDirectory(File baseDir, Long id) {
        return createEntryFolder(baseDir, String.valueOf(id));
    }
    
    public static File createBaseDirectory(String id) {
        File userdir = new File(System.getProperty("user.dir"));
        File vcdir = createEntryFolder(userdir, ".piraso");
        File tmpdir = createEntryFolder(vcdir, ".tmp");
        
        File baseDir = createEntryFolder(tmpdir, createBaseName(tmpdir, id));
        
        baseDir.deleteOnExit();
        
        return baseDir;
    }
    
    private static String createBaseName(File dir, String id) {        
        if(!new File(dir, id).isDirectory()) {
            return id;
        }

        StringBuilder buf = new StringBuilder();
        Matcher matcher = REGEX.matcher(id);

        if(matcher.find()) {
            int start = matcher.start(1);
            int end = matcher.end(1);
            int count = Integer.parseInt(id.substring(start, end));

            buf.append(id.substring(0, start));
            buf.append(String.valueOf(count + 1));
        } else {
            buf.append(id).append("_2");
        }
        
        return createBaseName(dir, buf.toString());
    }
    
    public static File createEntryFile(File parent, Long rowNum) {
        return new File(parent, String.valueOf(rowNum) + ".json");
    }
    
    public static File createEntryFolder(File parent, String name) {
        File dir = new File(parent, name);

        if(!dir.isDirectory()) {
            dir.mkdir();
        }

        return dir;
    }
}
