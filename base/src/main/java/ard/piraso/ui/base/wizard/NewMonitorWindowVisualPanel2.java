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

package ard.piraso.ui.base.wizard;

import ard.piraso.api.Preferences;
import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import ard.piraso.ui.api.extension.WizardVisualPanel;
import ard.piraso.ui.base.manager.PreferenceProviderManager;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public final class NewMonitorWindowVisualPanel2 extends WizardVisualPanel<NewContextMonitorModel> {
            
    /** Creates new form NewMonitorWindowVisualPanel2 */
    public NewMonitorWindowVisualPanel2() {
        initComponents();
        initPreferenceComponents();
    }

    @Override
    public void read(NewContextMonitorModel model) {
        Preferences preferences = model.getPreferences();

        for(int i = 0; i < preferenceKeys.length; i++) {
            chkPreferences[i].setSelected(preferences.isEnabled(preferenceKeys[i].getName()));
        }
    }

    private int getIndex(PreferenceProperty prop) {
        for(int i = 0; i < preferenceKeys.length; i++) {
            if(preferenceKeys[i].equals(prop)) {
                return i;
            }
        }

        throw new IllegalArgumentException(String.format("No property with name %s found", prop.getName()));
    }

    private void select(int i) {
        chkPreferences[i].setSelected(true);

        if(CollectionUtils.isNotEmpty(preferenceKeys[i].getDependents())) {
            for(PreferenceProperty property : preferenceKeys[i].getDependents()) {
                select(getIndex(property));
            }
        }
    }

    private void deselect(int i) {
        chkPreferences[i].setSelected(false);

        for(PreferenceProperty property : preferenceKeys) {
            if(property.getDependents().contains(preferenceKeys[i])) {
                deselect(getIndex(property));
            }
        }
    }

    @Override
    public void store(NewContextMonitorModel model) {
        Preferences preferences = model.getPreferences();
        for(int i = 0; i < chkPreferences.length; i++) {
            if(chkPreferences[i].isSelected()) {
                preferences.addProperty(preferenceKeys[i].getName(), Boolean.TRUE);
            } else if(MapUtils.isNotEmpty(preferences.getBooleanProperties())) {
                model.getPreferences().getBooleanProperties().remove(preferenceKeys[i].getName());
            }
        }
    }

    @Override
    public boolean isEntriesValid() {
        return true;
    }
    
    private void initPreferenceComponents() {
        CellConstraints c = new CellConstraints();

        List<PreferenceProvider> providers = PreferenceProviderManager.INSTANCE.getProviders();
        int size = 0;
        for(PreferenceProvider provider : providers) {
            size += CollectionUtils.size(provider.getPreferences());
        }
                
        int l = 0, r = 2;
        chkPreferences = new JCheckBox[size];
        preferenceKeys = new PreferenceProperty[size];

        for (PreferenceProvider provider : providers) {
            JLabel lblHeader = new JLabel(provider.getName());
            Font of = lblHeader.getFont();
            lblHeader.setFont(of.deriveFont(Font.BOLD));

            pnlPreferences.add(lblHeader, c.xyw(2, r, 5));
            r += 2;

            Iterator<? extends PreferenceProperty> itrp = provider.getPreferences().iterator();
            for (int j = 0; j < provider.getPreferences().size(); j++, l++) {
                PreferenceProperty prop = itrp.next();

                preferenceKeys[l] = prop;
                chkPreferences[l] = new JCheckBox();
                chkPreferences[l].setText(provider.getMessage(prop.getName()));
                chkPreferences[l].setSelected(prop.isDefaultValue());
                chkPreferences[l].addActionListener(new CheckBoxClickHandler(l));

                if(prop.isParent()) {
                    chkPreferences[l].setFont(chkPreferences[l].getFont().deriveFont(Font.BOLD));
                }

                if(prop.isChild()) {
                    pnlPreferences.add(chkPreferences[l], c.xy(6, r));
                } else {
                    pnlPreferences.add(chkPreferences[l], c.xyw(4, r, 3));
                }

                r += 2;
            }
        }
    }
    
    private FormLayout createLayout() {
        StringBuilder buf = new StringBuilder();
                
        buf.append("2dlu, ");
        
        for(PreferenceProvider provider : PreferenceProviderManager.INSTANCE.getProviders()) {
            buf.append("p, 4dlu, ");
            
            Iterator<? extends PreferenceProperty> itrp = provider.getPreferences().iterator();
            for(;itrp.hasNext(); itrp.next()) {
                buf.append("p, ");
                buf.append(itrp.hasNext() ? " 2dlu, " : " 6dlu, ");
            }
        }
        
        buf.append("2dlu");
        
        return new FormLayout("2dlu, 6dlu, 2dlu, 10dlu, 2dlu, p:g, 2dlu", buf.toString());
    }
    
    @Override
    public String getName() {
        return "Monitoring Preferences";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlPreferences = new javax.swing.JPanel();

        pnlPreferences.setOpaque(false);
        pnlPreferences.setLayout(null);
        pnlPreferences.setLayout(createLayout());
        jScrollPane1.setViewportView(pnlPreferences);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JPanel pnlPreferences;
    protected JCheckBox[] chkPreferences;
    protected PreferenceProperty[] preferenceKeys;
    // End of variables declaration//GEN-END:variables

    private class CheckBoxClickHandler implements ActionListener {
        private int index;

        private CheckBoxClickHandler(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(chkPreferences[index].isSelected()) {
                select(index);
            } else {
                deselect(index);
            }
        }
    }

}
