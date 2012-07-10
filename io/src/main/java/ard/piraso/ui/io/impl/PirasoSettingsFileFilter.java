package ard.piraso.ui.io.impl;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * piraso settings filter
 */
public class PirasoSettingsFileFilter extends FileFilter {

    public static final String EXTENSION = "prz";

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(EXTENSION);
    }

    @Override
    public String getDescription() {
        return "Piraso Settings File (prz)";
    }
}
