package org.cybnity.infrastructure.uis.adapter.api;

/**
 * Adapter managing integration (as client role connected to broker) to Users
 * Interactions Space according to the supported connectivity protocol (as
 * Anti-Corruption Layer).
 */
public interface UISAdapter {

	/**
	 * Initialize all the resources required by the adapter allowing it to become
	 * operational for interactions with the Users Interactions Space.
	 * 
	 * @throws Exception When instantiation problem.
	 */
	public void init() throws Exception;

	/**
	 * Free resources (e.g connections, pool) from the adapter.
	 */
	public void dispose();

	/**
	 * Verify operational state of the adapter.
	 */
	public void checkOperationalState();

}
