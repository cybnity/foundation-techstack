package org.cybnity.infrastructure.dis.adapter.impl.kafka;

import org.cybnity.infrastructure.dis.adapter.api.DISAdapter;
import org.cybnity.infrastructure.dis.adapter.api.DISAdapterFactory;

import io.vertx.core.Vertx;

/**
 * Kafka adapter factory using Vert.x Kafka client library.
 */
public class DISKafkaAdapterFactory extends DISAdapterFactory {

	private static DISKafkaClient instance = null;
	private Vertx context;

	public DISKafkaAdapterFactory(Vertx context) {
		super();
		this.context = context;
	}

	public DISKafkaAdapterFactory() {
		super();
	}

	public void setContext(Vertx context) {
		this.context = context;
	}

	/**
	 * This implementation manage and return a singleton instance of Lettuce client.
	 */
	@Override
	public DISAdapter create() throws InstantiationException {
		if (this.context == null)
			throw new InstantiationException("Context must had been defined before to call this method!");
		if (instance == null) {
			synchronized (DISKafkaClient.class) {
				if (instance == null) {
					// Create singleton
					instance = new DISKafkaClient(context);
				}
			}
		}
		return instance;
	}

}
