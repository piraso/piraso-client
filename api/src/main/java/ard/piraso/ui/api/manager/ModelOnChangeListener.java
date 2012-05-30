package ard.piraso.ui.api.manager;

import java.util.EventListener;

/**
 * Model event listener
 */
public interface ModelOnChangeListener extends EventListener {
    void onChange(ModelEvent evt);
}
