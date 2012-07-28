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

package ard.piraso.ui.api.manager;

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.EntryTabView;
import ard.piraso.ui.api.EntryTabViewProvider;
import org.openide.util.Lookup;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * EntryTabView provider manager
 */
public enum EntryTabViewProviderManager {
    INSTANCE;

    public List<EntryTabView> getTabView(Class owner, Entry entry) {
        Collection<? extends EntryTabViewProvider> providers = Lookup.getDefault().lookupAll(EntryTabViewProvider.class);
        
        List<EntryTabView> views = new LinkedList<EntryTabView>();

        for(EntryTabViewProvider provider : providers) {
            EntryTabView view = provider.getTabView(owner, entry);

            if(view != null) {
                views.add(view);
            }
        }

        return views;
    }
}
