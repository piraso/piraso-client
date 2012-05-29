package ard.piraso.ui.base.manager;

import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.ProfileModel;
import ard.piraso.ui.api.manager.ModelManager;
import ard.piraso.ui.api.manager.ModelManagerImpl;
import org.openide.filesystems.FileUtil;

/**
 * All model managers
 */
public interface ModelManagers {
    public static final ModelManager<NewContextMonitorModel> MONITORS = new ModelManagerImpl<NewContextMonitorModel>(FileUtil.getConfigRoot().getFileObject("Configured_Monitors"), NewContextMonitorModel.class);

    public static final ModelManager<ProfileModel> PROFILES = new ModelManagerImpl<ProfileModel>(FileUtil.getConfigRoot().getFileObject("Configured_Profiles"), ProfileModel.class);
}
