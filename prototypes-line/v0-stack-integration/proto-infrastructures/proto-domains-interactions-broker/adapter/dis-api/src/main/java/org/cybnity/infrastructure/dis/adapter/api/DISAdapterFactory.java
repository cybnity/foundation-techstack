package org.cybnity.infrastructure.dis.adapter.api;

/**
 * Factory of adapter instance (broker client) to Domains Interactions Space.
 */
public abstract class DISAdapterFactory {

	/**
	 * Create an instance of adapter to DIS broker regarding a type of
	 * implementation class with optional parameters (e.g broker connection setting
	 * about protocol, host, account...).
	 *
	 * @return An instance of configured adapter.
	 * @throws InstantiationException When impossible instantiation.
	 */
	public abstract DISAdapter create() throws InstantiationException;
}
