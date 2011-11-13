package ard.piraso.ui.io.cache;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.entry.MessageEntry;
import ard.piraso.ui.io.util.IOEntrySerializable;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;

/**
 * Test for {@link IOEntryCacheManager} class.
 */
public class IOEntryCacheManagerTest {
    @Test
    public void testAdd() throws Exception {
        IOEntryCacheManager manager = IOEntryCacheManager.INSTANCE;

        ObjectMapper mapper = JacksonUtils.createMapper();

        MessageEntry entry = new MessageEntry(1l, "test");

        IOEntrySerializable serializable = new IOEntrySerializable();
        serializable.setClassType(MessageEntry.class);
        serializable.setDate(new Date());
        serializable.setEntryValue(mapper.writeValueAsString(entry));
        serializable.setId(entry.getRequestId());
        serializable.setRowNum(1l);

        manager.add("a", 1, serializable);

        IOEntrySerializable actual = manager.get("a", 1, 1);

        assertNotNull(actual);
    }

    @Test
    public void testGet() throws Exception {

    }
}
