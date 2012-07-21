package ard.piraso.ui.base.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.JSONEntry;
import ard.piraso.ui.api.AbstractMessageProvider;
import ard.piraso.ui.api.MessageProvider;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openide.util.lookup.ServiceProvider;

/**
 * JSON entry
 */
@ServiceProvider(service=MessageProvider.class)
public class JSONEntryProviderImpl extends AbstractMessageProvider {
    public static final int MAX_GROUP_SIZE = 2;

    @Override
    public boolean isSupported(Entry entry) {
        return JSONEntry.class.isInstance(entry);
    }

    @Override
    public String toMessage(Entry entry) {
        return ((JSONEntry) entry).getMessage();
    }

    @Override
    public String toGroupMessage(Entry entry) {
        if(entry.getGroup() == null) {
            return "";
        }

        if(CollectionUtils.isNotEmpty(entry.getGroup().getGroups())) {
            String group = entry.getGroup().getGroups().iterator().next();
            String[] split = StringUtils.split(group, ".");

            if(split != null && split.length > MAX_GROUP_SIZE) {
                StringBuilder buf = new StringBuilder();

                for(int i = split.length - MAX_GROUP_SIZE; i < split.length; i++) {
                    if(buf.length() > 0) {
                        buf.append(".");
                    }

                    buf.append(split[i]);
                }

                group = buf.toString();
            }

            return String.format("[%s] ", group);
        }

        return "";
    }
}
