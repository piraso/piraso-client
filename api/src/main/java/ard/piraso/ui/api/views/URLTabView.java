package ard.piraso.ui.api.views;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.entry.MessageAwareEntry;
import ard.piraso.ui.api.manager.FontProviderManager;
import ard.piraso.ui.api.util.URLParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openide.ErrorManager;

import javax.swing.text.BadLocationException;
import java.net.URI;
import java.util.List;

import static ard.piraso.ui.api.util.JTextPaneUtils.*;

/**
 * URL tab view
 */
public class URLTabView extends FilteredJTextPaneTabView<MessageAwareEntry> {
    /**
     * Creates new form MessageAwareEntry
     *
     * @param entry       the entry
     */
    public URLTabView(MessageAwareEntry entry) {
        super(entry, "URL contents is now copied to clipboard.");
    }

    @Override
    public void refreshView(Entry entry) {
        MessageAwareEntry m = (MessageAwareEntry) entry;

        List<URI> urls = URLParser.parseUrls(m.getMessage());
        btnFilter.setVisible(false);
        btnCopy.setEnabled(true);

        try {
            txtEditor.setFont(FontProviderManager.INSTANCE.getEditorDefaultFont());
            btnCopy.setEnabled(true);
            txtEditor.setText("");
            int i = 0;
            for(URI uri : urls) {
                try {
                    insertKeyword(txtEditor, String.format("\n\n[%d] Scheme: ", ++i));
                    insertCode(txtEditor, uri.getScheme());

                    insertKeyword(txtEditor, "\n    Host: ");
                    insertCode(txtEditor, uri.getHost());

                    if(uri.getPort() > 80) {
                        insertKeyword(txtEditor, "\n    Port: ");
                        insertCode(txtEditor, String.valueOf(uri.getPort()));
                    }

                    insertKeyword(txtEditor, "\n    Path: ");
                    insertCode(txtEditor, uri.getPath());

                    String queryString = uri.getQuery();

                    if(StringUtils.isNotBlank(queryString)) {
                        List<NameValuePair> params = URLEncodedUtils.parse(uri, "UTF-8");

                        insertKeyword(txtEditor, "\n    Query String: ");

                        for(NameValuePair nvp : params) {
                            insertCode(txtEditor, "\n       ");
                            insertIdentifier(txtEditor, nvp.getName() + ": ");
                            insertCode(txtEditor, nvp.getValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            insertText(txtEditor, "\n\n");
            start(txtEditor);
        } catch (BadLocationException e) {
            btnCopy.setEnabled(false);
            ErrorManager.getDefault().notify(e);
        }
    }

    @Override
    protected void btnFilterClickHandle() {
    }
}
