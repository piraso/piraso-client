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

package ard.piraso.ui.base.model;

import ard.piraso.api.entry.RequestEntry;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Request url combo box
 */
public class IOEntryComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private List<RequestEntry> requests;

    private RequestEntry selected;

    public IOEntryComboBoxModel() {
         requests = new ArrayList<RequestEntry> ();
    }

    public void addRequest(RequestEntry entry) {
        synchronized (this) {
            if(!requests.contains(entry)) {
                requests.add(entry);
                int addedIndex = requests.size() - 1;
                fireIntervalAdded(this, addedIndex, addedIndex);
            }
        }
    }

    @Override
    public int getSize() {
        synchronized (this) {
            return CollectionUtils.size(requests);
        }
    }

    @Override
    public Object getElementAt(int index) {
        synchronized (this) {
            return requests.get(index);
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if ((selected != null && !selected.equals(anItem)) ||
            selected == null && anItem != null) {
            if(RequestEntry.class.isInstance(anItem)) {
                selected = (RequestEntry) anItem;
                fireContentsChanged(this, -1, -1);
            }
        }
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }
}
