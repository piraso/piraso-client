package ard.piraso.ui.api.manager;

/**
 * Base model manager
 */
public interface BaseModelManager {
    void addModelOnChangeListener(ModelOnChangeListener listener);
    void removeModelOnChangeListener(ModelOnChangeListener listener);
    void fireOnChangeEvent();
}
