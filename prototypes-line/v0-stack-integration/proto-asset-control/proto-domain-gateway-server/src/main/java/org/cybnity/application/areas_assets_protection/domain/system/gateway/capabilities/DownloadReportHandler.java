package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

import org.cybnity.feature.areas_assets_protection.api.APIChannel;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.PubSubChannelListener;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceClient;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

/**
 * UI capability regarding the creation and download of a report about the
 * current status of an assets area.
 */
public class DownloadReportHandler extends UISecurityCapability {

	public DownloadReportHandler() {
		super();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UI capability (function)
		UISLettuceClient space = uiSpace();
		RedisClient client = space.redisClient();
		// Attach the listener of channel which monitor the command relative to this UI
		// capability
		StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		DownloadAssetReportListener listener = new DownloadAssetReportListener(APIChannel.aap.name());
		connection.addListener(listener); // listener installed
		connection.async().subscribe(listener.monitoredChannel()); // channel subscribed
	}

	/**
	 * Handler of channel managed via a pub/sub mode that process the feature.
	 */
	private class DownloadAssetReportListener extends PubSubChannelListener {

		public DownloadAssetReportListener(String channelLabel) {
			super(channelLabel);
		}

		/**
		 * Generate a report regarding an asset and deliver to requester via download.
		 */
		@Override
		public void onMessage(String channel, Event event) {
			System.out.println("Report download event (correlationId: " + event.correlationId()
					+ ") requested from AAP boundary via the " + channel + " channel");
		}

	}
}
