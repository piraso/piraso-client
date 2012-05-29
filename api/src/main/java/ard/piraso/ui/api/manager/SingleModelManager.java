package ard.piraso.ui.api.manager;

/**
 * Single model manager
 */
public interface SingleModelManager<T> {
    void save(T item);
    T get();
}
