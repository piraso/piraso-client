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
import org.piraso.api.sql.SQLViewEntry;
import org.piraso.ui.api.AbstractMessageProvider;
import org.piraso.ui.api.MessageProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 * Provides message for {@link org.piraso.api.sql.SQLViewEntry} class.
 */
@ServiceProvider(service=MessageProvider.class)
public class SQLViewMessageProviderImpl extends AbstractMessageProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return SQLViewEntry.class.isInstance(entry);
    }

    @Override
    public String toMessage(Entry entry) {
        SQLViewEntry sql = (SQLViewEntry) entry;

        return sql.getSql();
    }
}
