/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.base;

import ard.piraso.api.GeneralPreferenceEnum;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service=PreferenceProvider.class)
public class GeneralPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<PreferenceProperty> getPreferences() {
        List<PreferenceProperty> preferences = new ArrayList<PreferenceProperty>(2);
        
        preferences.add(new PreferenceProperty(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName(), Boolean.class));
        preferences.add(new PreferenceProperty(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), Boolean.class, true));
        
        return preferences;
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(GeneralPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getName() {
        return getMessage("general.name");
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
