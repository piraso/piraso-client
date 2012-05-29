package ard.piraso.ui.api.manager;

/**
 * Visits all the models
 */
public interface ModelVisitor<T> {
    void visit(T t);
}
