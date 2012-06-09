package ard.piraso.ui.api.manager;

/**
 * Single model manager
 */
public interface SingleModelManager<T> extends BaseModelManager {
    void save(T item);
    T get();
}
