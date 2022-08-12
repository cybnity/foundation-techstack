package org.cybnity.infrastructure.dis.adapter.api;

import org.cybnity.infrastructure.common.event.Event;

/**
 * Observer of events monitoring from a channel
 */
public interface ChannelListener {

	/**
	 * Notify an event observed in a channel.
	 *
	 * @param channel Where the event was observed.
	 * @param event   The observed information.
	 */
	public void onMessage(String channel, Event event);

	public void message(String channel, String message);

	public void message(String pattern, String channel, String message);

	/**
	 * Label about the monitored channel.
	 *
	 * @return A channel name.
	 */
	public String monitoredChannel();

}
