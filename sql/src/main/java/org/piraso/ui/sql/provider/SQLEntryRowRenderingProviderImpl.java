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

package org.piraso.ui.sql.provider;

import org.piraso.api.entry.Entry;
import org.piraso.api.sql.SQLDataViewEntry;
import org.piraso.api.sql.SQLPreferenceEnum;
import org.piraso.ui.api.EntryRowRenderingProvider;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Provides rendering to general entry types.
 */
@ServiceProvider(service=EntryRowRenderingProvider.class)
public class SQLEntryRowRenderingProviderImpl implements EntryRowRenderingProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return SQLPreferenceEnum.CONNECTION_ENABLED.getPropertyName().equals(entry.getLevel()) ||
               SQLPreferenceEnum.VIEW_SQL_ENABLED.getPropertyName().equals(entry.getLevel()) ||
               SQLPreferenceEnum.RESULTSET_ENABLED.getPropertyName().equals(entry.getLevel());
    }

    @Override
    public void render(JLabel cell, Entry entry) {
        if(SQLPreferenceEnum.CONNECTION_ENABLED.getPropertyName().equals(entry.getLevel())) {
            cell.setBackground(new Color(0xDAE9F6));
            cell.setForeground(new Color(98, 143, 181));
            cell.setFont(cell.getFont().deriveFont(Font.BOLD));
        } else if(SQLPreferenceEnum.VIEW_SQL_ENABLED.getPropertyName().equals(entry.getLevel())) {
            cell.setBackground(new Color(0xBAEEBA));
            cell.setForeground(new Color(0x008000));
            cell.setFont(cell.getFont().deriveFont(Font.BOLD));
        } else if(SQLPreferenceEnum.RESULTSET_ENABLED.getPropertyName().equals(entry.getLevel())) {
            cell.setForeground(new Color(98, 143, 181));
            if (SQLDataViewEntry.class.isInstance(entry)) {
                cell.setFont(cell.getFont().deriveFont(Font.BOLD));
            }
        }
    }
}
