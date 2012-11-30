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

package org.piraso.ui.log4j;

import org.piraso.ui.api.manager.SingleModelManager;
import org.piraso.ui.api.manager.SingleModelManagerImpl;
import org.piraso.ui.log4j.provider.Log4jPreferencesModel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * single model managers
 */
public interface SingleModelManagers {

    public static final Log4jPreferencesModel DEFAULT = new Log4jPreferencesModel() {{
        add("com", "[GENERAL] com");
        add("org", "[GENERAL] org");
        add("org.springframework", "[THIRD-PARTY] org.springframework");
        add("org.hibernate", "[THIRD-PARTY] org.hibernate");
    }};

    public static final FileObject LOG4J_FILTER_OBJECT = FileUtil.getConfigRoot().getFileObject("Log4jPreferences");

    public static final SingleModelManager<Log4jPreferencesModel> LOG4J_PREFERENCES = new SingleModelManagerImpl<Log4jPreferencesModel>(LOG4J_FILTER_OBJECT, Log4jPreferencesModel.class, DEFAULT);
}
