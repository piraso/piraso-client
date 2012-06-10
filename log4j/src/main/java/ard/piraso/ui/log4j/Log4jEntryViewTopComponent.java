package ard.piraso.ui.log4j;

import ard.piraso.api.log4j.Log4jEntry;
import ard.piraso.ui.api.views.BaseEntryViewTopComponent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

import java.text.SimpleDateFormat;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;


@ActionID(category = "Window", id = "ard.piraso.ui.log4j.Log4jEntryViewTopComponent")
@ActionReference(path = "Menu/Window", position = 336)
@ConvertAsProperties(dtd = "-//ard.piraso.ui.log4j//Log4jEntryView//EN", autostore = false)
@TopComponent.Description(preferredID = "Log4jEntryViewTopComponent", iconBase="ard/piraso/ui/log4j/icons/log4j.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@TopComponent.OpenActionRegistration(displayName = "#CTL_Log4jViewAction", preferredID = "Log4jEntryViewTopComponent")
public class Log4jEntryViewTopComponent extends BaseEntryViewTopComponent<Log4jEntry> {

    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    public Log4jEntryViewTopComponent() {
        super(Log4jEntry.class, "Log4j");

        setName(NbBundle.getMessage(Log4jEntryViewTopComponent.class, "CTL_Log4jViewTopComponent"));
        setToolTipText(NbBundle.getMessage(Log4jEntryViewTopComponent.class, "HINT_Log4jViewTopComponent"));
    }

    @Override
    protected void populateMessage(Log4jEntry entry) throws Exception {
        insertBoldCode(txtMessage, "Level: ");
        insertCode(txtMessage, currentEntry.getLogLevel());

        if(CollectionUtils.isNotEmpty(currentEntry.getGroup().getGroups())) {
            insertBoldCode(txtMessage, "\nLogger: ");
            insertCode(txtMessage, currentEntry.getGroup().getGroups().iterator().next());
        }

        insertBoldCode(txtMessage, "\nTime: ");
        insertCode(txtMessage, FORMATTER.format(currentEntry.getTime()));

        insertBoldCode(txtMessage, "\nThread: ");
        insertUnderline(txtMessage, "id");
        insertCode(txtMessage, String.format(": %d, ", currentEntry.getThreadId()));
        insertUnderline(txtMessage, "name");
        insertCode(txtMessage, String.format(": %s, ", currentEntry.getThreadName()));
        insertUnderline(txtMessage, "priority");
        insertCode(txtMessage, String.format(": %d", currentEntry.getThreadPriority()));

        if(StringUtils.isNotBlank(currentEntry.getNdc())) {
            insertBoldCode(txtMessage, "\nNDC: ");
            insertCode(txtMessage, currentEntry.getNdc());
        }

        if(currentEntry.getMdc() != null && !StringUtils.equalsIgnoreCase(currentEntry.getMdc(), "null")) {
            insertBoldCode(txtMessage, "\nMDC: ");
            insertCode(txtMessage, currentEntry.getMdc());
        }

        insertBoldCode(txtMessage, "\nMessage: ");
        insertCode(txtMessage, currentEntry.getMessage());
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
