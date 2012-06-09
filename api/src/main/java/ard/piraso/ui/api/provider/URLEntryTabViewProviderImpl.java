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

package ard.piraso.ui.api.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MessageAwareEntry;
import ard.piraso.ui.api.EntryTabViewProvider;
import ard.piraso.ui.api.util.URLParser;
import ard.piraso.ui.api.views.URLTabView;
import org.apache.commons.collections.CollectionUtils;
import org.openide.util.lookup.ServiceProvider;

/**
 * json tab view
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class URLEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<URLTabView> {
    public URLEntryTabViewProviderImpl() {
        super("URLS");
    }

    @Override
    protected boolean isSupported(Entry entry) {
        return MessageAwareEntry.class.isInstance(entry) && CollectionUtils.isNotEmpty(URLParser.parseUrls(((MessageAwareEntry) entry).getMessage()));
    }

    @Override
    protected URLTabView createView(Entry entry) {
        return new URLTabView((MessageAwareEntry) entry);
    }

}
