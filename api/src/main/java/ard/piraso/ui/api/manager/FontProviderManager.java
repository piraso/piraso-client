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
}
