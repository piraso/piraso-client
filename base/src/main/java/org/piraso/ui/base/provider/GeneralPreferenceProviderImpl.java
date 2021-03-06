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

package org.piraso.ui.base.provider;

import org.piraso.api.GeneralPreferenceEnum;
import org.piraso.api.entry.Entry;
import org.piraso.ui.api.PPFactory;
import org.piraso.ui.api.PreferenceProperty;
import org.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service=PreferenceProvider.class)
public class GeneralPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        List<PreferenceProperty> preferences = new ArrayList<PreferenceProperty>(3);
        
        preferences.add(PPFactory.createNC(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName(), Boolean.class));
        preferences.add(PPFactory.createNC(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), Boolean.class, true));
        preferences.add(PPFactory.createNC(GeneralPreferenceEnum.NO_REQUEST_CONTEXT.getPropertyName(), Boolean.class, false));

        return preferences;
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(GeneralPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        return getMessage(entry.getLevel() + ".short");
    }

    @Override
    public Integer getOrder() {
        return 0;
    }

    @Override
    public String getName() {
        return getMessage("general.name");
    }

    @Override
    public String getShortName() {
        return getMessage("general.name.short");
    }

    @Override
    public boolean isHorizontalChildLayout() {
        return false;
    }

    @Override
    public boolean isPreviewLastChildOnly() {
        return false;
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
