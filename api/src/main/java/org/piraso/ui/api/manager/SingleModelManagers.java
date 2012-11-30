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

package org.piraso.ui.api.manager;

import org.piraso.ui.api.GeneralSettingsModel;
import org.piraso.ui.api.SVNSettingsUpdateModel;
import org.piraso.ui.api.StackTraceFilterModel;
import org.piraso.ui.api.WorkingSetSettings;
import org.openide.filesystems.FileUtil;

/**
 * single model managers
 */
public interface SingleModelManagers {

    public static final StackTraceFilterModel DEFAULT_STACK_TRACE_FILTER = new StackTraceFilterModel() {{
        add("com.*", true);
        add("java.*", false);
        add("javax.*", false);
        add("org.apache.jsp.*", true);
    }};

    public static final SingleModelManager<StackTraceFilterModel> STACK_TRACE_FILTER = new SingleModelManagerImpl<StackTraceFilterModel>(FileUtil.getConfigRoot().getFileObject("StackTraceFilters"), StackTraceFilterModel.class, DEFAULT_STACK_TRACE_FILTER);

    public static final SingleModelManager<WorkingSetSettings> WORKING_SET = new SingleModelManagerImpl<WorkingSetSettings>(FileUtil.getConfigRoot().getFileObject("WorkingSetSettings"), WorkingSetSettings.class, new WorkingSetSettings());

    public static final SingleModelManager<GeneralSettingsModel> GENERAL_SETTINGS = new SingleModelManagerImpl<GeneralSettingsModel>(FileUtil.getConfigRoot().getFileObject("GeneralSettings"), GeneralSettingsModel.class, new GeneralSettingsModel());

    public static final SingleModelManager<SVNSettingsUpdateModel> SVN_SETTINGS = new SingleModelManagerImpl<SVNSettingsUpdateModel>(FileUtil.getConfigRoot().getFileObject("SVNSyncSettings"), SVNSettingsUpdateModel.class, new SVNSettingsUpdateModel());
}
