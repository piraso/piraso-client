package ard.piraso.ui.spring.provider;

import ard.piraso.api.entry.Entry;
import ard.piraso.api.spring.SpringPreferenceEnum;
import ard.piraso.ui.api.EntryRowRenderingProvider;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.*;
import java.awt.*;

/**
 * spring row rendering
 */
@ServiceProvider(service=EntryRowRenderingProvider.class)
public class SpringEntryRowRenderingProviderImpl implements EntryRowRenderingProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return SpringPreferenceEnum.REMOTING_ENABLED.getPropertyName().equals(entry.getLevel());
    }

    @Override
    public void render(JLabel cell, Entry entry) {
        cell.setBackground(new Color(0xBAEEBA));
        cell.setForeground(new Color(0x008000));
    }
}
