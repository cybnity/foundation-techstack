package org.cybnity.application.asset_control.ui.system.backend.capabilities;

import org.cybnity.application.asset_control.ui.system.backend.capabilities.AssetControlVerticle;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server component from
 * IDE.
 *
 */
public class AssetControlCapabilitiesLauncher {

	public AssetControlCapabilitiesLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", AssetControlVerticle.class.getName());
	}

}
