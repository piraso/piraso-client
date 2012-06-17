package ard.piraso.ui.base.views;

import ard.piraso.api.entry.MethodCallEntry;
import ard.piraso.api.entry.ObjectEntry;
import ard.piraso.api.entry.ObjectEntryUtils;
import ard.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import javax.swing.*;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * Method call view component
 */
@ActionID(category = "Window", id = "ard.piraso.ui.base.MethodCallViewTopComponent")
@ActionReference(path = "Menu/Window", position = 333)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.base//MethodCallView//EN", autostore = false)
@TopComponent.Description(preferredID = "MethodCallViewTopComponent", iconBase="ard/piraso/ui/base/icons/method.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_MethodViewAction", preferredID = "MethodCallViewTopComponent")
public class MethodCallViewTopComponent extends BaseEntryViewTopComponent<MethodCallEntry> {

    public MethodCallViewTopComponent() {
        super(MethodCallEntry.class, "Method");

        setName(NbBundle.getMessage(MethodCallViewTopComponent.class, "CTL_MethodViewTopComponent"));
        setToolTipText(NbBundle.getMessage(MethodCallViewTopComponent.class, "HINT_MethodViewTopComponent"));
    }

    @Override
    protected void populateMessage(JTextPane txtMessage, MethodCallEntry entry) throws Exception {
        insertHeaderCode(txtMessage, currentEntry.getGenericString());
        insertCode(txtMessage, "\n");

        if(currentEntry.getArguments() != null && currentEntry.getArguments().length > 0) {
            for(int i = 0; i < currentEntry.getArguments().length; i++) {
                ObjectEntry argument = currentEntry.getArguments()[i];
                insertKeyword(txtMessage, String.format("\nArgument[%d]: ", i));
                insertCode(txtMessage, ObjectEntryUtils.toString(argument));
            }
        }

        if(!currentEntry.getReturnClassName().equals("void")) {
            insertKeyword(txtMessage, "\nReturn: ");
            insertCode(txtMessage, ObjectEntryUtils.toString(currentEntry.getReturnedValue()));
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
