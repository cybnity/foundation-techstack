package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface CapabilityRouter extends Router {

	/**
	 * Create a router supporting HTTP protocol.
	 *
	 * @param vertx the Vert.x instance
	 * @return A router including routes with HTTP handlers.
	 */
	static Router httpRouter(Vertx vertx) {
		return new UICapabilitiesHTTPRouterImpl(vertx);
	}

	/**
	 * Create a router supporting SockJS protocol.
	 * 
	 * @param vertx a Vert.x instance.
	 * @return A router including routes with SockJS handlers.
	 */
	static Router sockjsRouter(Vertx vertx) {
		return new UIDomainCapabilitiesRouterImpl(vertx);
	}
}
