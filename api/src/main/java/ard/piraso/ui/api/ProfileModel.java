package ard.piraso.ui.api;

import java.util.Set;

/**
 * Profile model
 */
public class ProfileModel implements WithNameModel {
    private String name;

    private String desc;

    private Set<String> monitors;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<String> getMonitors() {
        return monitors;
    }

    public void setMonitors(Set<String> monitors) {
        this.monitors = monitors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
