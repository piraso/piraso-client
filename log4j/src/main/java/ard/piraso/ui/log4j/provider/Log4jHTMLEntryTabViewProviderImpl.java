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

package ard.piraso.ui.log4j.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.ui.api.EntryTabViewProvider;
import ard.piraso.ui.api.formatter.HTMLFormatter;
import ard.piraso.ui.api.formatter.XMLFormatter;
import ard.piraso.ui.api.provider.AbstractEntryTabViewProvider;
import ard.piraso.ui.api.views.HTMLTabView;
import org.openide.util.lookup.ServiceProvider;

/**
 * json tab view
 */
@ServiceProvider(service=EntryTabViewProvider.class)
public class Log4jHTMLEntryTabViewProviderImpl extends AbstractEntryTabViewProvider<HTMLTabView> {
    @Override
    protected boolean isSupported(Entry entry) {
        if(Log4jEntry.class.isInstance(entry)) {
            Log4jEntry log4jEntry = (Log4jEntry) entry;

            return HTMLFormatter.isHTMLLike(log4jEntry.getMessage()) && !XMLFormatter.isXMLLike(log4jEntry.getMessage());
        }

        return false;
    }

    @Override
    protected HTMLTabView createView(Entry entry) {
        Log4jEntry log4jEntry = (Log4jEntry) entry;

        return new HTMLTabView(log4jEntry);
    }
}