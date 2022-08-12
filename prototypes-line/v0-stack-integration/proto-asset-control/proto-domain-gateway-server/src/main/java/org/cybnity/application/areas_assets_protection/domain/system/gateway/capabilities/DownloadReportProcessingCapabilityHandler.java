package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

import java.util.LinkedList;
import java.util.List;

import org.cybnity.application.areas_assets_protection.UISecurityCapability;
import org.cybnity.application.areas_assets_protection.domain.system.gateway.CapabilitiesBoundaryDestinationList;
import org.cybnity.application.areas_assets_protection.domain.system.gateway.CapabilitySupportedEvent;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.PubSubChannelListener;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

/**
 * UI capability regarding the creation and download of a report about the
 * current status of an assets area.
 */
public class DownloadReportProcessingCapabilityHandler extends UISecurityCapability {
	private CapabilitiesBoundaryDestinationList routes;
	private CapabilitySupportedEvent capabilityEntryPoint = CapabilitySupportedEvent.downloadReport;
	private StatefulRedisPubSubConnection<String, String> connection;
	private List<DownloadAssetReportListener> activeListeners = new LinkedList<DownloadAssetReportListener>();

	public DownloadReportProcessingCapabilityHandler() {
		super();
		routes = new CapabilitiesBoundaryDestinationList();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UI capability (function)
		// Attach the listener of channel to delegate treatment of each event relative
		// to any capability
		connection = uiSpace().redisClient().connectPubSub();
		// Create set of listener manager by this handler
		activeListeners.add(new DownloadAssetReportListener(routes.recipient(capabilityEntryPoint.name()).name()));

		// Install listeners on connection
		for (DownloadAssetReportListener listener : activeListeners) {
			connection.addListener(listener); // listener installed
			connection.async().subscribe(listener.monitoredChannel()); // channel subscribed
		}
	}

	@Override
	public void stop() throws Exception {
		// Free resources
		for (DownloadAssetReportListener listener : activeListeners) {
			// Stop active subscription
			connection.async().unsubscribe(listener.monitoredChannel());
			// remove listener from connection
			connection.removeListener(listener);
		}
		super.stop();
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
			System.out.println("Execute the download of report (UI capability) requested by event (type: "
					+ event.type() + ", correlationId: " + event.correlationId() + ") via AAP boundary (entry channel: "
					+ channel + ")");
		}

	}
}
