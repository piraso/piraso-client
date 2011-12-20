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
import ard.piraso.api.entry.ThrowableAwareEntry;
import ard.piraso.ui.api.EntryTabViewProvider;
import ard.piraso.ui.api.views.ExceptionTabView;
import org.openide.util.lookup.ServiceProvider;

/**
 * Exception entry provider.
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class ExceptionEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<ExceptionTabView> {

    public ExceptionEntryTabViewProviderImpl() {
        super("Exception");
    }

    @Override
    protected boolean isSupported(Entry entry) {
        if(!ThrowableAwareEntry.class.isInstance(entry)) {
            return false;
        }

        ThrowableAwareEntry throwable = (ThrowableAwareEntry) entry;
        return throwable.getThrown() != null;

    }

    @Override
    protected ExceptionTabView createView(Entry entry) {
        ThrowableAwareEntry throwable = (ThrowableAwareEntry) entry;

        return new ExceptionTabView(throwable);
    }
}
