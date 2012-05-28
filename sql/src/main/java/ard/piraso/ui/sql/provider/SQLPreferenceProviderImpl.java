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

package ard.piraso.ui.sql.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.sql.SQLPreferenceEnum;
import ard.piraso.ui.api.NCPreferenceProperty;
import ard.piraso.ui.api.PreferenceProperty;
import ard.piraso.ui.api.PreferenceProvider;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author adleon
 */
@ServiceProvider(service=PreferenceProvider.class)
public class SQLPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        NCPreferenceProperty conn = new NCPreferenceProperty(SQLPreferenceEnum.CONNECTION_ENABLED.getPropertyName(), Boolean.class, true);
        NCPreferenceProperty connMC = new NCPreferenceProperty(SQLPreferenceEnum.CONNECTION_METHOD_CALL_ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty prepared = new NCPreferenceProperty(SQLPreferenceEnum.PREPARED_STATEMENT_ENABLED.getPropertyName(), Boolean.class, true);
        NCPreferenceProperty viewSQL = new NCPreferenceProperty(SQLPreferenceEnum.VIEW_SQL_ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty preparedMC = new NCPreferenceProperty(SQLPreferenceEnum.PREPARED_STATEMENT_METHOD_CALL_ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty resultSet = new NCPreferenceProperty(SQLPreferenceEnum.RESULTSET_ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty resultSetMC = new NCPreferenceProperty(SQLPreferenceEnum.RESULTSET_METHOD_CALL_ENABLED.getPropertyName(), Boolean.class);

        conn.setParent(true);
        connMC.setChild(true);
        connMC.addDependents(conn);

        prepared.setParent(true);
        prepared.addDependents(conn);

        viewSQL.setChild(true);
        viewSQL.addDependents(conn, prepared);

        preparedMC.setChild(true);
        preparedMC.addDependents(conn, prepared);

        resultSet.setParent(true);
        resultSet.addDependents(conn, prepared);

        resultSetMC.setChild(true);
        resultSetMC.addDependents(resultSet, prepared, conn);

        return Arrays.asList(conn, connMC, prepared, viewSQL, preparedMC, resultSet, resultSetMC);
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(SQLPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        return getMessage(entry.getLevel() + ".short");
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    @Override
    public String getName() {
        return getMessage("sql.name");
    }

    @Override
    public String getShortName() {
        return getMessage("sql.name.short");
    }

    @Override
    public boolean isHorizontalChildLayout() {
        return false;
    }

    @Override
    public boolean isPreviewLastChildOnly() {
        return false;
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
