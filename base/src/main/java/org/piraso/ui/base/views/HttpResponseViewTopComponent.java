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

package org.piraso.ui.base.views;

import org.piraso.api.entry.CookieEntry;
import org.piraso.api.entry.HeaderEntry;
import org.piraso.api.entry.HttpResponseEntry;
import org.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;
import java.util.Date;
import java.util.Map;

import static org.piraso.ui.api.util.JTextPaneUtils.*;

@ActionID(category = "Window", id = "org.piraso.ui.base.HttpResponseViewTopComponent")
@ActionReference(path = "Menu/Window", position = 345)
@ConvertAsProperties(dtd = "-//org.piraso.ui.base//ResponseView//EN", autostore = false)
@TopComponent.Description(preferredID = "HttpResponseViewTopComponent", iconBase= "org/piraso/ui/base/icons/location_http.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ResponseViewAction", preferredID = "HttpResponseViewTopComponent")
public final class HttpResponseViewTopComponent extends BaseEntryViewTopComponent<HttpResponseEntry> {

    public HttpResponseViewTopComponent() {
        super(HttpResponseEntry.class, "Http Response");

        setName(NbBundle.getMessage(HttpResponseViewTopComponent.class, "CTL_ResponseViewTopComponent"));
        setToolTipText(NbBundle.getMessage(HttpResponseViewTopComponent.class, "HINT_ResponseViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, HttpResponseEntry entry) throws Exception {
        insertKeyword(txtMessage, "Status: ");

        if(currentEntry.getStatusReason() != null) {
            insertCode(txtMessage, currentEntry.getStatusReason());
        } else  {
            if(currentEntry.getStatus() == 0) {
                insertCode(txtMessage, "200");
            } else {
                insertCode(txtMessage, String.valueOf(currentEntry.getStatus()));
            }
        }

        if(MapUtils.isNotEmpty(currentEntry.getDateHeader()) ||
                MapUtils.isNotEmpty(currentEntry.getIntHeader()) ||
                MapUtils.isNotEmpty(currentEntry.getStringHeader())) {
            insertKeyword(txtMessage, "\n\nHeaders: ");

            if(MapUtils.isNotEmpty(currentEntry.getStringHeader())) {
                for(Map.Entry<String, HeaderEntry<String>> header : currentEntry.getStringHeader().entrySet()) {
                    if("cookie".equalsIgnoreCase(header.getKey())) {
                        continue;
                    }

                    HeaderEntry<String> h = header.getValue();

                    insertCode(txtMessage, "\n    ");
                    insertIdentifier(txtMessage, h.getName() + ": ");
                    insertCode(txtMessage, h.getValue());
                }
            }

            if(MapUtils.isNotEmpty(currentEntry.getIntHeader())) {
                for(Map.Entry<String, HeaderEntry<Integer>> header : currentEntry.getIntHeader().entrySet()) {
                    HeaderEntry<Integer> h = header.getValue();

                    insertCode(txtMessage, "\n    ");
                    insertIdentifier(txtMessage, h.getName() + ": ");
                    insertCode(txtMessage, String.valueOf(h.getValue()));
                }
            }

            if(MapUtils.isNotEmpty(currentEntry.getDateHeader())) {
                for(Map.Entry<String, HeaderEntry<Long>> header : currentEntry.getDateHeader().entrySet()) {
                    HeaderEntry<Long> h = header.getValue();

                    insertCode(txtMessage, "\n    ");
                    insertIdentifier(txtMessage, h.getName() + ": ");
                    insertCode(txtMessage, String.valueOf(new Date(h.getValue())));
                }
            }
        }

        if(CollectionUtils.isNotEmpty(currentEntry.getCookies())) {
            insertKeyword(txtMessage, "\n\nCookies: ");

            for(CookieEntry cookie : currentEntry.getCookies()) {
                insertCode(txtMessage, "\n    ");
                insertIdentifier(txtMessage, cookie.getName() + ": ");
                insertCode(txtMessage, cookie.getValue());
            }
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
