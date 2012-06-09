package ard.piraso.ui.api.manager;

import ard.piraso.api.JacksonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openide.filesystems.FileObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation for {@link SingleModelManager}.
 */
public class SingleModelManagerImpl<T> extends AbstractModelManager implements SingleModelManager<T> {

    private static final Logger LOG = Logger.getLogger(ModelManagerImpl.class.getName());

    private static final ObjectMapper MAPPER = JacksonUtils.createMapper();

    private final FileObject persistent;

    private final Class<T> clazz;

    private T defaultValue;

    public SingleModelManagerImpl(FileObject persistent, Class<T> clazz) {
        this(persistent, clazz, null);
    }

    public SingleModelManagerImpl(FileObject persistent, Class<T> clazz, T defaultValue) {
        this.clazz = clazz;
        this.persistent = persistent;
        this.defaultValue = defaultValue;
    }

    @Override
    public void save(T item) {
        try {
            persistent.setAttribute("value", MAPPER.writeValueAsString(item));

            fireOnChangeEvent();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error while writing json", e);
        }
    }

    @Override
    public T get() {
        try {
            if(persistent.getAttribute("value") != null) {
                return MAPPER.readValue(String.valueOf(persistent.getAttribute("value")), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
