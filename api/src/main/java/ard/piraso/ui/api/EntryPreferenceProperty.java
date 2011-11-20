package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

/**
 * Test for entry type
 */
public class EntryPreferenceProperty extends NCPreferenceProperty {

    private Class<? extends Entry> entryClass;

    public EntryPreferenceProperty(Class<? extends Entry> entryClass, String name, Class type) {
        super(name, type);
        this.entryClass = entryClass;
    }

    public EntryPreferenceProperty(Class<? extends Entry> entryClass, String name, Class type, boolean defaultValue) {
        super(name, type, defaultValue);
        this.entryClass = entryClass;
    }

    @Override
    public boolean isApplicable(Entry entry) {
        return entryClass.isInstance(entry);
    }
}
