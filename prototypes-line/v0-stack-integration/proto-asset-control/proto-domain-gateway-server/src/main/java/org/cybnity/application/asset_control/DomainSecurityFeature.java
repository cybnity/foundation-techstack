package org.cybnity.application.asset_control;

import org.cybnity.infrastructure.dis.adapter.api.DISAdapterAbstractFactory;
import org.cybnity.infrastructure.dis.adapter.api.DISAdapterFactory;
import org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaAdapterFactory;
import org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaClient;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapterAbstractFactory;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapterAbstractFactory.AdapterType;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceClient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Category of Verticle dedicated to the application layer and features domain.
 */
public abstract class DomainSecurityFeature extends AbstractVerticle {

	private UISLettuceClient uiSpace;
	private AdapterType uisAdapterType = UISAdapterAbstractFactory.AdapterType.LETTUCE_ADAPTER;
	private DISKafkaClient diSpace;
	private org.cybnity.infrastructure.dis.adapter.api.DISAdapterAbstractFactory.AdapterType disAdapterType = DISAdapterAbstractFactory.AdapterType.KAFKA_ADAPTER;

	/**
	 * Default constructor.
	 */
	public DomainSecurityFeature() {
		super();
	}

	@Override
	public void stop() throws Exception {
		// Free adapter resources
		if (uiSpace != null) {
			uiSpace.dispose();
		}
		if (diSpace != null) {
			diSpace.dispose();
		}
	}

	/**
	 * Get Domains Interactions Space client.
	 * 
	 * @return A client to domain space.
	 */
	protected DISKafkaClient diSpace() {
		return this.diSpace;
	}

	/**
	 * Get Users Interactions Space client.
	 * 
	 * @return A client to UI collaboration space.
	 */
	protected UISLettuceClient uiSpace() {
		return this.uiSpace;
	}

	/**
	 * Initialize the spaces adapters, check their operational states and execute
	 * the handlers registration.
	 */
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		start();
		// Create connector to Users Interactions Space
		uiSpace = (UISLettuceClient) new UISAdapterAbstractFactory().getInstance(uisAdapterType).create();
		// Create connector to Domains Interactions Space
		DISAdapterFactory factory = new DISAdapterAbstractFactory().getInstance(disAdapterType);
		if (factory instanceof DISKafkaAdapterFactory) {
			// Define the context required before client instantiation
			((DISKafkaAdapterFactory) factory).setContext(vertx);
		}
		diSpace = (DISKafkaClient) factory.create();

		// Activate the adapters
		uiSpace.init();
		diSpace.init();
		// Verify operational states
		uiSpace.checkOperationalState();
		diSpace.checkOperationalState();
		// Start UIS and DIS handlers
		registerUsersInteractionsSpaceHandlers();
		registerDomainsInteractionsSpaceHandlers();
		// Confirm start end
		startPromise.complete();
	}

	/**
	 * Define and set components which consume and manage events (e.g
	 * command/query/notification events) eligible for processing.
	 * 
	 * @throws Exception When registration problem of handlers.
	 */
	protected abstract void registerUsersInteractionsSpaceHandlers() throws Exception;

	/**
	 * Define and set components which consume and manage events (e.g
	 * command/query/notification events) eligible for processing.
	 * 
	 * @throws Exception When registration problem of handlers.
	 */
	protected abstract void registerDomainsInteractionsSpaceHandlers() throws Exception;
}
