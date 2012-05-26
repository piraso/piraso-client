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
import ard.piraso.ui.api.MessageProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup;

/**
 * Provides messages for entries.
 */
public final class MessageProviderManager {

    public static final MessageProviderManager INSTANCE = new MessageProviderManager();
    
    private Map<Class, MessageProvider> cache;
    
    private MessageProvider defaultProvider;

    private MessageProviderManager() {
        cache = new HashMap<Class, MessageProvider>();
        
        defaultProvider = new DefaultProviderImpl();
    }

    public String getMessage(Entry entry) {
        if(entry == null) {
            return null;
        }
        
        if(cache.containsKey(entry.getClass())) {
            return cache.get(entry.getClass()).toMessage(entry);
        }
        
        Collection<? extends MessageProvider> providers = Lookup.getDefault().lookupAll(MessageProvider.class);

        for(MessageProvider provider : providers) {
            if(provider.isSupported(entry)) {
                cache.put(entry.getClass(), provider);
                
                return provider.toMessage(entry);
            }
        }
        
        cache.put(entry.getClass(), defaultProvider);

        return defaultProvider.toMessage(entry);
    }
    
    private class DefaultProviderImpl implements MessageProvider {

        @Override
        public boolean isSupported(Entry entry) {
            return true;
        }

        @Override
        public String toMessage(Entry entry) {
            return "DEFAULT: " + entry.getClass().toString();
        }        
    }
}
