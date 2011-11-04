/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ard.piraso.ui.base;

import ard.piraso.ui.api.PreferenceProvider;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;

import java.util.Collection;
import java.util.logging.Logger;

public class Installer extends ModuleInstall {
    private static final Logger LOG = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        Collection<? extends PreferenceProvider> providers = Lookup.getDefault().lookupAll(PreferenceProvider.class);
        
        for(PreferenceProvider provider : providers) {
            LOG.info(provider.getClass().getName());
        }
    }
}
