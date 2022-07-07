package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;

/**
 * Router implementation which define the list of routes supported by a UI
 * capability scope.
 */
public class UICapabilitiesHTTPRouterImpl extends RouterImpl {

	public UICapabilitiesHTTPRouterImpl(Vertx vertx) {
		super(vertx);
		initRoutes();
	}

	/**
	 * Initialize all the routes supported by this routing service.
	 */
	private void initRoutes() {
		// Mount the handlers for all incoming requests at every path and HTTP method
		// via creation of the several routes supported

		get("/assetcontrol/").handler(context -> {
			// This handler is called for any GET request to a path starting with
			// /assetcontrol/
			sendJSONUICapabilityResponse(context, "assetcontrol");
		});

		get().handler(context -> {
			// This handler is called for any GET request
			sendJSONUICapabilityResponse(context, "undefined");
		});

	}

	/**
	 * Simulate a JSON answer provided on HTTP protocol by an API service.
	 * 
	 * @param context
	 * @param calledResourceName
	 */
	private void sendJSONUICapabilityResponse(RoutingContext context, String calledResourceName) {
		// Get the address of the request
		String address = context.request().connection().remoteAddress().toString();
		// Get the query parameter "name"
		MultiMap queryParams = context.queryParams();
		String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
		// Write a json response (re­turns a JSON ob­ject con­tain­ing the ad­dress of
		// the re­quest, the query pa­ra­me­ter name, and a greet­ing mes­sage)
		context.json(new JsonObject().put("name", name).put("address", address).put("message",
				"Hello " + name + " (connected from " + address) + "), welcome on the called resource ("
				+ calledResourceName + ")");
	}
}
