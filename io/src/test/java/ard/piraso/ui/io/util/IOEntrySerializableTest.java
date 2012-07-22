package ard.piraso.ui.io.util;

import ard.piraso.api.JacksonUtils;
import ard.piraso.api.entry.MessageEntry;
import ard.piraso.api.io.EntryReadEvent;
import ard.piraso.ui.io.IOEntry;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;

/**
 * Test for {@link IOEntrySerializable} class.
 */
public class IOEntrySerializableTest {
    @Test
    public void testJackson() throws Exception {
        ObjectMapper mapper = JacksonUtils.MAPPER;

        IOEntrySerializable serializable = new IOEntrySerializable(new EntryReadEvent(this, 12l, new MessageEntry("test"), new Date()));

        String str = mapper.writeValueAsString(serializable);

        IOEntrySerializable actual = mapper.readValue(str, IOEntrySerializable.class);
        IOEntry entry = new IOEntry(actual);

        assertNotNull(entry.getEntry());
    }
}
