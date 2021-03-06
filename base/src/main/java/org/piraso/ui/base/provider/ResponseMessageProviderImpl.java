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

package org.piraso.ui.base.provider;

import org.piraso.api.entry.Entry;
import org.piraso.api.entry.ResponseEntry;
import org.piraso.ui.api.AbstractMessageProvider;
import org.piraso.ui.api.MessageProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 * Request message provider
 */
@ServiceProvider(service=MessageProvider.class)
public class ResponseMessageProviderImpl  extends AbstractMessageProvider {

    @Override
    public boolean isSupported(Entry entry) {
        return ResponseEntry.class.isInstance(entry);
    }

    @Override
    public String toMessage(Entry entry) {
        ResponseEntry response = (ResponseEntry) entry;

        StringBuilder buf = new StringBuilder("RESPONSE:");

        if(response.getStatusReason() != null) {
            buf.append(" ").append(response.getStatusReason());
        }

        if(response.getStatus() == 0) {
            buf.append(" 200");
        } else {
            buf.append(" ").append(response.getStatus());
        }

        return buf.toString();
    }
}

