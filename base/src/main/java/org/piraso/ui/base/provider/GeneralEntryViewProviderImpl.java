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

import org.piraso.api.entry.*;
import org.piraso.ui.api.EntryViewProvider;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;
import org.piraso.ui.base.views.*;

/**
 *
 * @author adeleon
 */
@ServiceProvider(service=EntryViewProvider.class)
public class GeneralEntryViewProviderImpl implements EntryViewProvider {

    @Override
    public Class<? extends TopComponent> getViewClass(Entry entry) {
        if(MessageEntry.class.isInstance(entry)) {
            return MessageEntryViewTopComponent.class;
        } else if(MethodCallEntry.class.isInstance(entry)) {
            return MethodCallViewTopComponent.class;
        } else if(HttpRequestEntry.class.isInstance(entry)) {
            return HttpRequestViewTopComponent.class;
        } else if(HttpResponseEntry.class.isInstance(entry)) {
            return HttpResponseViewTopComponent.class;
        } else if(JSONEntry.class.isInstance(entry)) {
            return JSONViewTopComponent.class;
        }

        return null;
    }
    
}
