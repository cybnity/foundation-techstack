package org.cybnity.infrastructure.uis.adapter.impl.lettuce;

import org.cybnity.infrastructure.common.event.Event;

/**
 * Handler of channel managed via a pub/sub mode that record traces regarding
 * events operated/treated.
 */
public class PubSubChannelTraceListener extends PubSubChannelListener {

	public PubSubChannelTraceListener(String channelLabel) {
		super(channelLabel);
	}

	@Override
	public void onMessage(String channel, Event event) {
		System.out
				.println("Event (correlationId: " + event.correlationId() + ") entry into the " + channel + " channel");
	}

}
