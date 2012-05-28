package ard.piraso.ui.base.manager;

import ard.piraso.ui.api.NewContextMonitorModel;

/**
 * Visits all monitor model.
 */
public interface MonitorVisitor {

    void visit(NewContextMonitorModel model);
}
