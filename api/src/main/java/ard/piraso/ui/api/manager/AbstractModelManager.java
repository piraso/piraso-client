package ard.piraso.ui.api.manager;

import javax.swing.event.EventListenerList;

/**
 * model managers
 */
public abstract class AbstractModelManager implements BaseModelManager {

    private EventListenerList listeners = new EventListenerList();

    @Override
    public void addModelOnChangeListener(ModelOnChangeListener listener) {
        listeners.add(ModelOnChangeListener.class, listener);
    }

    public void removeModelOnChangeListener(ModelOnChangeListener listener) {
        listeners.remove(ModelOnChangeListener.class, listener);
    }

    @Override
    public void fireOnChangeEvent() {
        ModelOnChangeListener[] handlers = listeners.getListeners(ModelOnChangeListener.class);

        for(ModelOnChangeListener handler : handlers) {
            handler.onChange(new ModelEvent(this));
        }
    }


}
