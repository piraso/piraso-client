/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.piraso.ui.sql;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * SQLData table cell renderer
 */
public class SQLDataTableCellRenderer extends JLabel implements TableCellRenderer {

    public SQLDataTableCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object data,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        setFont(table.getFont());

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }

        String col = String.valueOf(table.getModel().getValueAt(row, 0));
        if (col.startsWith("@")) {
            setBackground(Color.DARK_GRAY);//new Color(233, 232, 226));
            setFont(getFont().deriveFont(Font.BOLD));
            setForeground(Color.WHITE);
        }

        if("@null".equals(data)) {
            data = "null";
            setForeground(Color.BLUE);
        } else if("@not-supported".equals(data)) {
            data = "not-supported";
            setForeground(Color.RED);
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
