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

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.HttpRequestEntry;
import ard.piraso.api.entry.HttpResponseEntry;
import ard.piraso.api.entry.MethodCallEntry;
import ard.piraso.ui.api.EntryViewProvider;
import ard.piraso.ui.base.MethodViewTopComponent;
import ard.piraso.ui.base.views.RequestViewTopComponent;
import ard.piraso.ui.base.views.ResponseViewTopComponent;
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
        if(MethodCallEntry.class.isInstance(entry)) {
            return MethodViewTopComponent.class;
        } else if(HttpRequestEntry.class.isInstance(entry)) {
            return RequestViewTopComponent.class;
        } else if(HttpResponseEntry.class.isInstance(entry)) {
            return ResponseViewTopComponent.class;
        }

        return null;
    }
    
}
