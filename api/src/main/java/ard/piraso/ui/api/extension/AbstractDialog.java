package ard.piraso.ui.api.extension;

import org.openide.windows.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Base class for dialog
 */
public abstract class AbstractDialog extends JDialog {

    protected ActionListener disposeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
    };

    protected AbstractDialog() {
        super(WindowManager.getDefault().getMainWindow(), true);
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().registerKeyboardAction(disposeActionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
