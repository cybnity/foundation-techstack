package org.cybnity.application.asset_control.domain.system;

import io.vertx.core.Launcher;

/**
 * Utility class for easy debugging and running of the server component from
 * IDE.
 *
 */
public class AssetControlRTComputationUnitLauncher {

	public AssetControlRTComputationUnitLauncher() {
	}

	public static void main(String[] args) {
		Launcher.executeCommand("run", AssetControlComputationUnit.class.getName());
	}

}
