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

package org.piraso.ui.api;

import org.piraso.api.entry.Entry;

/**
 * Regular expression comparison preference property.
 */
public class RXPreferenceProperty extends NCPreferenceProperty {

    public RXPreferenceProperty(String name, Class type) {
        super(name, type);
    }

    public RXPreferenceProperty(String name, Class type, boolean defaultValue) {
        super(name, type, defaultValue);
    }

    @Override
    public boolean isApplicable(Entry entry) {
        if(super.isApplicable(entry)) {
            return true;
        }

        return entry.getLevel().matches(name);
    }
}
