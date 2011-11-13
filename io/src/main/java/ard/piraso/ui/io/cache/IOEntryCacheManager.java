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

        mapper = JacksonUtils.createMapper();
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
