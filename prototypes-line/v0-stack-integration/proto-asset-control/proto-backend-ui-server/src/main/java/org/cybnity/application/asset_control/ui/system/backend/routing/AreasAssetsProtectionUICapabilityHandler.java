package org.cybnity.application.asset_control.ui.system.backend.routing;

import org.cybnity.application.asset_control.ui.system.backend.infrastructure.impl.redis.RedisOptionFactory;
import org.cybnity.infrastructure.common.event.Event;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

/**
 * Handler of UI events and interactions regarding one boundary of cockpit
 * capabilities (areas & assets protection)
 */
public class AreasAssetsProtectionUICapabilityHandler extends EventBusBridgeHandler {

	private String cqrsResponseChannel;
	private Vertx context;
	private RedisOptions redisOpts;
	private String refName;

	public AreasAssetsProtectionUICapabilityHandler(EventBus eventBus, SharedData sessionStore,
			String cqrsResponseChannel, Vertx vertx) {
		super(eventBus, sessionStore);
		this.cqrsResponseChannel = cqrsResponseChannel;
		this.context = vertx;
		// Define Redis options allowing capabilities to discuss with users interactions
		// space (don't use pool that avoid possible usable of channels subscription by
		// handlers)
		redisOpts = RedisOptionFactory.createUsersInteractionsSpaceOptions();
	}

	/**
	 * UI capability entry point regarding Vert.x bus events regarding UI
	 * capabilities.
	 */
	@Override
	protected void toUsersInteractionsSpace(BridgeEvent event) throws Exception {
		// regarding ui-event, domain-event, command-event, etc. as CYBNITY bridge
		System.out.println("Event entry from client side that need to be processed by UI Interaction Logic...");
		JsonObject message = event.getRawMessage();
		if (message != null) {
			// Read the event and contents requiring for context-based routing
			String eventType = message.getString("type", null);
			final String routingAddress = message.getString("address", null);
			final JsonObject body = message.getJsonObject("body", null);
			if (eventType != null) {
				// - quality of event received and integrity WITHOUT TRANSFORMATION of event
				// message
				// - translation into supported event types by the UI interactions layer (Redis
				// space) when need

				if ("send".equalsIgnoreCase(eventType)) {
					// - the identification of channel of space where to push the event to process
					// - the push of event to space for processing by Application layer

					// Collaborate with users interactions space
					Redis.createClient(context, redisOpts).connect().onSuccess(conn -> {
						String correlationId = (body != null) ? body.getString("correlationId", null) : null;
						String eventId = (body != null) ? body.getString("id", null) : null;

						// Send event into UI space's channel
						conn.send(Request.cmd(Command.SET, /* key is event id */ eventId, /*
																							 * key value is the json
																							 * string
																							 */
								body.toString())).onSuccess(info -> {
									// Confirm notification about performed routing
									JsonObject transactionResult = new JsonObject();
									transactionResult.put("status", "processing");
									if (correlationId != null) {
										transactionResult.put("correlationId", correlationId);
									}

									System.out.println("Event forwared from (address: " + routingAddress
											+ ") to User Interactions Space: " + body);
									System.out.println("with event (" + eventId + ") in status: " + transactionResult);

									// Notify the front side via the event bus
									bus().send(cqrsResponseChannel, transactionResult);
								}).onFailure(error -> {
									System.out
											.println(refName + " connection to UIS broker failed: " + error.getCause());
									error.printStackTrace();
								});
					}).onFailure(fail -> {
						System.out.println(refName + " UIS broker connection failed: ");
						fail.printStackTrace();
					});

				}

				if ("publish".equalsIgnoreCase(eventType)) {
					throw new Exception("missing implementation!");
				}
				/*
				 * Optional<Integer> counter = repository.get(); if (counter.isPresent()) {
				 * Integer value = counter.get() + 1; repository.update(value);
				 * eventBus.publish("out", value); } else { Integer value = 1;
				 * repository.update(value); eventBus.publish("out", value); }
				 */

			}
		}
	}

}
