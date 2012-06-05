package ard.piraso.ui.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Object entry settings
 */
public class ObjectEntrySettings {
    private Map<String, String> models = new HashMap<String, String>();

    public void add(String key, String model) {
        models.put(key, model);
    }

    public Map<String, String> getModels() {
        return models;
    }

    public void setModels(Map<String, String> models) {
        this.models = models;
    }
}
