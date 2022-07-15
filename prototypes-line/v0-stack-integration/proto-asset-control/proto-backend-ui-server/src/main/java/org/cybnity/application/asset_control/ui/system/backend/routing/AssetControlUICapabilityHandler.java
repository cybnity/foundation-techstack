package org.cybnity.application.asset_control.ui.system.backend.routing;

import org.cybnity.infrastructure.common.event.Event;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of UI events and interactions regarding one domain (asset control)
 */
public class AssetControlUICapabilityHandler extends EventBusBridgeHandler {

	private String cqrsResponseChannel;

	public AssetControlUICapabilityHandler(EventBus eventBus, SharedData sessionStore, String cqrsResponseChannel) {
		super(eventBus, sessionStore);
		this.cqrsResponseChannel = cqrsResponseChannel;
	}

	/**
	 * UI capability entry point regarding Vert.x bus events regarding UI
	 * capabilities.
	 */
	@Override
	protected void publishToUsersInteractionsSpace(BridgeEvent event) {
		// regarding ui-event, domain-event, command-event, etc. as CYBNITY bridge
		System.out.println("Event entry from client side that need to be processed by UI Interaction Logic...");
		JsonObject message = event.getRawMessage();
		if (message != null) {
			// Read the event and contents requiring for context-based routing
			String eventType = message.getString("type", null);
			String routingAddress = message.getString("address", null);
			JsonObject body = message.getJsonObject("body", null);
			if (eventType != null) {
				// Interpretation of the transport bridge event to identify the CQRS event to
				// process
				Event evt = null;
				JsonObject transactionResult = null;
				// - quality of event received and integrity WITHOUT TRANSFORMATION of event
				// message
				// - translation into supported event types by the UI interactions layer (Redis
				// space) when need

				if ("send".equalsIgnoreCase(eventType)) {
					// Confirm notification about performed routing
					transactionResult = new JsonObject();
					transactionResult.put("status", "processing");

					String correlationId = (body != null) ? body.getString("correlationId", null) : null;
					String eventId = body.getString("id");
					if (correlationId != null) {
						transactionResult.put("correlationId", correlationId);
					}
					// - the identification of channel of space where to push the event to process
					// - the push of event to space for processing by Application layer

					// Send event into UI space's channel
					System.out.println("Event forwared to User Interactions Space: " + body);
					System.out.println("with event (" + eventId + ") in status: " + transactionResult);
				}

				if ("publish".equalsIgnoreCase(eventType)) {

				}
				/*
				 * Optional<Integer> counter = repository.get(); if (counter.isPresent()) {
				 * Integer value = counter.get() + 1; repository.update(value);
				 * eventBus.publish("out", value); } else { Integer value = 1;
				 * repository.update(value); eventBus.publish("out", value); }
				 */

				if (transactionResult != null)
					bus().send(cqrsResponseChannel, transactionResult);
			}
		}
	}

}
