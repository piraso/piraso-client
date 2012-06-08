package ard.piraso.ui.log4j;

import ard.piraso.ui.api.manager.SingleModelManager;
import ard.piraso.ui.api.manager.SingleModelManagerImpl;
import ard.piraso.ui.log4j.provider.Log4jPreferencesModel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * single model managers
 */
public interface SingleModelManagers {

    public static final Log4jPreferencesModel DEFAULT = new Log4jPreferencesModel() {{
        add("com", "[GENERAL] com");
        add("org", "[GENERAL] org");
        add("org.springframework", "[THIRD-PARTY] org.springframework");
        add("org.hibernate", "[THIRD-PARTY] org.hibernate");
    }};

    public static final FileObject LOG4J_FILTER_OBJECT = FileUtil.getConfigRoot().getFileObject("Log4jPreferences");

    public static final SingleModelManager<Log4jPreferencesModel> LOG4J_PREFERENCES = new SingleModelManagerImpl<Log4jPreferencesModel>(LOG4J_FILTER_OBJECT, Log4jPreferencesModel.class, DEFAULT);
}
