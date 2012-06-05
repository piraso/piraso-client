package ard.piraso.ui.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Monitor preferences
 */
public class MonitorSettings {

    private List<NewContextMonitorModel> models = new ArrayList<NewContextMonitorModel>();

    public void add(NewContextMonitorModel model) {
        models.add(model);
    }

    public List<NewContextMonitorModel> getModels() {
        return models;
    }

    public void setModels(List<NewContextMonitorModel> models) {
        this.models = models;
    }
}
