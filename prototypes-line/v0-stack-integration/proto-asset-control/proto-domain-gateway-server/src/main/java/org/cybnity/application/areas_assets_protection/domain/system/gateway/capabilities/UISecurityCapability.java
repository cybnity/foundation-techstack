package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

import java.util.MissingResourceException;

import org.cybnity.infrastructure.uis.adapter.api.UISAdapterAbstractFactory;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapterAbstractFactory.AdapterType;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceClient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public abstract class UISecurityCapability extends AbstractVerticle {

	private UISLettuceClient uiSpace;
	private AdapterType uisAdapterType = UISAdapterAbstractFactory.AdapterType.LETTUCE_ADAPTER;

	/**
	 * Default constructor reading configuration file.
	 * 
	 * @throws MissingResourceException When uis-adapter-config.properties file is
	 *                                  not found in classpath.
	 */
	public UISecurityCapability() throws MissingResourceException {
		super();
	}

	@Override
	public void stop() throws Exception {
		// Free adapter resources
		if (uiSpace != null) {
			uiSpace.dispose();
		}
	}

	/**
	 * Get Users Interactions Space client.
	 * 
	 * @return A client to collaboration space.
	 */
	protected UISLettuceClient uiSpace() {
		return this.uiSpace;
	}

	/**
	 * Initialize the Users Interactions Space adapter, initialize it, check
	 * operational state and execute the capability handlers registration.
	 */
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		start();
		// Create and test connector to Users Interactions Space
		uiSpace = (UISLettuceClient) new UISAdapterAbstractFactory().getInstance(uisAdapterType).create();
		// Activate the adapter
		uiSpace.init();
		// Verify operation state
		uiSpace.checkOperationalState();
		// Start UIS handlers
		registerUsersInteractionsSpaceHandlers();
		// Confirm start end
		startPromise.complete();
	}

	/**
	 * Define and set components which consume and manage events (e.g command/query
	 * events coming from the backend server) eligible for processing.
	 * 
	 * @throws Exception When registration problem of handlers.
	 */
	protected abstract void registerUsersInteractionsSpaceHandlers() throws Exception;
}
