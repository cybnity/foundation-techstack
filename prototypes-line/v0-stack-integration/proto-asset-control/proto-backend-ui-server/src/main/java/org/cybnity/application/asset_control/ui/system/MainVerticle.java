package org.cybnity.application.asset_control.ui.system;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// Route HTTP re­quests to spe­cific re­quest han­dlers

		// Create a Router
		Router router = Router.router(vertx);

		// Mount the handler for all incoming requests at every path and HTTP method
		router.route().handler(context -> {
			// Get the address of the request
			String address = context.request().connection().remoteAddress().toString();
			// Get the query parameter "name"
			MultiMap queryParams = context.queryParams();
			String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
			// Write a json response (re­turns a JSON ob­ject con­tain­ing the ad­dress of
			// the re­quest, the query pa­ra­me­ter name, and a greet­ing mes­sage)
			context.json(new JsonObject().put("name", name).put("address", address).put("message",
					"Hello " + name + " connected from " + address));
		});

		// Create the HTTP server
		getVertx().createHttpServer()
				// Handle every request using the router
				.requestHandler(router)
				// Start http listening
				.listen(8888)
				// Print the port
				.onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
	}
}
