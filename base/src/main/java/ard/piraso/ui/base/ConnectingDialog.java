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

import ard.piraso.ui.api.NewContextMonitorModel;
import ard.piraso.ui.io.impl.HttpEntrySource;
import org.openide.windows.WindowManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adeleon
 */
public class ConnectingDialog extends javax.swing.JDialog {
    
    private static final Logger LOG = Logger.getLogger(ConnectingDialog.class.getName());
    
    private Map<NewContextMonitorModel, String> failures;
    
    private List<HttpEntrySource> validResults;

    /**
     * Creates new form ConnectingDialog
     */
    public ConnectingDialog() {
        super(WindowManager.getDefault().getMainWindow(), false);
        initComponents();

        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
    
    public void startTests(List<NewContextMonitorModel> models) {
        failures = new HashMap<NewContextMonitorModel, String>();
        validResults = new ArrayList<HttpEntrySource>();
        setVisible(true);

        for(NewContextMonitorModel m : models) {
            HttpEntrySource source = new HttpEntrySource(m.getPreferences(), m.getLoggingUrl(), m.getWatchedAddr());
            source.setName(m.getName());

            try {
                setLabel("Testing connection to " + m.getName() + "...");
                source.reset();
                if(source.testConnection()) {
                    validResults.add(source);
                } else {
                    failures.put(m, "Connection Failure.");
                }
            } catch(Exception e) {
                //LOG.log(Level.WARNING, e.getMessage(), e);
                failures.put(m, e.getMessage());
            }
        }

        dispose();
    }

    public List<HttpEntrySource> getValidResults() {
        return validResults;
    }

    public Map<NewContextMonitorModel, String> getFailures() {
        return failures;
    }

    public void setLabel(String text) {
        lblStatus.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        lblStatus.setText(org.openide.util.NbBundle.getMessage(ConnectingDialog.class, "ConnectingDialog.lblStatus.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(lblStatus)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(17, 17, 17)
                .add(lblStatus)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
