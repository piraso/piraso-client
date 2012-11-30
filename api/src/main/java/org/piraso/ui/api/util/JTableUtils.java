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

package org.piraso.ui.api.util;

import org.apache.commons.lang.ArrayUtils;

import javax.swing.*;

/**
 * Table utilities
 */
public class JTableUtils {

    public static void scrollToFirstRow(JTable... tables) {
        scrollTo(tables, 0);
    }

    public static void scrollTo(JTable table, int rowNum) {
        scrollTo(new JTable[] {table}, rowNum);
    }

    public static void scrollTo(JTable[] tables, int rowNum) {
        if(ArrayUtils.isNotEmpty(tables)) {
            for(JTable table : tables) {
                if(table == null) continue;

                table.scrollRectToVisible(table.getCellRect(rowNum, 1, true));
            }
        }
    }
}
