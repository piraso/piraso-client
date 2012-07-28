/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
