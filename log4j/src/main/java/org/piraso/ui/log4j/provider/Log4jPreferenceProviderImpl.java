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

package org.piraso.ui.log4j.provider;

import org.piraso.api.entry.Entry;
import org.piraso.api.log4j.Log4jEntry;
import org.piraso.api.log4j.Log4jPreferenceEnum;
import org.piraso.ui.api.NCPreferenceProperty;
import org.piraso.ui.api.PPFactory;
import org.piraso.ui.api.PreferenceProperty;
import org.piraso.ui.api.PreferenceProvider;
import org.piraso.ui.log4j.SingleModelManagers;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.*;

/**
 * Provides log4j preferences.
 */
@ServiceProvider(service=PreferenceProvider.class)
public class Log4jPreferenceProviderImpl implements PreferenceProvider {
    
    private Map<String, String> mapping;

    @Override
    public Integer getOrder() {
        return 2;
    }

    @Override
    public String getName() {
        return getMessage("log4j.name");
    }

    @Override
    public String getShortName() {
        return getMessage("log4j.name.short");
    }

    @Override
    public boolean isHorizontalChildLayout() {
        return true;
    }

    @Override
    public boolean isPreviewLastChildOnly() {
        return true;
    }

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        Log4jPreferencesModel model = SingleModelManagers.LOG4J_PREFERENCES.get();

        mapping = new HashMap<String, String>();
        List<PreferenceProperty> result = new LinkedList<PreferenceProperty>();

        PreferenceProperty log4jEnabled = PPFactory.createEntry(Log4jEntry.class, Log4jPreferenceEnum.LOG4J_ENABLED.getPropertyName(), Boolean.class);
        result.add(log4jEnabled);

        for(Log4jPreferencesModel.Child child : model.getPreferences()) {
            String regex = child.getLogger();

            mapping.put("log4j." + regex, child.getDescription());
            NCPreferenceProperty pp = PPFactory.createEntry(Log4jEntry.class, "log4j." + regex, Boolean.class);
            pp.setParent(true);
            pp.addDependents(log4jEnabled);
            result.add(pp);

            List<NCPreferenceProperty> levels = new ArrayList<NCPreferenceProperty>(6);
            for(String level : new String[] {"fatal", "error", "warn", "info", "debug", "trace", "all"}) {
                mapping.put("log4j." + regex + ".*." + level.toUpperCase(), getMessage("log4j." + level));

                NCPreferenceProperty pplevel = PPFactory.createEntry(Log4jEntry.class, "log4j." + regex + ".*." + level.toUpperCase(), Boolean.class);
                pplevel.addDependents(log4jEnabled);
                pplevel.setChild(true);
                pplevel.addDependents(levels.toArray(new NCPreferenceProperty[levels.size()]));

                levels.add(pplevel);

                pplevel.addDependents(pp);
                pp.addOptionalDependents(pplevel);

                result.add(pplevel);
            }
        }


        return result;
    }

    @Override
    public String getMessage(String name) {
        if(mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            return getMessage(name, null);
        }
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(Log4jPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        if(Log4jEntry.class.isInstance(entry)) {
            return ((Log4jEntry) entry).getLogLevel();
        }

        return entry.getLevel();
    }
}
