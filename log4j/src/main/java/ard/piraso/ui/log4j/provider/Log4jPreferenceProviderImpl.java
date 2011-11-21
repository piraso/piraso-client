package ard.piraso.ui.log4j.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.api.log4j.Log4jPreferenceEnum;
import ard.piraso.ui.api.NCPreferenceProperty;
import ard.piraso.ui.api.PPFactory;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.*;

/**
 * Provides log4j preferences.
 */
@ServiceProvider(service=PreferenceProvider.class)
public class Log4jPreferenceProviderImpl implements PreferenceProvider {
    
    private Map<String, String> mapping;

    @Override
    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getName() {
        return getMessage("log4j.name");
    }

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        FileObject log4jFolder = FileUtil.getConfigRoot().getFileObject("log4j");
        FileObject preferences = log4jFolder.getFileObject("preferences");
        
        mapping = new HashMap<String, String>();
        List<PreferenceProperty> result = new LinkedList<PreferenceProperty>();

        PreferenceProperty log4jEnabled = PPFactory.createEntry(Log4jEntry.class, Log4jPreferenceEnum.LOG4J_ENABLED.getPropertyName(), Boolean.class);
        result.add(log4jEnabled);

        Enumeration<String> attributes = preferences.getAttributes();
        while(attributes.hasMoreElements()) {
            String regex = attributes.nextElement();
            
            mapping.put("log4j." + regex, String.valueOf(preferences.getAttribute(regex)) + " (All Levels)");
            NCPreferenceProperty pp = PPFactory.createEntry(Log4jEntry.class, "log4j." + regex, Boolean.class);
            pp.setParent(true);
            pp.addDependents(log4jEnabled);
            result.add(pp);

            List<NCPreferenceProperty> levels = new ArrayList<NCPreferenceProperty>(6);
            for(String level : new String[] {"fatal", "error", "warn", "info", "debug", "trace"}) {
                mapping.put("log4j." + regex + "." + level.toUpperCase(), getMessage("log4j." + level));

                NCPreferenceProperty pplevel = PPFactory.createEntry(Log4jEntry.class, "log4j." + regex + "." + level.toUpperCase(), Boolean.class);
                pplevel.addDependents(log4jEnabled);
                pplevel.setChild(true);
                pplevel.addDependents(levels.toArray(new NCPreferenceProperty[levels.size()]));

                levels.add(pplevel);
                pp.addDependents(pplevel);

                result.add(pplevel);
            }
        }
        
        return result;
    }

    @Override
    public String getMessage(String name) {
        if(mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            return getMessage(name, null);
        }
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(Log4jPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        if(Log4jEntry.class.isInstance(entry)) {
            return ((Log4jEntry) entry).getLogLevel();
        }

        return entry.getLevel();
    }
}
