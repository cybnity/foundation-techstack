package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of UI events and interactions
 */
public class UICapabilityHandler extends EventBusBridgeHandler {

	private String cqrsResponseChannel;

	public UICapabilityHandler(EventBus eventBus, SharedData sessionStore, String cqrsResponseChannel) {
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
		// Manage :
		// - quality of event received and integrity
		// - translation into supported event types by the UI interactions layer (Redis
		// space)
		// - the identification of channel of space where to push the event to process
		// - the push of event to space for processing by Application layer

		// Read the event and contents

		// Manage the treatment to execute

		// Publish result/callback event in event bus's channel
		bus().send(cqrsResponseChannel, new JsonObject().put("key", "value"));

		/*
		 * Optional<Integer> counter = repository.get(); if (counter.isPresent()) {
		 * Integer value = counter.get() + 1; repository.update(value);
		 * eventBus.publish("out", value); } else { Integer value = 1;
		 * repository.update(value); eventBus.publish("out", value); }
		 */
	}

}
