/*
 * Copyright (c) 2011. Piraso Alvin R. de Leon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Piraso licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ard.piraso.ui.api.extension;

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
    @SuppressWarnings("unchecked")
    public void readSettings(Object settings) {
        WizardDescriptor descriptor = (WizardDescriptor) settings;
        M model = (M) descriptor.getProperty(Constants.MODEL);
        
        component.read(model);
    }

    @Override
    @SuppressWarnings("unchecked")
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
