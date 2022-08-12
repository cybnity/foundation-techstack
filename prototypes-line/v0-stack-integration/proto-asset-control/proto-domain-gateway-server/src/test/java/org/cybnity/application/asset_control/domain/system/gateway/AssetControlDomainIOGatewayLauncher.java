package org.cybnity.application.asset_control.domain.system.gateway;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server component from
 * IDE.
 *
 */
public class AssetControlDomainIOGatewayLauncher {

	public AssetControlDomainIOGatewayLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", AssetControlDomainIOGateway.class.getName());
	}

}
