/*
 * Copyright
 */
package ard.piraso.ui.base.wizard;

import ard.piraso.ui.base.model.Constants;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author adleon
 */
public abstract class AbstractWizardPanel<T extends WizardVisualPanel<M>, M extends WizardModel> 
    implements WizardDescriptor.Panel, ChangeListener {
    
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    
    protected T component;
    
    protected abstract T createComponent();

    @Override
    public Component getComponent() {
        if(component == null) {
            component = createComponent();
            component.setChangeListener(this);
            component.initChangeEvents();
        }
        
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(Object settings) {
        WizardDescriptor descriptor = (WizardDescriptor) settings;
        M model = (M) descriptor.getProperty(Constants.MODEL);
        
        component.read(model);
    }

    @Override
    public void storeSettings(Object settings) {
        WizardDescriptor descriptor = (WizardDescriptor) settings;
        M model = (M) descriptor.getProperty(Constants.MODEL);
        
        component.store(model);
    }

    @Override
    public boolean isValid() {
        return component.isEntriesValid();
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        fireChangeEvent();
    }
    
    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }    
}
