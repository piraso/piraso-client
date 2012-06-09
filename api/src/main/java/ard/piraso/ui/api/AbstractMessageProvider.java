package ard.piraso.ui.api;

import ard.piraso.api.entry.Entry;
import org.apache.commons.collections.CollectionUtils;

/**
 * message provider
 */
public abstract class AbstractMessageProvider implements MessageProvider {

    @Override
    public String toGroupMessage(Entry entry) {
        if(entry.getGroup() == null) {
            return "";
        }

        if(CollectionUtils.isNotEmpty(entry.getGroup().getGroups())) {
            String group = entry.getGroup().getGroups().iterator().next();
            return String.format("[%s] ", group);
        }

        return "";
    }
}
