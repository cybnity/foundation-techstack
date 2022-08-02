package org.cybnity.infrastructure.uis.adapter.api;

/**
 * Factory of adapter instance (broker client) to Users Interactions Space.
 */
public abstract class UISAdapterFactory {

	/**
	 * Create an instance of adapter to UIS broker regarding a type of
	 * implementation class with optional parameters (e.g broker connection setting
	 * about protocol, host, account...).
	 * 
	 * @return An instance of configured adapter.
	 * @throws InstantiationException When impossible instantiation.
	 */
	public abstract UISAdapter create() throws InstantiationException;
}
