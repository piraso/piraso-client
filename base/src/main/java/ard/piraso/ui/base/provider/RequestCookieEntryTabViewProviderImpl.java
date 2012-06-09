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

import ard.piraso.api.entry.CookieEntry;
import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.HttpRequestEntry;
import ard.piraso.ui.api.EntryTabViewProvider;
import ard.piraso.ui.api.provider.AbstractEntryTabViewProvider;
import ard.piraso.ui.api.views.NameValue;
import ard.piraso.ui.api.views.NameValueTableProvider;
import ard.piraso.ui.api.views.NameValueTableTabView;
import org.apache.commons.collections.CollectionUtils;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * json tab view
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class RequestCookieEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<NameValueTableTabView> {
    public RequestCookieEntryTabViewProviderImpl() {
        super("Cookies");
    }

    @Override
    protected boolean isSupported(Entry entry) {
        return HttpRequestEntry.class.isInstance(entry) && CollectionUtils.isNotEmpty(((HttpRequestEntry) entry).getCookies());
    }

    @Override
    protected NameValueTableTabView createView(Entry entry) {
        return new NameValueTableTabView(entry, new Provider());
    }

    private class Provider implements NameValueTableProvider {
        @Override
        public List<NameValue> list(Entry entry) {
            HttpRequestEntry request = (HttpRequestEntry) entry;

            List<NameValue> nvs = new ArrayList<NameValue>();

            for(CookieEntry cookie : request.getCookies()) {
                nvs.add(new NameValue(cookie.getName().toLowerCase(), cookie.getValue()));
            }

            return nvs;
        }
    }
}
