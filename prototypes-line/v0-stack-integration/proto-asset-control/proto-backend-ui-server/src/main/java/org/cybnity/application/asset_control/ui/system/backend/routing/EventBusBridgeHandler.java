package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of bus events exposed between frontend and backend as integration
 * layer.
 */
public abstract class EventBusBridgeHandler implements Handler<BridgeEvent> {
	private final EventBus bus;
	private SharedData sessionStore;

	public EventBusBridgeHandler(EventBus eventBus, SharedData sessionStore) {
		this.bus = eventBus;
		this.sessionStore = sessionStore;
	}

	/**
	 * Get the session store.
	 * 
	 * @return A store or null.
	 */
	protected SharedData sessionStore() {
		return this.sessionStore;
	}

	/**
	 * Get the connected event bus.
	 * 
	 * @return A bus.
	 */
	protected EventBus bus() {
		return this.bus;
	}

	/**
	 * Check the Access Control Layer (ACL) conditions allowing or rejecting the
	 * processing of an event. This method is call by handle(BridgeEvent event)
	 * method for each even handled, before to delegate treatment via a call to
	 * toUsersInteractionsSpace(BridgeEvent event).
	 * 
	 * @param event The subject to control.
	 * @return True if authorized (e.g command permitted according to an authorized
	 *         RBAC result). False when resource access not authorized.
	 * @throws SecurityException When a security problem (e.g missing security
	 *                           information required for ACL check) is detected on
	 *                           the controlled event.
	 */
	protected abstract boolean authorizedResourceAccess(BridgeEvent event) throws SecurityException;

	@Override
	public void handle(BridgeEvent event) {
		if (event != null) {
			if (event.type() == BridgeEventType.SOCKET_IDLE) {
				// This even will occur when SockJS socket is on idle for longer period of time
				// than initially configured
				System.out.println("Socket IDLE occured");
			} else if (event.type() == BridgeEventType.SOCKET_CREATED) {
				// This event will occur when a SockJS socket is created
				// System.out.println("Socket is created");
			} else if (event.type() == BridgeEventType.SOCKET_CLOSED) {
				// This event will occur when a SockJS socket is closed
				// System.out.println("Socket is closed");
			} else if (event.type() == BridgeEventType.SOCKET_ERROR) {
				// This event will occur when an underlying transport errors
				System.out.println("Socket error occured: " + event.getRawMessage().encode());
			}

			// --- CHECK THE CONFORMITY OF THE EVENT STRUCTURE ---
			JsonObject message = event.getRawMessage();

			if (event.type() == BridgeEventType.PUBLISH || event.type() == BridgeEventType.RECEIVE) {
				if (message.getString("body") != null && message.getString("body").contentEquals("violation")) {
					// Reject event where a specific word exist in body (e.g security, conformity
					// violation)
					System.out.println("Event rejected caused by content violation");
					event.complete(false);
					return;
				}
				if (event.type() == BridgeEventType.RECEIVE) {
					// a message is attempted to be delivered from the server to the client
					// Add security tracking about event transmitted to client side
					// System.out.println("Event delivered from server to client side");
				}
			}

			if (event.type() == BridgeEventType.PUBLISH || event.type() == BridgeEventType.SEND) {
				// A message is attempted to be published from the client to the server (PUBLISH
				// = too all the handler; SEND = only to one of the handler instances)

				// --- CHECK THE AUTHORIZATION OF EVENT TREATMENT ACCORDING TO ACL ---
				if (authorizedResourceAccess(event)) {
					try {
						// Delegate to inputs handling about UI capabilities treatment/control by the UI
						// interaction logic
						toUsersInteractionsSpace(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Event rejected caused by unauthorized processing");
					event.complete(false);
					return;
				}
			}

			// Complete the event delegation status
			event.complete(true);
		}
	}

	/**
	 * Transmit an interaction event (e.g ui-event, domain-event, command-event)
	 * coming from frontend side, to User Interactions Space for
	 * analysis/processing.
	 * 
	 * @param event To treat.
	 * @throws Exception When failure occurred during transmission to space.
	 */
	protected abstract void toUsersInteractionsSpace(BridgeEvent event) throws Exception;

}
