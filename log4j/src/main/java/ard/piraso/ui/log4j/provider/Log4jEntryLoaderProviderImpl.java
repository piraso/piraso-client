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

package ard.piraso.ui.log4j.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.io.AbstractPirasoEntryLoader;
import ard.piraso.api.io.PirasoEntryLoader;
import ard.piraso.ui.api.PirasoEntryLoaderProvider;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Provides entry loader
 */
@ServiceProvider(service=PirasoEntryLoaderProvider.class)
public class Log4jEntryLoaderProviderImpl implements PirasoEntryLoaderProvider {

    @Override
    public PirasoEntryLoader getLoader() {
        return new PirasoEntryLoaderImpl();
    }

    private class PirasoEntryLoaderImpl extends AbstractPirasoEntryLoader {
        @Override
        @SuppressWarnings("unchecked")
        public Entry loadEntry(String className, String content) throws IOException, ClassNotFoundException {
            Class clazz = Class.forName(className);
            return (Entry) mapper.readValue(content, clazz);
        }
    }
}
