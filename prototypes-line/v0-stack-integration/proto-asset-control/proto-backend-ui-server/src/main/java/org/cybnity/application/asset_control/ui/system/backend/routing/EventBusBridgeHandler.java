package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of bus events regarding frontend/backend integration layer.
 */
public abstract class EventBusBridgeHandler implements Handler<BridgeEvent> {
	private final EventBus bus;
	private SharedData sessionStore;

	public EventBusBridgeHandler(EventBus eventBus, SharedData sessionStore) {
		this.bus = eventBus;
		this.sessionStore = sessionStore;
	}

	@Override
	public void handle(BridgeEvent event) {
		if (event.type() == BridgeEventType.SOCKET_IDLE) {
			// This even will occur when SockJS socket is on idle for longer period of time
			// than initially configured
			System.out.println("Socket IDLE occured");
		} else if (event.type() == BridgeEventType.SOCKET_CLOSED) {
			// This event will occur when a SockJS socket is closed
			System.out.println("Socket is closed");
		} else if (event.type() == BridgeEventType.SOCKET_ERROR) {
			// This event will occur when an underlying transport errors
			System.out.println("Socket error occured: " + event.getRawMessage().encode());
		}

		if (event.type() == BridgeEventType.PUBLISH || event.type() == BridgeEventType.RECEIVE) {
			if (event.getRawMessage().getString("body").contentEquals("violation")) {
				// Reject event where a specific word exist in body (e.g security, conformity
				// violation)
				System.out.println("Event rejected caused by content violation");
				event.complete(false);
				return;
			}
			if (event.type() == BridgeEventType.RECEIVE) {
				// a message is attempted to be delivered from the server to the client
				// Add security tracking about event transmitted to client side
				System.out.println("Event delivered from server to client side");
			}
		}

		if (event.type() == BridgeEventType.PUBLISH || event.type() == BridgeEventType.SEND) {
			// A message is attempted to be published from the client to the server
			// Delegate to inputs handling about UI capabilities treatment/control by the UI
			// interaction logic
			publishToUsersInteractionsSpace(event);
		}

		// Complete the event delegation status
		if (!event.tryComplete())
			event.complete(true);
	}

	/**
	 * Publish an interaction event (e.g ui-event, domain-event, command-event)
	 * coming from frontend side, to User Interactions Space for
	 * analysis/processing.
	 * 
	 * @param event To treat.
	 */
	protected abstract void publishToUsersInteractionsSpace(BridgeEvent event);

}
