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

package org.piraso.ui.api.provider;

import org.piraso.api.entry.Entry;
import org.piraso.api.entry.ThrowableAwareEntry;
import org.piraso.ui.api.EntryTabViewProvider;
import org.piraso.ui.api.views.ExceptionTabView;
import org.openide.util.lookup.ServiceProvider;

/**
 * Exception entry provider.
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class ExceptionEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<ExceptionTabView> {

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
