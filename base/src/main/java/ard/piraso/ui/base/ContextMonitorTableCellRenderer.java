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

package ard.piraso.ui.base;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MethodCallEntry;
import ard.piraso.api.entry.RequestEntry;
import ard.piraso.api.entry.ResponseEntry;
import ard.piraso.api.sql.SQLPreferenceEnum;
import ard.piraso.ui.base.model.IOEntryTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Table cell renderer.
 */
public class ContextMonitorTableCellRenderer extends JLabel implements TableCellRenderer {
    private static final int ROWNUM_INDEX = 0;
    private static final int ELAPSE_INDEX = 3;

    public ContextMonitorTableCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object data,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        setFont(table.getFont());
        IOEntryTableModel model = (IOEntryTableModel) table.getModel();
        Entry entry = model.getEntryAt(row).getEntry();

        setBackground(Color.WHITE);
        if (MethodCallEntry.class.isInstance(entry)) {
            setForeground(Color.DARK_GRAY);
        } else {
            setForeground(Color.BLACK);
        }

        if (column == ELAPSE_INDEX) {
            if (model.getElapseMilliseconds(entry) > 2000) {
                setForeground(Color.red);
                setFont(getFont().deriveFont(Font.BOLD));
            } else {
                setFont(getFont().deriveFont(Font.PLAIN));
            }

            setHorizontalAlignment(RIGHT);
        } else if (column == ROWNUM_INDEX) {
            setHorizontalAlignment(RIGHT);
        } else {
            setHorizontalAlignment(LEFT);
        }

        if (RequestEntry.class.isInstance(entry) || ResponseEntry.class.isInstance(entry)) {
            setBackground(new Color(255, 180, 66));
            setFont(getFont().deriveFont(Font.BOLD));
        } else if (SQLPreferenceEnum.CONNECTION_ENABLED.getPropertyName().equals(entry.getLevel())) {
            setForeground(Color.BLUE);
            setFont(getFont().deriveFont(Font.BOLD));
        } else if (SQLPreferenceEnum.VIEW_SQL_ENABLED.getPropertyName().equals(entry.getLevel())) {
            setBackground(new Color(189, 230, 170));
            setFont(getFont().deriveFont(Font.BOLD));
//        } else if (wrapper.key.getType() == LogEntryEnum.SQL_EXPLAIN_PLAN) {
//            setBackground(new Color(176,197,227));
        } else if (SQLPreferenceEnum.RESULTSET_ENABLED.getPropertyName().equals(entry.getLevel())) {
            setBackground(new Color(224, 232, 241));
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }

        if (column == ROWNUM_INDEX) {
            setBackground(new Color(233, 232, 226));
            setFont(getFont().deriveFont(Font.BOLD));
            setForeground(Color.BLACK);
        }

        if (data != null) {
            setText(String.valueOf(data));
        } else {
            setText("");
            setToolTipText("");
        }

        return this;
    }
}