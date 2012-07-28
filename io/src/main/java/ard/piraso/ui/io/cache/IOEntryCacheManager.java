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

package ard.piraso.ui.io.cache;


import ard.piraso.api.JacksonUtils;
import ard.piraso.ui.io.util.IOEntrySerializable;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Cache Manager
 */
public class IOEntryCacheManager {
    
    public static final IOEntryCacheManager INSTANCE = new IOEntryCacheManager();

    private Cache cache;

    private ObjectMapper mapper;

    private IOEntryCacheManager() {
        CacheManager manager = CacheManager.getInstance();

        mapper = JacksonUtils.MAPPER;
        cache = manager.getCache(IOEntrySerializable.class.getName());
    }

    public void add(String id, long requestId, IOEntrySerializable entry) throws IOException {
        IOEntryCacheKey key = new IOEntryCacheKey(id, requestId, entry.getRowNum());

        cache.put(new Element(key, mapper.writeValueAsString(entry)));
    }

    public IOEntrySerializable get(String id, long requestId, long rowNum) throws IOException {
        IOEntryCacheKey key = new IOEntryCacheKey(id, requestId, rowNum);

        Element el = cache.get(key);
        String value = (String) el.getValue();

        return mapper.readValue(value, IOEntrySerializable.class);
    }
}
