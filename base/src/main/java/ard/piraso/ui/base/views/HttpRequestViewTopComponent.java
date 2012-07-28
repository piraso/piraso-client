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

package ard.piraso.ui.base.views;

import ard.piraso.api.entry.CookieEntry;
import ard.piraso.api.entry.HttpRequestEntry;
import ard.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.util.Arrays;
import java.util.Map;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
* Top component which displays something.
*/
@ActionID(category = "Window", id = "ard.piraso.ui.base.HttpRequestViewTopComponent")
@ActionReference(path = "Menu/Window", position = 340)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.base//RequestView//EN", autostore = false)
@TopComponent.Description(preferredID = "HttpRequestViewTopComponent", iconBase="ard/piraso/ui/base/icons/location_http.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_RequestViewAction", preferredID = "HttpRequestViewTopComponent")
public final class HttpRequestViewTopComponent extends BaseEntryViewTopComponent<HttpRequestEntry> {

    public HttpRequestViewTopComponent() {
        super(HttpRequestEntry.class, "Http Request");

        setName(NbBundle.getMessage(HttpRequestViewTopComponent.class, "CTL_RequestViewTopComponent"));
        setToolTipText(NbBundle.getMessage(HttpRequestViewTopComponent.class, "HINT_RequestViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, HttpRequestEntry entry) throws BadLocationException {
        insertKeyword(txtMessage, "Server: ");
        insertCode(txtMessage, currentEntry.getServerName());

        insertKeyword(txtMessage, "\nURL: ");
        insertCode(txtMessage, currentEntry.getUri());

        insertKeyword(txtMessage, "\nMethod: ");
        insertCode(txtMessage, currentEntry.getMethod());

        insertKeyword(txtMessage, "\nRemote Address: ");
        insertCode(txtMessage, currentEntry.getRemoteAddr());

        if(MapUtils.isNotEmpty(currentEntry.getHeaders())) {
            insertKeyword(txtMessage, "\n\nHeaders: ");

            for(Map.Entry<String, String> header : currentEntry.getHeaders().entrySet()) {
                if("cookie".equals(header.getKey())) {
                    continue;
                }
                insertCode(txtMessage, "\n    ");
                insertIdentifier(txtMessage, header.getKey().toLowerCase() + ": ");
                insertCode(txtMessage, header.getValue());
            }
        }

        if(MapUtils.isNotEmpty(currentEntry.getParameters())) {
            insertKeyword(txtMessage, "\n\nParameters: ");

            for(Map.Entry<String, String[]> param : currentEntry.getParameters().entrySet()) {
                insertCode(txtMessage, "\n    ");
                insertIdentifier(txtMessage, param.getKey() + ": ");
                insertCode(txtMessage, String.valueOf(Arrays.asList(param.getValue())));
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
