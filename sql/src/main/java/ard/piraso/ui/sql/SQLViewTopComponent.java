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

package ard.piraso.ui.sql;

import SQLinForm_200.SQLForm;
import ard.piraso.api.sql.SQLDataViewEntry;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ActionID(category = "Window", id = "ard.piraso.ui.sql.SQLViewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.sql//SQLView//EN", autostore = false)
@TopComponent.Description(preferredID = "SQLViewTopComponent", iconBase="ard/piraso/ui/sql/icons/sql.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SQLViewAction", preferredID = "SQLViewTopComponent")
public final class SQLViewTopComponent extends TopComponent {

    public SQLViewTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SQLViewTopComponent.class, "CTL_SQLViewTopComponent"));
        setToolTipText(NbBundle.getMessage(SQLViewTopComponent.class, "HINT_SQLViewTopComponent"));

    }

    private String getSQL() {
        SQLDataViewEntry entry = new SQLDataViewEntry();

        return null;
    }

    private SQLForm createSQLInForm() {
        SQLForm form = new SQLForm();
        form.setAlignmentKeyword(true);
        form.setAlignmentOperator(true);
        form.setLowerCase(true);
        form.setSuppressEmptyLine(true);
        form.setSuppressLinebreak(true);
        form.setSuppressSpace(true);
        form.setIndentJoin(true);
        form.setSuppressEmptyLine(true);
        form.setSuppressLinebreak(true);
        form.setSuppressSpace(true);
        form.setLineBreak(true, false, true, false, false, false);

        return form;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
