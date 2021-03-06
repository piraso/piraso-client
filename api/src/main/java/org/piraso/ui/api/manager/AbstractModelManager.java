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

package org.piraso.ui.api.manager;

import javax.swing.event.EventListenerList;

/**
 * model managers
 */
public abstract class AbstractModelManager implements BaseModelManager {

    private EventListenerList listeners = new EventListenerList();

    @Override
    public void addModelOnChangeListener(ModelOnChangeListener listener) {
        listeners.add(ModelOnChangeListener.class, listener);
    }

    public void removeModelOnChangeListener(ModelOnChangeListener listener) {
        listeners.remove(ModelOnChangeListener.class, listener);
    }

    @Override
    public void fireOnChangeEvent() {
        ModelOnChangeListener[] handlers = listeners.getListeners(ModelOnChangeListener.class);

        for(ModelOnChangeListener handler : handlers) {
            handler.onChange(new ModelEvent(this));
        }
    }


}
