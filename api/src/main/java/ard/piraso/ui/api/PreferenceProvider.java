/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.api;

import java.util.List;

/**
 *
 * @author adleon
 */
public interface PreferenceProvider {
    
    public String getName();
    
    public List<PreferenceProperty> getPreferences();
    
    public String getMessage(String name);
    
    public String getMessage(String name, Object[] args);
}
