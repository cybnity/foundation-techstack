package org.cybnity.infrastructure.dis.adapter.api;

/**
 * Factory of adapter instance (broker client) to Domains Interactions Space.
 */
public class DISAdapterAbstractFactory {

	/**
	 * Types of adapter supported by the infrastructure.
	 */
	public enum AdapterType {
		KAFKA_ADAPTER;
	}

	public DISAdapterAbstractFactory() {
	}

	/**
	 * Get a factory according to a type of adapter supported by the infrastructure.
	 *
	 * @param adapterType Mandatory type of adapter to instantiate.
	 * @return A factory.
	 * @throws IllegalArgumentException When parameter is null.
	 * @throws Exception                When unknown adapter type requested in
	 *                                  parameter, or problem during instantiation
	 *                                  of instance (e.g class not found).
	 */
	public DISAdapterFactory getInstance(AdapterType adapterType) throws IllegalArgumentException, Exception {
		String className = null;
		if (AdapterType.KAFKA_ADAPTER == adapterType) {
			className = "org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaAdapterFactory";
		}
		if (className != null) {
			return (DISAdapterFactory) getClass().getClassLoader().loadClass(className).getConstructor()
					.newInstance((Object[]) null);
		}
		throw new IllegalArgumentException("Adapter type parameter is not supported!");
	}
}
