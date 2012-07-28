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

package ard.piraso.ui.api.views;

import java.util.Comparator;

/**
 *
 * @author adeleon
 */
public class NameValue {

    public final static Comparator<NameValue> ASCENDING = new Comparator<NameValue>() {
        @Override
        public int compare(NameValue o1, NameValue o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    public final static Comparator<NameValue> DESCENDING = new Comparator<NameValue>() {
        @Override
        public int compare(NameValue o1, NameValue o2) {
            return o2.getName().compareTo(o1.getName());
        }
    };

    private String name;
    
    private String value;
    
    public NameValue() {}
    
    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
