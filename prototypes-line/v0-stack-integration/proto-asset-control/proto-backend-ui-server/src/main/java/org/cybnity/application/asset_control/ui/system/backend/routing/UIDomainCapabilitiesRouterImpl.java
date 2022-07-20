package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.ext.web.impl.RouterImpl;

/**
 * Router implementation which define the list of routes supported by a UI
 * domain regarding its capabilities.
 */
public class UIDomainCapabilitiesRouterImpl extends RouterImpl {

	public UIDomainCapabilitiesRouterImpl(Vertx vertx) {
		super(vertx);
		initRoutes(vertx);
	}

	/**
	 * Initialize all the routes supported by this routing service.
	 */
	private void initRoutes(Vertx vertx) {
		// Mount the bridge for all incoming requests
		// according to the channels permitted and authorizations
		createRoutes(vertx);
	}

	private AuthenticationProvider authProvider() {
		AuthenticationProvider provider = null;
		// TODO créer le procider de demo d'authorization et décommenter l'assignation
		// de requiredAuthority pour place_cqrs dans les permittedoptions

		return provider;
	}

	/**
	 * Define input/outputs permitted channels (addresses) and event types rules
	 * regarding the event bus bridge.
	 * 
	 * @param vertx
	 * @return
	 */
	private void createRoutes(Vertx vertx) {
		// --- DEFINE BASIC AUTH HANDLING ---
		// route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		// AuthenticationHandler basicAuthHandler =
		// BasicAuthHandler.create(authProvider());
		// route("/eventbus/*").handler(basicAuthHandler);

		// --- DEFINE PERMITTED INPUT EVENT TYPES (will look through inbound permitted
		// matches when it is received from client side Javascript to the server) ---
		// Let through any message sent to 'assetcontrol.isAlive' from the client
		// side
		PermittedOptions inboundPermitted1 = new PermittedOptions().setAddress("assetcontrol.isAlive");

		// Allow calls to the address domain channels from the client as long as the
		// messages have an action filed with value (e.g CQRS type as 'query, command')
		PermittedOptions inboundPermitted2 = new PermittedOptions().setAddress("assetcontrol.in")
				.setMatch(new JsonObject().put("type", "QueryEvent"));
		PermittedOptions inboundPermitted3 = new PermittedOptions().setAddress("assetcontrol.in")
				.setMatch(new JsonObject().put("type", "CommandEvent"));

		// But only if the user is logged in and has the authority "place_cqrs" (the
		// user must be first logged in and secondly have the required authority)
		// inboundPermitted2.setRequiredAuthority("place_cqrs");

		// Add the UI static contents route supported by the HTTP layer about url path
		// and static contents directory
		StaticHandler staticWebContentsHandler = StaticHandler.create("static");
		// Configure the static files delivery
		staticWebContentsHandler.setCachingEnabled(false);
		staticWebContentsHandler.setDefaultContentEncoding("UTF-8");
		staticWebContentsHandler.setIndexPage("index.html");
		staticWebContentsHandler.setIncludeHidden(false);
		staticWebContentsHandler.setDirectoryListing(false);
		// Handle static resources
		route("/static/*").handler(staticWebContentsHandler).failureHandler(failure -> {
			System.out.println("static route failure: " + failure.toString());
		});

		// Add possible API routes supported by the HTTP layer as a JSON api
		UICapabilitiesHTTPRouterImpl.initRoutes(this);

		// --- DEFINE PERMITTED OUTPUT EVENT TYPES (will look through outbound permitted
		// matches before it is sent to the client Vert.x-Web) ---

		// Define what we're going to allow from server -> client
		// Let through any message coming from address 'assetcontrol.cqrs_response'
		String cqrsResponseChannel = "assetcontrol.out";
		PermittedOptions outboundPermitted1 = new PermittedOptions().setAddress(cqrsResponseChannel);

		// Let through any messages from addresses starting with 'assets.' (e.g
		// assets.updated, assets.news, etc)
		PermittedOptions outboundPermitted2 = new PermittedOptions().setAddressRegex("assetcontrol.asset\\..+");

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
		// routes about event bus
		SockJSHandlerOptions sockJSHandlerOpts = new SockJSHandlerOptions().setRegisterWriteHandler(true)
				.setOrigin("http://localhost:8080" /**
													 * <protocol>://<domain>[:<port>][</resource>] can be verifier
													 * during handling about received by server domain identifier
													 */
				).setLocalWriteHandler(false)// configure the sockJS instance build, that can be retrieved an stored in
												// local map
				.setRegisterWriteHandler(true);
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, sockJSHandlerOpts);

		// Create a sub-route dedicated to the Asset Control domain
		route("/eventbus/*")
				.subRouter(sockJSHandler.bridge(options, new AreasAssetsProtectionUICapabilityHandler(vertx.eventBus(),
						sessionStore, cqrsResponseChannel, vertx)));
		// Add other domain endpoints routers (per cockpit capability boundary)

	}

}
