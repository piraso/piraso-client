package ard.piraso.ui.api;

import java.net.URL;

/**
 * SVNSync model
 */
public class SVNSyncModel {

    private URL url;

    private String name;

    private String password;

    private boolean loadOnStartup = true;

    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
