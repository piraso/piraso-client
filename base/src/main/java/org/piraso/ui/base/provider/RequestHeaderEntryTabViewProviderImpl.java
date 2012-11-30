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
import org.piraso.api.entry.HttpRequestEntry;
import org.piraso.ui.api.EntryTabViewProvider;
import org.piraso.ui.api.provider.AbstractEntryTabViewProvider;
import org.piraso.ui.api.views.NameValue;
import org.piraso.ui.api.views.NameValueTableProvider;
import org.piraso.ui.api.views.NameValueTableTabView;
import org.apache.commons.collections.MapUtils;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json tab view
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class RequestHeaderEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<NameValueTableTabView> {

    @Override
    protected boolean isSupported(Entry entry) {
        return HttpRequestEntry.class.isInstance(entry) && MapUtils.isNotEmpty(((HttpRequestEntry) entry).getHeaders());
    }

    @Override
    protected NameValueTableTabView createView(Entry entry) {
        return new NameValueTableTabView("Headers", entry, new Provider());
    }

    private class Provider implements NameValueTableProvider {
        @Override
        public List<NameValue> list(Entry entry) {
            HttpRequestEntry request = (HttpRequestEntry) entry;

            List<NameValue> nvs = new ArrayList<NameValue>();

            for(Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                if("cookie".equals(header.getKey())) {
                    continue;
                }

                nvs.add(new NameValue(header.getKey().toLowerCase(), header.getValue()));
            }

            return nvs;
        }
    }
}
