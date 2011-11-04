/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.base;

import ard.piraso.api.GeneralPreferenceEnum;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service=PreferenceProvider.class)
public class GeneralPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<PreferenceProperty> getPreferences() {
        List<PreferenceProperty> preferences = new ArrayList<PreferenceProperty>(2);
        
        preferences.add(new PreferenceProperty(GeneralPreferenceEnum.STACK_TRACE_ENABLED.getPropertyName(), Boolean.class));
        preferences.add(new PreferenceProperty(GeneralPreferenceEnum.SCOPE_ENABLED.getPropertyName(), Boolean.class));
        
        return preferences;
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(GeneralPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getName() {
        return getMessage("general.name");
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
