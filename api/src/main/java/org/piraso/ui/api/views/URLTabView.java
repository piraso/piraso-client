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

package org.piraso.ui.api.views;

import org.piraso.api.entry.MessageAwareEntry;
import org.piraso.ui.api.util.URLParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.util.List;

import static org.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * URL tab view
 */
public class URLTabView extends FilteredJTextPaneTabView<MessageAwareEntry> {
    /**
     * Creates new form MessageAwareEntry
     *
     * @param entry       the entry
     */
    public URLTabView(MessageAwareEntry entry) {
        super("URLs", entry, "URL contents is now copied to clipboard.");
        
        btnFilter.setVisible(false);
    }

    @Override
    protected void populateMessage(MessageAwareEntry m) throws Exception {
        List<URI> urls = URLParser.parseUrls(m.getMessage());

        int i = 0;
        for(URI uri : urls) {
            try {
                if(i != 0) {
                    insertKeyword(txtEditor, "\n\n");
                }

                insertKeyword(txtEditor, String.format("[%d] Scheme: ", ++i));
                insertCode(txtEditor, uri.getScheme());

                insertKeyword(txtEditor, "\n    Host: ");
                insertCode(txtEditor, uri.getHost());

                if(uri.getPort() > 80) {
                    insertKeyword(txtEditor, "\n    Port: ");
                    insertCode(txtEditor, String.valueOf(uri.getPort()));
                }

                insertKeyword(txtEditor, "\n    Path: ");
                insertCode(txtEditor, uri.getPath());

                String queryString = uri.getQuery();

                if(StringUtils.isNotBlank(queryString)) {
                    List<NameValuePair> params = URLEncodedUtils.parse(uri, "UTF-8");

                    insertKeyword(txtEditor, "\n    Query String: ");

                    for(NameValuePair nvp : params) {
                        insertCode(txtEditor, "\n       ");
                        insertIdentifier(txtEditor, nvp.getName() + ": ");
                        insertCode(txtEditor, nvp.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
