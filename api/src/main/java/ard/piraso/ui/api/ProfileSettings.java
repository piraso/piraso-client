package ard.piraso.ui.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Profile settings
 */
public class ProfileSettings {
    private List<ProfileModel> models = new ArrayList<ProfileModel>();

    public void add(ProfileModel model) {
        models.add(model);
    }

    public List<ProfileModel> getModels() {
        return models;
    }

    public void setModels(List<ProfileModel> models) {
        this.models = models;
    }
}
