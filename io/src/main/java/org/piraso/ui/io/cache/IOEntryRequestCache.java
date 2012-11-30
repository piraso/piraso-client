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

package org.piraso.ui.io.cache;

import org.piraso.ui.io.util.IOEntrySerializable;

import java.io.IOException;

/**
 * Wrapper class for request cache.
 */
public class IOEntryRequestCache {

    private String parentId;

    private long requestId;

    private IOEntryCacheManager cache;

    public IOEntryRequestCache(String parentId, long requestId) {
        this.parentId = parentId;
        this.requestId = requestId;

        cache = IOEntryCacheManager.INSTANCE;
    }

    public void put(IOEntrySerializable serializable) throws IOException {
        cache.add(parentId, requestId, serializable);
    }

    public IOEntrySerializable get(int rowNum) throws IOException {
        return cache.get(parentId, requestId, rowNum);
    }
}
