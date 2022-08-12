package org.cybnity.infrastructure.common.event;

/**
 * Capability to be identified by providing of identification element.
 */
public interface Identifiable {

	/**
	 * Get a name (e.g command name of a capability or feature to execute).
	 * 
	 * @return A name.
	 */
	public String name();
}
