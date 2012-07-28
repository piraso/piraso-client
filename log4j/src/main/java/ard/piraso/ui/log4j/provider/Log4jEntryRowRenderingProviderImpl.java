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

package ard.piraso.ui.log4j.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.ui.api.EntryRowRenderingProvider;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Provides rendering to general entry types.
 */
@ServiceProvider(service=EntryRowRenderingProvider.class)
public class Log4jEntryRowRenderingProviderImpl implements EntryRowRenderingProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return Log4jEntry.class.isInstance(entry);
    }

    @Override
    public void render(JLabel cell, Entry entry) {
        Log4jEntry log4j = (Log4jEntry) entry;

        if("ERROR".equals(log4j.getLogLevel()) || "FATAL".equals(log4j.getLogLevel())) {
            cell.setBackground(new Color(0xFFC8BD));
            cell.setForeground(Color.RED);
        } else if("WARN".equals(log4j.getLogLevel())) {
            cell.setForeground(new Color(0xFF801D));
            cell.setBackground(new Color(0xF7D7C1));
        } else if("INFO".equals(log4j.getLogLevel())) {
            cell.setBackground(new Color(0xBAEEBA));
            cell.setForeground(new Color(0x008000));
        } else if("DEBUG".equals(log4j.getLogLevel())) {
            cell.setBackground(new Color(0xEAF2F5));
            cell.setForeground(new Color(0x999999));
        } else if("TRACE".equals(log4j.getLogLevel())) {
            cell.setBackground(new Color(0xF3F3F3));
            cell.setForeground(new Color(0x5D5D5D));
        } else {
            cell.setBackground(new Color(0xEAF2F5));
            cell.setForeground(new Color(0x999999));
        }
    }
}
