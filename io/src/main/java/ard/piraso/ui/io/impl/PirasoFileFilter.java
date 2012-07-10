package ard.piraso.ui.io.impl;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * piraso filter
 */
public class PirasoFileFilter extends FileFilter {

    public static final String EXTENSION = "prso";

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(EXTENSION);
    }

    @Override
    public String getDescription() {
        return "Piraso Monitor Instance File (prso)";
    }
}
