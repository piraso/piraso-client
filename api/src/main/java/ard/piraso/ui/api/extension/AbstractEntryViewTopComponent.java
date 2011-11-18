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

package ard.piraso.ui.api.extension;

import ard.piraso.api.entry.Entry;
import org.apache.commons.collections.CollectionUtils;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

import java.util.Collection;

/**
 * Base class for Entry View Top Component.
 */
public abstract class AbstractEntryViewTopComponent<T extends Entry> extends TopComponent implements LookupListener {
    protected Lookup.Result result = null;

    protected T currentEntry;

    protected Class<T> typeClass;

    protected AbstractEntryViewTopComponent(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * Refresh the view since new entry was available.
     */
    protected abstract void refreshView();

    @Override
    @SuppressWarnings("unchecked")
    public void resultChanged(LookupEvent evt) {
        Lookup.Result<T> r = (Lookup.Result<T>) evt.getSource();
        Collection<? extends T> entries = r.allInstances();

        if(CollectionUtils.isNotEmpty(entries)) {
            currentEntry = entries.iterator().next();
            refreshView();
        }
    }

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(typeClass);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
        result = null;
    }
}
