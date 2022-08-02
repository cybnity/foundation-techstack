package org.cybnity.infrastructure.uis.adapter.impl.lettuce;

import org.cybnity.infrastructure.common.event.CommandEvent;
import org.cybnity.infrastructure.common.event.CommonEvent;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.common.event.NotificationEvent;
import org.cybnity.infrastructure.common.event.QueryEvent;
import org.cybnity.infrastructure.uis.adapter.api.ChannelListener;

import io.lettuce.core.pubsub.RedisPubSubListener;
import io.vertx.core.json.JsonObject;

/**
 * Handler of channel managed via a pub/sub mode.
 */
public abstract class PubSubChannelListener implements ChannelListener, RedisPubSubListener<String, String> {

	private String monitoredChannel;

	public PubSubChannelListener(String channelLabel) {
		this.monitoredChannel = channelLabel;
	}

	@Override
	public String monitoredChannel() {
		return this.monitoredChannel;
	}

	@Override
	public void message(String channel, String message) {
		System.out.println("Got " + message + " on channel " + channel);
		// Serialize the message as non type object that is equals to a Map
		JsonObject json = new JsonObject(message);
		// Identify which type of class type is defined for the event type
		String eventType = json.getString("type");

		switch (eventType) {
		case "CommandEvent":
			try {
				// delegate the event treatment to onMessage(String channel, Event event)
				onMessage(channel, json.mapTo(CommandEvent.class));
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
			break;
		case "QueryEvent":
			try {
				// delegate the event treatment to onMessage(String channel, Event event)
				onMessage(channel, json.mapTo(QueryEvent.class));
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
			break;
		case "NotificationEvent":
			try {
				// delegate the event treatment to onMessage(String channel, Event event)
				onMessage(channel, json.mapTo(NotificationEvent.class));
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
			break;
		default:
			try {
				// delegate the event treatment to onMessage(String channel, Event event)
				onMessage(channel, json.mapTo(CommonEvent.class));
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void message(String pattern, String channel, String message) {
		System.out.println("Got " + message + " on channel" + channel + " based on pattern " + pattern);
		// Delegate to message method
		message(channel, message);
	}

	@Override
	public abstract void onMessage(String channel, Event event);

	@Override
	public void subscribed(String channel, long count) {

	}

	@Override
	public void psubscribed(String pattern, long count) {

	}

	@Override
	public void unsubscribed(String channel, long count) {
	}

	@Override
	public void punsubscribed(String pattern, long count) {

	}
}
