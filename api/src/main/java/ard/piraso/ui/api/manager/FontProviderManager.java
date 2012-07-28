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

package ard.piraso.ui.api.manager;

import java.awt.*;
import java.util.Arrays;

/**
 * Font provider
 */
public enum FontProviderManager {
    INSTANCE;

    private Font DEFAULT_FONT;

    private FontProviderManager() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = ge.getAvailableFontFamilyNames();
        Arrays.sort(fonts);
        if (Arrays.binarySearch(fonts, "Menlo") >= 0) {
            DEFAULT_FONT = new Font("Menlo", Font.PLAIN, 12);
        } else if (Arrays.binarySearch(fonts, "Monospaced") >= 0) {
            DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 12);
        } else if (Arrays.binarySearch(fonts, "Courier New") >= 0) {
            DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 12);
        } else if (Arrays.binarySearch(fonts, "Courier") >= 0) {
            DEFAULT_FONT = new Font("Courier", Font.PLAIN, 12);
        }
    }

    public Font getDefaultFont() {
        return DEFAULT_FONT;
    }

    public Font getEditorDefaultFont() {
        return DEFAULT_FONT.deriveFont((float) SingleModelManagers.GENERAL_SETTINGS.get().getFontSize());
    }

    public Font getBoldEditorDefaultFont() {
        Font font = getEditorDefaultFont();

        return font.deriveFont(Font.BOLD);
    }
}
