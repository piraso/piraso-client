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

package org.piraso.ui.base.manager;

import org.piraso.api.JacksonUtils;
import org.piraso.client.net.HttpBasicAuthentication;
import org.piraso.client.net.HttpPirasoException;
import org.piraso.ui.api.ImportHandler;
import org.piraso.ui.api.ObjectEntrySettings;
import org.piraso.ui.api.HttpSettingsUpdateModel;
import org.piraso.ui.api.manager.SingleModelManagers;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

public class HttpUpdateManager extends HttpBasicAuthentication {
    private static final Logger LOG = Logger.getLogger(HttpUpdateManager.class.getName());

    public static HttpUpdateManager create() {
        ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager();
        manager.setDefaultMaxPerRoute(1);
        manager.setMaxTotal(1);

        HttpParams params = new BasicHttpParams();

        // set timeout
        HttpConnectionParamBean connParamBean = new HttpConnectionParamBean(params);
        connParamBean.setConnectionTimeout(3000);
        connParamBean.setSoTimeout(1000 * 60 * 120);

        HttpClient client = new DefaultHttpClient(manager, params);
        HttpContext context = new BasicHttpContext();

        return new HttpUpdateManager(client, context);
    }

    private HttpSettingsUpdateModel model;

    private HttpEntity responseEntity;

    private boolean force;

    public HttpUpdateManager(HttpClient client, HttpContext context) {
        super(client, context);
    }

    public void updateSettings(boolean force) throws URISyntaxException, IOException {
        this.force = force;
        model = SingleModelManagers.HTTP_SETTINGS.get();

        if(model.getUrl() != null) {
            URI uri = model.getUrl().toURI();

            setUserName(model.getName());
            setPassword(model.getPassword());

            setUri(uri);
            setTargetHost(new HttpHost(this.uri.getHost(), this.uri.getPort(), this.uri.getScheme()));

            execute();
        }
    }

    @Override
    public void execute() throws IOException {
        if(StringUtils.isNotBlank(model.getName())) {
            super.execute();
        }

        try {
            doExecuteInternal();
        } finally {
            EntityUtils.consume(responseEntity);
        }
    }

    public void doExecuteInternal() throws IOException {
        HttpGet get = new HttpGet(uri.getPath());

        HttpResponse response = client.execute(targetHost, get, context);
        StatusLine status = response.getStatusLine();

        if(status.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpPirasoException(status.toString());
        }

        // retrieve revision
        if(response.containsHeader("Etag")) {
            String eTag = response.getFirstHeader("Etag").getValue();

            if(model.getRevision() == null || !StringUtils.equals(model.getRevision(), eTag) || force) {
                model.setRevision(eTag);
                
                updateFromHttp(response);
            }
        } else {
            updateFromHttp(response);
        }
    }
    
    private void updateFromHttp(HttpResponse response) throws IOException {
        responseEntity = response.getEntity();
        ObjectEntrySettings settings = JacksonUtils.MAPPER.readValue(responseEntity.getContent(), ObjectEntrySettings.class);

        List<ImportHandler> options = ImportExportProviderManager.INSTANCE.getImportHandlers();
        for(ImportHandler handler : options) {
            String settingsStr = settings.getModels().get(handler.getOption());

            handler.handle(settingsStr);
        }

        LOG.info(String.format("Updated Settings from '%s' with revision '%s'.", model.getUrl(), model.getRevision()));
        SingleModelManagers.HTTP_SETTINGS.save(model);
    }
}
