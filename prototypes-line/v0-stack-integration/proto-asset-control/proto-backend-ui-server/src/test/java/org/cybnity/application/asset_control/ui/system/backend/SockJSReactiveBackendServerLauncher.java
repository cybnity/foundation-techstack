package org.cybnity.application.asset_control.ui.system.backend;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server component from
 * IDE.
 *
 */
public class SockJSReactiveBackendServerLauncher {

	public SockJSReactiveBackendServerLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", SockJSReactiveBackendServer.class.getName());
	}

}
