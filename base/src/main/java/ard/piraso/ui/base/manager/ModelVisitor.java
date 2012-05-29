package ard.piraso.ui.base.manager;

/**
 * Visits all the models
 */
public interface ModelVisitor<T> {
    void visit(T t);
}
