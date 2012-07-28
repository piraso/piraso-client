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

package ard.piraso.ui.api.extension;

import ard.piraso.api.entry.Entry;
import ard.piraso.ui.api.manager.ModelEvent;
import ard.piraso.ui.api.manager.ModelOnChangeListener;
import ard.piraso.ui.api.manager.SingleModelManagers;
import org.apache.commons.collections.CollectionUtils;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

import javax.swing.*;
import java.util.Collection;

/**
 * Base class for Entry View Top Component.
 */
public abstract class AbstractEntryViewTopComponent<T extends Entry> extends TopComponent implements LookupListener {

    private final ModelOnChangeListener GENERAL_SETTINGS_LISTENER = new ModelOnChangeListener() {
        @Override
        public void onChange(ModelEvent evt) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    refreshOnSettingsChange();
                }
            });
        }
    };

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

    protected void refreshOnSettingsChange() {
        refreshView();
    }

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
        SingleModelManagers.GENERAL_SETTINGS.addModelOnChangeListener(GENERAL_SETTINGS_LISTENER);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
        result = null;
        SingleModelManagers.GENERAL_SETTINGS.removeModelOnChangeListener(GENERAL_SETTINGS_LISTENER);
    }
}
