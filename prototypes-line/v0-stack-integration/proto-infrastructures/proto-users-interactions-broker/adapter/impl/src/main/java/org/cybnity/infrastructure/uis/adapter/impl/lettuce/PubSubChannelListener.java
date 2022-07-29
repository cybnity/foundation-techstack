package org.cybnity.infrastructure.uis.adapter.impl.lettuce;

import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.uis.adapter.api.ChannelListener;

import io.lettuce.core.pubsub.RedisPubSubListener;

/**
 * Handler of channel managed via a pub/sub mode.
 */
public class PubSubChannelListener implements ChannelListener, RedisPubSubListener<String, String> {

	private String monitoredChannel;

	public PubSubChannelListener(String channelLabel) {
		this.monitoredChannel = channelLabel;
	}

	@Override
	public void onMessage(String channel, Event event) {

	}

	@Override
	public void message(String channel, String message) {
		System.out.println("Got " + message + " on channel" + channel);
		// TODO call handler and/or execute delegation with
		// transformation/validation/... to security feature according to the role of
		// the components of this layer (see ppt)
	}

	@Override
	public void message(String pattern, String channel, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribed(String channel, long count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void psubscribed(String pattern, long count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribed(String channel, long count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void punsubscribed(String pattern, long count) {
		// TODO Auto-generated method stub

	}

	@Override
	public String monitoredChannel() {
		return this.monitoredChannel;
	}
}
