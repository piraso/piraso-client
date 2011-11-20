package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

/**
 * Regular expression comparison preference property.
 */
public class RXPreferenceProperty extends NCPreferenceProperty {

    public RXPreferenceProperty(String name, Class type) {
        super(name, type);
    }

    public RXPreferenceProperty(String name, Class type, boolean defaultValue) {
        super(name, type, defaultValue);
    }

    @Override
    public boolean isApplicable(Entry entry) {
        if(super.isApplicable(entry)) {
            return true;
        }

        return entry.getLevel().matches(name);
    }
}
