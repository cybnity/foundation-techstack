package org.cybnity.application;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server gateways from IDE.
 *
 */
public class MultipleDomainGatewayLauncher {

	public MultipleDomainGatewayLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", MultipleDomainGateway.class.getName());
	}

}
