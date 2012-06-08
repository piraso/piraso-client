package ard.piraso.ui.api.manager;

import ard.piraso.api.JacksonUtils;
import ard.piraso.ui.api.WithNameModel;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.filesystems.FileObject;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model manager.
 */
public class ModelManagerImpl<T extends WithNameModel> implements ModelManager<T> {

    private static final Logger LOG = Logger.getLogger(ModelManagerImpl.class.getName());

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

    private final FileObject persistent;

    private final Class<T> clazz;

    private EventListenerList listeners = new EventListenerList();

    public ModelManagerImpl(FileObject persistent, Class<T> clazz) {
        this.clazz = clazz;
        this.persistent = persistent;
    }

    @Override
    public void addModelOnChangeListener(ModelOnChangeListener listener) {
        listeners.add(ModelOnChangeListener.class, listener);
    }

    @Override
    public void fireOnChangeEvent() {
        ModelOnChangeListener[] handlers = listeners.getListeners(ModelOnChangeListener.class);

        for(ModelOnChangeListener handler : handlers) {
            handler.onChange(new ModelEvent(this));
        }
    }

    @Override
    public List<String> getNames() {
        final List<String> names = new ArrayList<String>();

        visit(new ModelVisitor<T>() {
            @Override
            public void visit(T t) {
                names.add(t.getName());
            }
        });

        Collections.sort(names);
        return names;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        int total = 0;

        for(FileObject monitor : persistent.getChildren()) {
            try {
                String json = (String) monitor.getAttribute("value");
                MAPPER.readValue(json, clazz);
                total++;
            } catch (Exception e) {
                LOG.log(Level.WARNING, String.format("Error while parsing json '%s'", monitor.getName()), e);
            }
        }

        return total;
    }

    @Override
    public boolean contains(String name) {
        return get(name) != null;
    }

    @Override
    public void visit(ModelVisitor<T> visitor) {
        for(FileObject monitor : persistent.getChildren()) {
            try {
                String json = (String) monitor.getAttribute("value");
                visitor.visit(MAPPER.readValue(json, clazz));
            } catch (Exception e) {
                LOG.log(Level.WARNING, String.format("Error while parsing json '%s'", monitor.getName()), e);
            }
        }
    }

    @Override
    public T get(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }

        for(FileObject monitor : persistent.getChildren()) {
            if(!name.equals(monitor.getName())) {
                continue;
            }

            try {
                String json = (String) monitor.getAttribute("value");
                return MAPPER.readValue(json, clazz);
            } catch (Exception e) {
                LOG.log(Level.WARNING, String.format("Error while parsing json %s", monitor.getName()), e);
            }
        }

        return null;
    }

    @Override
    public void save(T model) throws IOException {
        for(FileObject monitor : persistent.getChildren()) {
            if(!model.getName().equalsIgnoreCase(monitor.getName())) {
                continue;
            }

            monitor.setAttribute("value", MAPPER.writeValueAsString(model));
            fireOnChangeEvent();
            return;
        }

        FileObject newMonitor = persistent.createData(model.getName());
        newMonitor.setAttribute("value", MAPPER.writeValueAsString(model));
        fireOnChangeEvent();
    }

    @Override
    public void remove(String name) throws IOException {
        for(FileObject monitor : persistent.getChildren()) {
            if(!name.equals(monitor.getName())) {
                continue;
            }

            monitor.delete();
        }

        fireOnChangeEvent();
    }

}
