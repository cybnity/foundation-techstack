package org.cybnity.application.asset_control;

import org.cybnity.infrastructure.dis.adapter.api.DISAdapterAbstractFactory;
import org.cybnity.infrastructure.dis.adapter.api.DISAdapterFactory;
import org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaAdapterFactory;
import org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaClient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Category of Verticle dedicated to an application layer's feature implementing
 * a processing unit (e.g a security feature realization).
 */
public abstract class DomainProcessingFeature extends AbstractVerticle {

	private DISKafkaClient diSpace;
	private org.cybnity.infrastructure.dis.adapter.api.DISAdapterAbstractFactory.AdapterType disAdapterType = DISAdapterAbstractFactory.AdapterType.KAFKA_ADAPTER;

	/**
	 * Default constructor.
	 */
	public DomainProcessingFeature() {
		super();
	}

	@Override
	public void stop() throws Exception {
		// Free adapter resources
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
	 * Initialize the space adapter, check their operational states and execute the
	 * handlers registration.
	 */
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		start();
		// Create connector to Domains Interactions Space
		DISAdapterFactory factory = new DISAdapterAbstractFactory().getInstance(disAdapterType);
		if (factory instanceof DISKafkaAdapterFactory) {
			// Define the context required before client instantiation
			((DISKafkaAdapterFactory) factory).setContext(vertx);
		}
		diSpace = (DISKafkaClient) factory.create();

		// Activate the adapters
		diSpace.init();
		// Verify operational state
		diSpace.checkOperationalState();
		// Start DIS handlers
		registerDomainsInteractionsSpaceHandlers();
		// Confirm start end
		startPromise.complete();
	}

	/**
	 * Define and set components which consume and execute features realization
	 * according to the events (e.g command/query/notification events) eligible for
	 * processing.
	 * 
	 * @throws Exception When registration problem of handlers.
	 */
	protected abstract void registerDomainsInteractionsSpaceHandlers() throws Exception;
}
