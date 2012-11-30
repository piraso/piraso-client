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

import org.piraso.api.JacksonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.filesystems.FileObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation for {@link SingleModelManager}.
 */
public class SingleModelManagerImpl<T> extends AbstractModelManager implements SingleModelManager<T> {

    private static final Logger LOG = Logger.getLogger(ModelManagerImpl.class.getName());

    private static final ObjectMapper MAPPER = JacksonUtils.MAPPER;

    private final FileObject persistent;

    private final Class<T> clazz;

    private T defaultValue;

    public SingleModelManagerImpl(FileObject persistent, Class<T> clazz) {
        this(persistent, clazz, null);
    }

    public SingleModelManagerImpl(FileObject persistent, Class<T> clazz, T defaultValue) {
        this.clazz = clazz;
        this.persistent = persistent;
        this.defaultValue = defaultValue;
    }

    @Override
    public void save(T item) {
        try {
            persistent.setAttribute("value", MAPPER.writeValueAsString(item));

            fireOnChangeEvent();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error while writing json", e);
        }
    }

    @Override
    public T get() {
        try {
            if(persistent.getAttribute("value") != null) {
                return MAPPER.readValue(String.valueOf(persistent.getAttribute("value")), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
