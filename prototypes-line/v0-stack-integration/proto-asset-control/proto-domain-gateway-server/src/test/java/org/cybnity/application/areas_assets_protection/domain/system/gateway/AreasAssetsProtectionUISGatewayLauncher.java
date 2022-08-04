package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server component from
 * IDE.
 *
 */
public class AreasAssetsProtectionUISGatewayLauncher {

	public AreasAssetsProtectionUISGatewayLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", AreasAssetsProtectionUISGateway.class.getName());
	}

}
