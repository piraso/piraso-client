package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Name comparison preference property
 */
public class NCPreferenceProperty implements PreferenceProperty {
    protected String name;

    protected Class type;

    protected boolean defaultValue;

    protected boolean parent;

    protected boolean child;

    protected List<PreferenceProperty> dependents = new LinkedList<PreferenceProperty>();

    public NCPreferenceProperty(String name, Class type) {
        this(name, type, false);
    }

    public NCPreferenceProperty(String name, Class type, boolean defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public void addDependents(PreferenceProperty... properties) {
        dependents.addAll(Arrays.asList(properties));
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

    @Override
    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    @Override
    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    @Override
    public List<PreferenceProperty> getDependents() {
        return dependents;
    }
}
