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

package org.piraso.ui.sql.provider;

import org.piraso.api.io.AbstractPirasoObjectLoader;
import org.piraso.api.io.PirasoObjectLoader;
import org.piraso.ui.api.PirasoObjectLoaderProvider;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Provides entry loader
 */
@ServiceProvider(service=PirasoObjectLoaderProvider.class)
public class SQLObjectLoaderProviderImpl implements PirasoObjectLoaderProvider {

    @Override
    public PirasoObjectLoader getLoader() {
        return new PirasoEntryLoaderImpl();
    }

    private class PirasoEntryLoaderImpl extends AbstractPirasoObjectLoader {
        @Override
        @SuppressWarnings("unchecked")
        public Object loadObject(String className, String content) throws IOException, ClassNotFoundException {
            ClassLoader loader = SQLObjectLoaderProviderImpl.class.getClassLoader();

            Class clazz = loader.loadClass(className);
            return mapper.readValue(content, clazz);
        }
    }
}
