/*
 * Copyright 2012 adeleon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ard.piraso.ui.base;

import ard.piraso.api.Preferences;
import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openide.util.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Preference panel.
 * 
 * @author adeleon
 */
public class PreferencePanel extends javax.swing.JPanel {
    private static final String EXPAND_ICON_PATH = "ard/piraso/ui/base/icons/bullet_toggle_plus.png";

    private static final String COLLAPSE_ICON_PATH = "ard/piraso/ui/base/icons/bullet_toggle_minus.png";

    private ImageIcon expandImage = ImageUtilities.loadImageIcon(EXPAND_ICON_PATH, true);

    private ImageIcon collapseImage = ImageUtilities.loadImageIcon(COLLAPSE_ICON_PATH, true);

    private PreferenceProvider provider;

    private java.util.List<ParentChildHandler> handlers;

    /**
     * Creates new form PreferencePanel
     */
    public PreferencePanel(PreferenceProvider provider) {
        this.provider = provider;
        
        initComponents();

        if(provider.isHorizontalChildLayout()) {
            initPreferenceComponentsHorizontalChildLayout();
        } else {
            initPreferenceComponentsVerticalChildLayout();
        }
    }

    public void autoExpandUI() {
        if(CollectionUtils.isNotEmpty(handlers)) {
            for(ParentChildHandler handler : handlers) {
                handler.refresh();
            }
        }
    }
    
    public void read(NewContextMonitorModel model) {
        Preferences preferences = model.getPreferences();

        for(int i = 0; i < preferenceKeys.length; i++) {
            chkPreferences[i].setSelected(preferences.isEnabled(preferenceKeys[i].getName()));
        }

        autoExpandUI();
    }
    
    private int getIndex(PreferenceProperty prop) {
        for(int i = 0; i < preferenceKeys.length; i++) {
            if(preferenceKeys[i].equals(prop)) {
                return i;
            }
        }

        return -1;
    }

    private void select(int i) {
        select(i, false);
    }

    private void select(int i, boolean skipOptional) {
        chkPreferences[i].setSelected(true);

        if(CollectionUtils.isNotEmpty(preferenceKeys[i].getDependents())) {
            for(PreferenceProperty property : preferenceKeys[i].getDependents()) {
                select(getIndex(property), true);
            }
        }

        // check all optional dependents
        if(!skipOptional && CollectionUtils.isNotEmpty(preferenceKeys[i].getOptionalDependents())) {
            for(PreferenceProperty property : preferenceKeys[i].getOptionalDependents()) {
                chkPreferences[getIndex(property)].setSelected(true);
            }
        }
    }

    private void deselect(int i) {
        chkPreferences[i].setSelected(false);

        for(PreferenceProperty property : preferenceKeys) {
            if(property.getDependents().contains(preferenceKeys[i])) {
                deselect(getIndex(property));
            }

            if(property.getOptionalDependents().contains(preferenceKeys[i])) {
                boolean allUnchecked = true;

                for(PreferenceProperty op : property.getOptionalDependents()) {
                    if(chkPreferences[getIndex(op)].isSelected()) {
                        allUnchecked = false;
                        break;
                    }
                }

                if(allUnchecked) {
                    chkPreferences[getIndex(property)].setSelected(false);
                }
            }
        }
    }

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

    private void initPreferenceComponentsVerticalChildLayout() {
        CellConstraints c = new CellConstraints();

        int size = CollectionUtils.size(provider.getPreferences());

        int l = 0, r = 2;
        chkPreferences = new JCheckBox[size];
        preferenceKeys = new PreferenceProperty[size];

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
    
    private void initPreferenceComponentsHorizontalChildLayout() {
        handlers = new ArrayList<ParentChildHandler>();
        CellConstraints c = new CellConstraints();

        int size = CollectionUtils.size(provider.getPreferences());
                
        int l = 0, r = 2;
        chkPreferences = new JCheckBox[size];
        preferenceKeys = new PreferenceProperty[size];

        JLabel lblHeader = new JLabel(provider.getName());
        Font of = lblHeader.getFont();
        lblHeader.setFont(of.deriveFont(Font.BOLD));

        pnlPreferences.add(lblHeader, c.xyw(2, r, 5));
        r += 2;

        JButton parentToggle = null;
        JPanel childrenPanel = null;
        ParentChildHandler parentChildHandler = null;

        Iterator<? extends PreferenceProperty> itrp = provider.getPreferences().iterator();
        for (int j = 0; j < provider.getPreferences().size(); j++, l++) {
            PreferenceProperty prop = itrp.next();

            preferenceKeys[l] = prop;
            chkPreferences[l] = new JCheckBox();
            chkPreferences[l].setText(provider.getMessage(prop.getName()));
            chkPreferences[l].setSelected(prop.isDefaultValue());

            if(prop.isChild()) {
                if(childrenPanel == null) {
                    childrenPanel = new JPanel();
                    childrenPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
                    childrenPanel.setOpaque(false);
                    childrenPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 1));
                    childrenPanel.setVisible(false);

                    if(parentChildHandler != null) {
                        parentChildHandler.setChildrenPanel(childrenPanel);
                    }

                    pnlPreferences.add(childrenPanel, c.xy(6, r));

                    r += 2;
                }

                if(parentChildHandler != null) {
                    parentChildHandler.addPreference(chkPreferences[l]);
                }

                childrenPanel.add(chkPreferences[l]);
                parentToggle = null;
            } else {
                if(parentToggle != null) {
                    parentChildHandler.hide();
                }

                JPanel parentPanel = new JPanel();
                parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
                parentPanel.setOpaque(false);

                parentToggle = new JButton(expandImage);

                JLabel previewLabel = new JLabel();
                previewLabel.setForeground(new Color(0, 128, 0));
                previewLabel.setFont(previewLabel.getFont().deriveFont(Font.ITALIC));

                parentChildHandler = new ParentChildHandler(parentToggle, previewLabel);
                parentChildHandler.addPreference(chkPreferences[l]);
                handlers.add(parentChildHandler);

                parentToggle.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

                parentPanel.add(parentToggle);
                parentPanel.add(chkPreferences[l]);
                parentPanel.add(previewLabel);

                pnlPreferences.add(parentPanel, c.xyw(4, r, 3));

                childrenPanel = null;
                r += 2;
            }

            chkPreferences[l].addActionListener(new CheckBoxClickHandler(l, parentChildHandler));
        }
    }

    private FormLayout createHorizontalChildLayout() {
        StringBuilder buf = new StringBuilder();

        buf.append("2dlu, ");
        buf.append("p, 4dlu, ");
        boolean hasChild = false;
        Iterator<? extends PreferenceProperty> itrp = provider.getPreferences().iterator();
        for(;itrp.hasNext(); ) {
            PreferenceProperty prop = itrp.next();
            if(prop.isChild() && !hasChild) {
                buf.append("p, ");
                buf.append(itrp.hasNext() ? " 1dlu, " : " 6dlu, ");
                hasChild = true;
            } else if(!prop.isChild()) {
                buf.append("p, ");
                buf.append(itrp.hasNext() ? " 1dlu, " : " 6dlu, ");
                hasChild = false;
            }
        }
        buf.append("2dlu");

        return new FormLayout("2dlu, 6dlu, 2dlu, 10dlu, 2dlu, p:g, 2dlu", buf.toString());
    }

    private FormLayout createVerticalChildLayout() {
        StringBuilder buf = new StringBuilder();

        buf.append("2dlu, ");
        buf.append("p, 4dlu, ");
        Iterator<? extends PreferenceProperty> itrp = provider.getPreferences().iterator();
        for(;itrp.hasNext(); itrp.next()) {
            buf.append("p, ");
            buf.append(itrp.hasNext() ? " 2dlu, " : " 6dlu, ");
        }
        buf.append("2dlu");

        return new FormLayout("2dlu, 6dlu, 2dlu, 10dlu, 2dlu, p:g, 2dlu", buf.toString());
    }
    
    private FormLayout createLayout() {
        if(provider.isHorizontalChildLayout()) {
            return createHorizontalChildLayout();
        } else {
            return createVerticalChildLayout();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlPreferences = new javax.swing.JPanel();

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        pnlPreferences.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnlPreferences.setOpaque(false);
        pnlPreferences.setLayout(null);
        pnlPreferences.setLayout(createLayout());
        jScrollPane1.setViewportView(pnlPreferences);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JPanel pnlPreferences;
    protected JCheckBox[] chkPreferences;
    protected PreferenceProperty[] preferenceKeys;
    // End of variables declaration//GEN-END:variables

    private class ParentChildHandler implements ActionListener {
        private JButton parentToggle;

        private JLabel preview;

        private JPanel childrenPanel;

        private LinkedList<JCheckBox> preferences = new LinkedList<JCheckBox>();

        private ParentChildHandler(JButton parentToggle, JLabel preview) {
            this.parentToggle = parentToggle;
            this.preview = preview;
        }

        private void setChildrenPanel(JPanel childrenPanel) {
            this.childrenPanel = childrenPanel;

            if(childrenPanel != null && parentToggle != null) {
                parentToggle.addActionListener(this);
            }
        }

        public void hide() {
            parentToggle.setVisible(false);
            preview.setVisible(false);
        }

        private void addPreference(JCheckBox preference) {
            preferences.add(preference);
        }

        public void refresh() {
            if(childrenPanel == null || parentToggle == null) {
                return;
            }

            refreshUI(false);
        }

        private void refreshUI() {
            if(childrenPanel != null && parentToggle != null) {
                refreshUI(childrenPanel.isVisible());
            }
        }

        private void refreshUI(boolean show) {
            if(show) {
                childrenPanel.setVisible(true);
                parentToggle.setIcon(collapseImage);
                preview.setVisible(false);
            } else {
                childrenPanel.setVisible(false);
                parentToggle.setIcon(expandImage);

                String previewText = createPreviewText();
                if(StringUtils.isNotBlank(previewText)) {
                    preview.setText("[" + previewText + "]");
                    preview.setVisible(true);
                } else {
                    preview.setVisible(false);
                }
            }
        }

        private String createPreviewText() {
            StringBuilder buf = new StringBuilder();
            for(JCheckBox box : preferences) {
                if(box.isSelected()) {
                    if(provider.isPreviewLastChildOnly()) {
                        buf.delete(0, buf.length());
                        buf.append(box.getText());
                    } else {
                        if(buf.length() != 0) {
                            buf.append(", ");
                        }

                        buf.append(box.getText());
                    }
                }
            }

            return buf.toString();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshUI(!childrenPanel.isVisible());
        }
    }

    private class CheckBoxClickHandler implements ActionListener {
        private int index;
        private ParentChildHandler handler;

        private CheckBoxClickHandler(int index) {
            this(index, null);
        }

        private CheckBoxClickHandler(int index, ParentChildHandler handler) {
            this.index = index;
            this.handler = handler;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(chkPreferences[index].isSelected()) {
                select(index);
            } else {
                deselect(index);
            }

            if(handler != null) {
                handler.refreshUI();
            }
        }
    }    

}
