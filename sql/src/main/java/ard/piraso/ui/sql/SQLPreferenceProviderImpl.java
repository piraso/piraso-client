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

package ard.piraso.ui.sql;

import ard.piraso.api.sql.SQLPreferenceEnum;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adleon
 */
@ServiceProvider(service=PreferenceProvider.class)
public class SQLPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<PreferenceProperty> getPreferences() {
        List<PreferenceProperty> properties = new ArrayList<PreferenceProperty>(SQLPreferenceEnum.values().length);
        
        for(SQLPreferenceEnum flag : SQLPreferenceEnum.values()) {
            PreferenceProperty property;
            if(flag.isLevel()) {
                property = new PreferenceProperty(flag.getPropertyName(), Boolean.class);
            } else {
                property = new PreferenceProperty(flag.getPropertyName(), Integer.class);
            }
            
            properties.add(property);
        }
        
        return properties;
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(SQLPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getName() {
        return getMessage("sql.name");
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
    
}
