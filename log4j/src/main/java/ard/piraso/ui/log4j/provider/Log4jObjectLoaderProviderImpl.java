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

import ard.piraso.api.io.AbstractPirasoObjectLoader;
import ard.piraso.api.io.PirasoObjectLoader;
import ard.piraso.ui.api.PirasoObjectLoaderProvider;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 * Provides entry loader
 */
@ServiceProvider(service=PirasoObjectLoaderProvider.class)
public class Log4jObjectLoaderProviderImpl implements PirasoObjectLoaderProvider {

    @Override
    public PirasoObjectLoader getLoader() {
        return new PirasoEntryLoaderImpl();
    }

    private class PirasoEntryLoaderImpl extends AbstractPirasoObjectLoader {
        @Override
        @SuppressWarnings("unchecked")
        public Object loadObject(String className, String content) throws IOException, ClassNotFoundException {
            ClassLoader loader = Log4jObjectLoaderProviderImpl.class.getClassLoader();

            Class clazz = loader.loadClass(className);
            return mapper.readValue(content, clazz);
        }
    }
}
