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

package org.piraso.ui.log4j.provider;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Log4j preferences model
 */
public class Log4jPreferencesModel {

    private List<Child> preferences = new ArrayList<Child>();

    public List<Child> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Child> preferences) {
        this.preferences = preferences;
    }

    public void add(String logger, String description) {
        Validate.notEmpty(logger, "logger should not be empty");
        Validate.notEmpty(description, "description should not be empty");

        preferences.add(new Child(logger, description));
        Collections.sort(preferences);
    }

    public static final class Child implements Comparable<Child> {
        private String logger;

        private String description;

        public Child() {
        }

        public Child(String logger, String description) {
            this.description = description;
            this.logger = logger;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
        }

        @Override
        public int compareTo(Child o) {
            return description.compareTo(o.description);
        }
    }
}
