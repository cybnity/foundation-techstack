package org.cybnity.infrastructure.dis.adapter.api;

/**
 * Adapter managing integration (as client role connected to broker) to Domains
 * Interactions Space according to the supported connectivity protocol (as
 * Anti-Corruption Layer).
 */
public interface DISAdapter {

	/**
	 * Initialize all the resources required by the adapter allowing it to become
	 * operational for interactions with the Domains Interactions Space.
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

	/**
	 * Add an observer of channel.
	 *
	 * @param listener             Mandatory observer instance called by the adapter
	 *                             when the observability pattern is satisfied. If
	 *                             null, none observer is applied.
	 * @param observabilityPattern Optional filtering pattern about the event to
	 *                             monitor justifying the call of the listener.
	 * @param mode                 Type of channel mode observed (e.g PUB_SUB_MODE
	 *                             when queue is observed) with the listener.
	 */
	public void addListener(ChannelListener listener, String observabilityPattern, ChannelMode mode);

	/**
	 * Type of channel integrated and managed by the adapter.
	 */
	public enum ChannelMode {
		PUB_SUB_MODE, STREAM;
	}

	/**
	 * Create a channel.
	 * 
	 * @param channelName A mandatory name.
	 * @throws IllegalArgumentException When null or invalid channel name parameter.
	 */
	public void createChannel(String channelName) throws IllegalArgumentException;

}
