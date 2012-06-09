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

import ard.piraso.ui.api.manager.FontProviderManager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 *
 */
public final class JTextPaneUtils {

    public static final SimpleAttributeSet CODE_HEADER = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE_BOLD = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE_BOLD_BLUE = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE_UNDERLINE = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE_BLUE = new SimpleAttributeSet();

    public static final SimpleAttributeSet CODE_RED = new SimpleAttributeSet();
    
    public static final SimpleAttributeSet CODE_GRAY = new SimpleAttributeSet();


    static {
        Font baseFont = FontProviderManager.INSTANCE.getEditorDefaultFont();

        setupCode(CODE_HEADER, Color.BLACK, true, baseFont.getSize());
        setupCode(CODE_BOLD, Color.BLACK, true, baseFont.getSize());
        setupCode(CODE, Color.BLACK, false, baseFont.getSize());
        setupCode(CODE_BLUE, Color.BLUE, false, baseFont.getSize());
        setupCode(CODE_BOLD_BLUE, Color.BLUE, true, baseFont.getSize());

        setupCode(CODE_RED, Color.RED, false, baseFont.getSize());
        setupCode(CODE_GRAY, Color.GRAY, false, baseFont.getSize());

        setupCode(CODE_UNDERLINE, Color.BLACK, false, true, baseFont.getSize());

    }

    private static void setupCode(SimpleAttributeSet set, Color color, boolean bold, int fontSize) {
        setupCode(set, color, bold, false, fontSize);
    }

    private static void setupCode(SimpleAttributeSet set, Color color, boolean bold, boolean underline, int fontSize) {
        StyleConstants.setForeground(set, color);

        if(bold) {
            StyleConstants.setBold(set, true);
        }

        if(underline) {
            StyleConstants.setUnderline(set, true);
        }
        Font baseFont = FontProviderManager.INSTANCE.getEditorDefaultFont();
        StyleConstants.setFontFamily(set, baseFont.getFamily());
        StyleConstants.setFontSize(set, fontSize);
    }

    public static void insertText(JEditorPane textPane, String text, SimpleAttributeSet set) throws BadLocationException {
        SimpleAttributeSet copy = (SimpleAttributeSet) set.copyAttributes();

        Font baseFont = FontProviderManager.INSTANCE.getEditorDefaultFont();
        StyleConstants.setFontFamily(copy, baseFont.getFamily());
        StyleConstants.setFontSize(copy, baseFont.getSize());

        textPane.getDocument().insertString(textPane.getDocument().getLength(), text, copy);
    }

    public static void insertHeaderCode(JEditorPane textPane, String text) throws BadLocationException {
        insertText(textPane, text, CODE_HEADER);
    }

    public static void insertBoldCode(JEditorPane textPane, String text) throws BadLocationException {
        insertText(textPane, text, CODE_BOLD);
    }

    public static void insertBoldBlueCode(JEditorPane textPane, String text) throws BadLocationException {
        insertText(textPane, text, CODE_BOLD_BLUE);
    }

    public static void insertCode(JEditorPane textPane, String text) throws BadLocationException {

        if(text.equals("@not-supported")) {
            insertText(textPane, text, CODE_RED);
        } else if(text.equals("@null")) {
            insertText(textPane, text, CODE_BLUE);
        } else {
            insertText(textPane, text, CODE);
        }
    }

    public static void insertUnderline(JEditorPane textPane, String text) throws BadLocationException {

        if(text.equals("@not-supported")) {
            insertText(textPane, text, CODE_RED);
        } else if(text.equals("@null")) {
            insertText(textPane, text, CODE_BLUE);
        } else {
            insertText(textPane, text, CODE_UNDERLINE);
        }
    }

    public static void insertGrayCode(JEditorPane textPane, String text) throws BadLocationException {
        if(text.equals("@not-supported")) {
            insertText(textPane, text, CODE_RED);
        } else if(text.equals("@null")) {
            insertText(textPane, text, CODE_BLUE);
        } else {
            insertText(textPane, text, CODE_GRAY);
        }
    }

    public static void insertText(JEditorPane textPane, String text) throws BadLocationException {
        insertText(textPane, text, null);
    }

    public static void start(JEditorPane textPane) {
        textPane.setSelectionStart(0);
        textPane.setSelectionEnd(0);
    }
}
