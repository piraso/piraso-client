package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

/**
 * Factory for {@link PreferenceProperty}.
 */
public class PPFactory {

    public static NCPreferenceProperty createNC(String name, Class type) {
        return new NCPreferenceProperty(name, type);
    }

    public static NCPreferenceProperty createNC(String name, Class type, boolean defaultValue) {
        return new NCPreferenceProperty(name, type, defaultValue);
    }

    public static RXPreferenceProperty createRX(String name, Class type) {
        return new RXPreferenceProperty(name, type);
    }

    public static RXPreferenceProperty createRX(String name, Class type, boolean defaultValue) {
        return new RXPreferenceProperty(name, type, defaultValue);
    }

    public static EntryPreferenceProperty createEntry(Class<? extends Entry> entryClass, String name, Class type) {
        return new EntryPreferenceProperty(entryClass, name, type);
    }
}
