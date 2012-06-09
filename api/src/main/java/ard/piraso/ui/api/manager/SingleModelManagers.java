package ard.piraso.ui.api.manager;

import ard.piraso.ui.api.GeneralSettingsModel;
import ard.piraso.ui.api.StackTraceFilterModel;
import org.openide.filesystems.FileUtil;

/**
 * single model managers
 */
public interface SingleModelManagers {

    public static final StackTraceFilterModel DEFAULT_STACK_TRACE_FILTER = new StackTraceFilterModel() {{
        add("com.*", true);
        add("java.*", false);
        add("javax.*", false);
        add("org.apache.jsp.*", true);
    }};

    public static final SingleModelManager<StackTraceFilterModel> STACK_TRACE_FILTER = new SingleModelManagerImpl<StackTraceFilterModel>(FileUtil.getConfigRoot().getFileObject("StackTraceFilters"), StackTraceFilterModel.class, DEFAULT_STACK_TRACE_FILTER);

    public static final SingleModelManager<GeneralSettingsModel> GENERAL_SETTINGS = new SingleModelManagerImpl<GeneralSettingsModel>(FileUtil.getConfigRoot().getFileObject("GeneralSettings"), GeneralSettingsModel.class, new GeneralSettingsModel());
}
