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

package ard.piraso.ui.base.manager;

import ard.piraso.api.Preferences;
import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.Lookup;

import java.util.*;

/**
 * Manages preference provider.
 */
public final class PreferenceProviderManager {

    public static final PreferenceProviderManager INSTANCE = new PreferenceProviderManager();

    private List<PreferenceProvider> providerCache;

    private PreferenceProviderManager() {
    }

    public List<PreferenceProvider> getProviders() {
        if(providerCache != null) {
            return providerCache;
        }

        Collection<? extends PreferenceProvider> providers = Lookup.getDefault().lookupAll(PreferenceProvider.class);

        providerCache = new ArrayList<PreferenceProvider>(providers);
        Collections.sort(providerCache, new Comparator<PreferenceProvider>() {
            @Override
            public int compare(PreferenceProvider t, PreferenceProvider t1) {
                return t.getOrder().compareTo(t1.getOrder());
            }
        });

        return providerCache;
    }

    public String getShortName(Entry entry) {
        for(PreferenceProvider provider : getProviders()) {
            for(PreferenceProperty property : provider.getPreferences()) {
                if(property.isApplicable(entry)) {
                    return provider.getShortName(entry, property);
                }
            }
        }

        return null;
    }
    
    public Preferences createPreferences() {
        Preferences preferences = new Preferences();
        
        for(PreferenceProvider provider : getProviders()) {
            for(PreferenceProperty property : provider.getPreferences()) {
                if(property.isDefaultValue()) {
                    preferences.addProperty(property.getName(), true);
                }
            }
        }
        
        return preferences;
    }
}
