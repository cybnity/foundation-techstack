package org.cybnity.infrastructure.uis.adapter.api;

/**
 * Factory of adapter instance (broker client) to Users Interactions Space.
 */
public class UISAdapterAbstractFactory {

	/**
	 * Types of adapter supported by the infrastructure.
	 */
	public enum AdapterType {
		JEDIS_ADAPTER, LETTUCE_ADAPTER;
	}

	public UISAdapterAbstractFactory() {
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
	public UISAdapterFactory getInstance(AdapterType adapterType) throws IllegalArgumentException, Exception {
		String className = null;
		if (AdapterType.JEDIS_ADAPTER == adapterType) {
			className = "org.cybnity.infrastructure.uis.adapter.impl.jedis.UISJedisAdapterFactory";
		} else if (AdapterType.LETTUCE_ADAPTER == adapterType) {
			className = "org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceAdapterFactory";
		}
		if (className != null) {
			return (UISAdapterFactory) getClass().getClassLoader().loadClass(className).getConstructor()
					.newInstance((Object[]) null);
		}
		throw new IllegalArgumentException("Adapter type parameter is not supported!");
	}
}
