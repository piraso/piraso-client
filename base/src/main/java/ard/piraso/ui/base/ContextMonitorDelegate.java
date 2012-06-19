package ard.piraso.ui.base;

import ard.piraso.api.entry.RequestEntry;

/**
 * Context monitor handler
 */
public interface ContextMonitorDelegate {

    public String getName();

    public void selectRequest(RequestEntry request);
    
    public RequestEntry getSelectedRequest();
}
