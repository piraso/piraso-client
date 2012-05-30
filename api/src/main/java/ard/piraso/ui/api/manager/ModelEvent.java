package ard.piraso.ui.api.manager;

import java.util.EventObject;

/**
 * Model event object
 */
public class ModelEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ModelEvent(ModelManager source) {
        super(source);
    }
}
