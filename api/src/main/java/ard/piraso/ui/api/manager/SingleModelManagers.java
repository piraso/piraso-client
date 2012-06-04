package ard.piraso.ui.api.manager;

import ard.piraso.ui.api.StackTraceFilterModel;
import ard.piraso.ui.api.manager.SingleModelManager;
import ard.piraso.ui.api.manager.SingleModelManagerImpl;
import org.openide.filesystems.FileUtil;

/**
 * single model managers
 */
public interface SingleModelManagers {

    public static final StackTraceFilterModel DEFAULT_STACK_TRACE_FILTER = new StackTraceFilterModel() {{
        add("com.*", true);
        add("java.*", false);
        add("javax.*", false);
    }};

    public static final SingleModelManager<StackTraceFilterModel> STACK_TRACE_FILTER = new SingleModelManagerImpl<StackTraceFilterModel>(FileUtil.getConfigRoot().getFileObject("StackTraceFilters"), StackTraceFilterModel.class, DEFAULT_STACK_TRACE_FILTER);
}
