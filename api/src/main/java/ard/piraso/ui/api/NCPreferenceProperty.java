package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

/**
 * Name comparison preference property
 */
public class NCPreferenceProperty implements PreferenceProperty {
    protected String name;

    protected Class type;

    protected boolean defaultValue;

    public NCPreferenceProperty(String name, Class type) {
        this(name, type, false);
    }

    public NCPreferenceProperty(String name, Class type, boolean defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isApplicable(Entry entry) {
        return name.equals(entry.getLevel());
    }
}
