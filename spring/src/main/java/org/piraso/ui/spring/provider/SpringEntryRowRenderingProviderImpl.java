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

package org.piraso.ui.spring.provider;

import org.piraso.api.entry.Entry;
import org.piraso.api.spring.SpringPreferenceEnum;
import org.piraso.ui.api.EntryRowRenderingProvider;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.*;
import java.awt.*;

/**
 * spring row rendering
 */
@ServiceProvider(service=EntryRowRenderingProvider.class)
public class SpringEntryRowRenderingProviderImpl implements EntryRowRenderingProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return SpringPreferenceEnum.REMOTING_ENABLED.getPropertyName().equals(entry.getLevel());
    }

    @Override
    public void render(JLabel cell, Entry entry) {
        cell.setBackground(new Color(0xBAEEBA));
        cell.setForeground(new Color(0x008000));
    }
}
