/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.api;

/**
 *
 * @author adleon
 */
public class PreferenceProperty {
    
    private String name;
    
    private Class type;
    
    public PreferenceProperty(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }
}
