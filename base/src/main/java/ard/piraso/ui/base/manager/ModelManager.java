package ard.piraso.ui.base.manager;

import java.io.IOException;

/**
 * model manager.
 */
public interface ModelManager<T> {
    int size();
    boolean contains(String name);
    void visit(ModelVisitor<T> visitor);
    T get(String name);
    void save(T model) throws IOException;
    void remove(String name) throws IOException;
}
