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

package org.piraso.ui.api.manager;

import org.piraso.ui.api.WithNameModel;

import java.io.IOException;
import java.util.List;

/**
 * model manager.
 */
public interface ModelManager<T extends WithNameModel> extends BaseModelManager {
    List<String> getNames();
    boolean isEmpty();
    int size();
    boolean contains(String name);
    void visit(ModelVisitor<T> visitor);
    T get(String name);
    void save(T model) throws IOException;
    void remove(String name) throws IOException;
    void clear() throws IOException;
}
