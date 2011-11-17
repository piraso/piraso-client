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

package ard.piraso.ui.api.util;

import org.openide.awt.NotificationDisplayer;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Notification utils
 */
public class NotificationUtils {

    private static ImageIcon INFO = new ImageIcon(NotificationUtils.class.getResource("/ard/piraso/ui/api/icons/info.png"));

    public static void info(String str) {
        info("Notification", str);
    }

    public static void info(String title, String str) {
        info(title, str, null);
    }

    public static void info(String title, String str, ActionListener listener) {
        NotificationDisplayer.getDefault().notify(title, INFO, str, listener);
    }
}