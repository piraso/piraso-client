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

package ard.piraso.ui.api.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.EntryTabView;
import ard.piraso.ui.api.EntryTabViewProvider;
import ard.piraso.ui.api.views.AbstractTabView;
import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class AbstractEntryTabViewProvider<T extends AbstractTabView> implements EntryTabViewProvider {

    private Map<Class, T> views = new HashMap<Class, T>(4);

    protected AbstractEntryTabViewProvider() {
    }
    
    public void add(Class clazz, T view) {
        views.put(clazz, view);
    }
    
    public T getView(Class clazz) {
        return views.get(clazz);
    }
    
    public boolean contains(Class clazz) {
        return views.containsKey(clazz);
    }

    protected abstract boolean isSupported(Entry entry);

    protected abstract T createView(Entry entry);

    @Override
    @SuppressWarnings("unchecked")
    public EntryTabView getTabView(final Class owner, Entry entry) {
        Validate.notNull(owner);

        if(!isSupported(entry)) {
            return null;
        }

        synchronized (owner) {
            if(contains(owner)) {
                T view = getView(owner);
                view.refreshView(entry);
                
                return new EntryTabView(view);
            }

            T view = createView(entry);
            add(owner, view);

            return new EntryTabView(view);
        }
    }
}
