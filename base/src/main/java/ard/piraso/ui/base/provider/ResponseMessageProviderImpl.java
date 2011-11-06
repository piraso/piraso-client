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

package ard.piraso.ui.base.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.ResponseEntry;
import ard.piraso.ui.api.MessageProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 * Request message provider
 */
@ServiceProvider(service=MessageProvider.class)
public class ResponseMessageProviderImpl implements MessageProvider {

    @Override
    public boolean isSupported(Entry entry) {
        return ResponseEntry.class.isInstance(entry);
    }

    @Override
    public String toMessage(Entry entry) {
        ResponseEntry response = (ResponseEntry) entry;

        StringBuilder buf = new StringBuilder("RESPONSE: ");
        buf.append(response.getStatus());

        if(response.getStatusReason() != null) {
            buf.append(" ").append(response.getStatusReason());
        }

        return buf.toString();
    }
}
