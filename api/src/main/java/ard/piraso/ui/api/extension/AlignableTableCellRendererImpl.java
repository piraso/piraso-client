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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ard.piraso.ui.api.extension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * The AlignableTableCellRendererImpl class is
 *
 * @author alvin
 */
public class AlignableTableCellRendererImpl extends JLabel implements TableCellRenderer {
    private int alignment;

    private boolean selection;

    private boolean fixed;

    public AlignableTableCellRendererImpl(int alignment, boolean selection) {
        this(alignment, selection, false);
    }

    public AlignableTableCellRendererImpl(int alignment, boolean selection, boolean fixed) {
        this.alignment = alignment;
        this.selection = selection;
        this.fixed = fixed;
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object data,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        setFont(table.getFont());
        setHorizontalAlignment(alignment);

        if (isSelected && selection) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        if(!selection && isSelected) {
            setBorder(new LineBorder(Color.BLACK));
        } else {
            setBorder(new EmptyBorder(1, 1, 1, 1));
        }

        if (fixed) {
            setBackground(new Color(233, 232, 226));
            setFont(getFont().deriveFont(Font.BOLD));
            setForeground(Color.BLACK);
            setBorder(new EmptyBorder(1, 1, 1, 1));
        }

        setText(String.valueOf(data));

        return this;
    }

}
