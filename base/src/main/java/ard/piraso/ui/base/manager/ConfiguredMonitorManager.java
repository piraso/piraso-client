package ard.piraso.ui.base.manager;

import ard.piraso.api.JacksonUtils;
import ard.piraso.ui.api.NewContextMonitorModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configured monitor manager
 */
public enum ConfiguredMonitorManager {
    INSTANCE;

    private static final Logger LOG = Logger.getLogger(ConfiguredMonitorManager.class.getName());

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

    private static final FileObject CONFIGURED_MONITORS = FileUtil.getConfigRoot().getFileObject("Configured_Monitors");

    public void visitMonitors(MonitorVisitor visitor) {
        for(FileObject monitor : CONFIGURED_MONITORS.getChildren()) {
            try {
                String json = (String) monitor.getAttribute("value");
                visitor.visit(MAPPER.readValue(json, NewContextMonitorModel.class));
            } catch (Exception e) {
                LOG.log(Level.WARNING, String.format("Error while parsing json for monitor %s", monitor.getName()), e);
            }
        }
    }

    public NewContextMonitorModel getMonitor(String name) {
        for(FileObject monitor : CONFIGURED_MONITORS.getChildren()) {
            if(!name.equals(monitor.getName())) {
                continue;
            }

            try {
                String json = (String) monitor.getAttribute("value");
                return MAPPER.readValue(json, NewContextMonitorModel.class);
            } catch (Exception e) {
                LOG.log(Level.WARNING, String.format("Error while parsing json for monitor %s", monitor.getName()), e);
            }
        }

        return null;
    }
}
