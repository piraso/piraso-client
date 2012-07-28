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

package ard.piraso.ui.api;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author adeleon
 */
public class WorkingSetSettings {

    private List<Child> preferences = new ArrayList<Child>();

    public List<Child> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Child> preferences) {
        this.preferences = preferences;
    }

    public void add(String name, String regex) {
        Validate.notEmpty(name, "name should not be empty");
        Validate.notEmpty(regex, "regex should not be empty");

        Pattern.compile(regex);

        preferences.add(new Child(name, regex));
        Collections.sort(preferences);
    }

    public String getRegex(String name) {
        for(Child child : preferences) {
            if(child.getName().equals(name)) {
                return child.getRegex();
            }
        }

        return null;
    }
    
    public static final class Child implements Comparable<Child> {
        private String name;

        private String regex;

        public Child() {
        }

        public Child(String name, String regex) {
            this.regex = regex;
            this.name = name;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Child o) {
            return regex.compareTo(o.regex);
        }
    }
}
