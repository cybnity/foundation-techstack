package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.impl.RouterImpl;

/**
 * Router implementation which define the list of routes supported by a UI
 * capability domain.
 */
public class UICapabilitiesDomainRouterImpl extends RouterImpl {

	public UICapabilitiesDomainRouterImpl(Vertx vertx) {
		super(vertx);
		initRoutes(vertx);
	}

	/**
	 * Initialize all the routes supported by this routing service.
	 */
	private void initRoutes(Vertx vertx) {
		// Mount the bridge for all incoming requests
		// according to the channels permitted and authorizations
		init(vertx);
	}

	private AuthenticationProvider authProvider() {
		AuthenticationProvider provider = null;
		// TODO créer le procider de demo d'authorization et décommenter l'assignation
		// de requiredAuthority pour place_orders dans les permittedoptions

		return provider;
	}

	/**
	 * Define input/outputs permitted channels (addresses) and event types rules
	 * regarding the event bus bridge.
	 * 
	 * @param vertx
	 * @return
	 */
	private void init(Vertx vertx) {
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

		// --- DEFINE BASIC AUTH HANDLING ---
		// route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		// AuthenticationHandler basicAuthHandler =
		// BasicAuthHandler.create(authProvider());
		// route("/eventbus/*").handler(basicAuthHandler);

		// --- DEFINE PERMITTED INPUT EVENT TYPES (will look through inbound permitted
		// matches when it is received from client side Javascript to the server) ---
		// Let through any message sent to 'assetcontrol.orderMgr' from the client side
		PermittedOptions inboundPermitted1 = new PermittedOptions().setAddress("assetcontrol.orderMgr");

		// But only if the user is logged in and has the authority "place_orders" (the
		// user must be first logged in and secondly have the required authority)
		// inboundPermitted1.setRequiredAuthority("place_orders");

		// Allow calls to the address 'assetcontrol.cqrs' from the client as long as the
		// messages have an action filed with value 'find'
		// and a a collection field with value 'assets'
		PermittedOptions inboundPermitted2 = new PermittedOptions().setAddress("assetcontrol.cqrs")
				.setMatch(new JsonObject().put("action", "find").put("collection", "assets"));

		// Allow calls to the address 'otherdomain.orderMgr' from the client side
		PermittedOptions inboundPermitted3 = new PermittedOptions().setAddress("otherdomain.orderMgr");

		// --- DEFINE PERMITTED OUTPUT EVENT TYPES (will look through outbound permitted
		// matches before it is sent to the client Vert.x-Web) ---

		// Define what we're going to allow from server -> client
		// Let through any message coming from address 'assetcontrol.cqrs_response'
		PermittedOptions outboundPermitted1 = new PermittedOptions().setAddress("assetcontrol.cqrs_response");

		// Let through any messages from addresses starting with 'news.' (e.g
		// news.assetcontrol, news.otherdomain, etc)
		PermittedOptions outboundPermitted2 = new PermittedOptions().setAddressRegex("news\\..+");

		// --- DEFINE WHAT WE'RE GOING TO ALLOW FROM CLIENT -> SERVER
		SockJSBridgeOptions options = new SockJSBridgeOptions().addInboundPermitted(inboundPermitted1)
				.addInboundPermitted(inboundPermitted2).addInboundPermitted(inboundPermitted3)
				.addOutboundPermitted(outboundPermitted1)
				/**
				 * if ping message doesn’t arrive from client within 5 seconds then the
				 * SOCKET_IDLE bridge event would be triggered
				 **/
				.addOutboundPermitted(outboundPermitted2).setPingTimeout(5000);

		// Create Vert.x application data store
		SharedData sessionStore = vertx.sharedData();
		// Add the several control capabilities supported by the bridge on the router's
		// routes
		route("/eventbus/*")
				.subRouter(sockJSHandler.bridge(options, new UICapabilityHandler(vertx.eventBus(), sessionStore)));
	}

}
