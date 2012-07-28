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

package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;

/**
 * Test for entry type
 */
public class EntryPreferenceProperty extends NCPreferenceProperty {

    private Class<? extends Entry> entryClass;

    public EntryPreferenceProperty(Class<? extends Entry> entryClass, String name, Class type) {
        super(name, type);
        this.entryClass = entryClass;
    }

    public EntryPreferenceProperty(Class<? extends Entry> entryClass, String name, Class type, boolean defaultValue) {
        super(name, type, defaultValue);
        this.entryClass = entryClass;
    }

    @Override
    public boolean isApplicable(Entry entry) {
        return entryClass.isInstance(entry);
    }
}
