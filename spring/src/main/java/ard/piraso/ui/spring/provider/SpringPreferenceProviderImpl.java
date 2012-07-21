package ard.piraso.ui.spring.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.spring.SpringPreferenceEnum;
import ard.piraso.ui.api.NCPreferenceProperty;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Spring preference provider
 */
@ServiceProvider(service=PreferenceProvider.class)
public class SpringPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        NCPreferenceProperty enabled = new NCPreferenceProperty(SpringPreferenceEnum.REMOTING_ENABLED.getPropertyName(), Boolean.class, true);
        NCPreferenceProperty elapseTime = new NCPreferenceProperty(SpringPreferenceEnum.REMOTING_ELAPSE_TIME_ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty methodCall = new NCPreferenceProperty(SpringPreferenceEnum.REMOTING_METHOD_CALL_ENABLED.getPropertyName(), Boolean.class);

        enabled.setParent(true);
        elapseTime.setChild(true);
        elapseTime.addDependents(enabled);

        methodCall.setChild(true);
        methodCall.addDependents(enabled);

        return Arrays.asList(enabled, elapseTime, methodCall);
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(SpringPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        return getMessage(entry.getLevel() + ".short");
    }

    @Override
    public Integer getOrder() {
        return 4;
    }

    @Override
    public String getName() {
        return getMessage("spring.name");
    }

    @Override
    public String getShortName() {
        return getMessage("spring.name.short");
    }

    @Override
    public boolean isHorizontalChildLayout() {
        return false;
    }

    @Override
    public boolean isPreviewLastChildOnly() {
        return false;
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
