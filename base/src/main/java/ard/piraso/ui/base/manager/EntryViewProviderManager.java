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

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.EntryViewProvider;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

import java.util.Collection;

/**
 * Manages all {@link EntryViewProvider} service providers.
 */
public class EntryViewProviderManager implements EntryViewProvider {

    public static final EntryViewProviderManager INSTANCE = new EntryViewProviderManager();

    private EntryViewProviderManager() {}

    @Override
    public Class<? extends TopComponent> getViewClass(Entry entry) {
        Collection<? extends EntryViewProvider> providers = Lookup.getDefault().lookupAll(EntryViewProvider.class);

        for(EntryViewProvider provider : providers) {
            Class<? extends TopComponent> viewClass = provider.getViewClass(entry);

            if(viewClass != null) {
                return viewClass;
            }
        }

        return null;
    }
}
