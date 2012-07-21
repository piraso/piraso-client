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
package ard.piraso.ui.base.provider;

import ard.piraso.api.entry.*;
import ard.piraso.ui.api.EntryViewProvider;
import ard.piraso.ui.base.views.*;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;

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
