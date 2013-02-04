package org.piraso.ui.api.bridge;

import org.mortbay.jetty.Server;

public class PirasoServerBridge {

    private static final Server SERVER = new Server(14344);

    static {
        SERVER.addHandler(new BridgeHandler());
    }

    public static void start() throws Exception {
        SERVER.start();
    }

    public static void stop() throws Exception {
        SERVER.stop();
    }

    public static boolean isAlive() throws Exception {
        return SERVER.isStarted() && SERVER.isRunning();
    }
}
