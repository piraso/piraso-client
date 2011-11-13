package ard.piraso.ui.io.cache;

import ard.piraso.ui.io.util.IOEntrySerializable;

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
