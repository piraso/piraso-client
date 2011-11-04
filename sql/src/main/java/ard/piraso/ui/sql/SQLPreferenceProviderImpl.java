package ard.piraso.ui.sql;

import ard.piraso.api.sql.SQLPreferenceEnum;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adleon
 */
@ServiceProvider(service=PreferenceProvider.class)
public class SQLPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<PreferenceProperty> getPreferences() {
        List<PreferenceProperty> properties = new ArrayList<PreferenceProperty>(SQLPreferenceEnum.values().length);
        
        for(SQLPreferenceEnum flag : SQLPreferenceEnum.values()) {
            PreferenceProperty property;
            if(flag.isLevel()) {
                property = new PreferenceProperty(flag.getPropertyName(), Boolean.class);
            } else {
                property = new PreferenceProperty(flag.getPropertyName(), Integer.class);
            }
            
            properties.add(property);
        }
        
        return properties;
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(SQLPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getName() {
        return getMessage("sql.name");
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
    
}
