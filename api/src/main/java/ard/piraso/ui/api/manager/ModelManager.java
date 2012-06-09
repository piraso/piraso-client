package ard.piraso.ui.api.manager;

import ard.piraso.ui.api.WithNameModel;

import java.io.IOException;
import java.util.List;

/**
 * model manager.
 */
public interface ModelManager<T extends WithNameModel> extends BaseModelManager {
    List<String> getNames();
    boolean isEmpty();
    int size();
    boolean contains(String name);
    void visit(ModelVisitor<T> visitor);
    T get(String name);
    void save(T model) throws IOException;
    void remove(String name) throws IOException;
}
