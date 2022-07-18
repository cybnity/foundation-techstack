package org.cybnity.application.asset_control.ui.system.backend;

import org.cybnity.application.asset_control.ui.system.backend.routing.CapabilityRouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class UICapabilityHTTPServer extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// Route HTTP re­quests to spe­cific re­quest han­dlers

		// Create a Router initialized to support capability routes
		Router router = CapabilityRouter.httpRouter(vertx);

		// Create the HTTP server
		getVertx().createHttpServer()
				// Handle every request using the router
				.requestHandler(router)
				// Start HTTP listening
				.listen(8888)
				// Print the port
				.onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
	}
}
