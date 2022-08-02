package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import java.util.HashMap;
import java.util.Map;

import org.cybnity.feature.areas_assets_protection.api.APIChannel;

/**
 * Utility class allowing to map each capability name (e.g command event's
 * name), with each name of the dedicated UI capability entry point (redis
 * stream channel) allowing to bridge the events forwarding. This implementation
 * helps to implement the Content-Based Router pattern where the recipient
 * channel is identified from the message content to forward.
 */
public class CapabilitiesBoundaryDestinationList {

	private Map<String, Enum<?>> routingMap;

	/**
	 * Default constructor initializing the routing table. This configuration
	 * implementation example of linked channels could be replaced by a routing
	 * configuration file more easy to maintain (e.g with possible hot change
	 * supported) with settings hosted by the domains-interacts-broker module (e.g
	 * as configuration api).
	 */
	public CapabilitiesBoundaryDestinationList() {
		// Initialize the routing destination tables that link a Users Interactions
		// Space's channel with
		// an UI capability name
		routingMap = new HashMap<String, Enum<?>>();

		// Set each destination path
		routingMap.put(CapabilityEventNameRepository.downloadReport.name(), APIChannel.aap_downloadReport);

	}

	/**
	 * Find a route to Users Interactions Space's channel.
	 * 
	 * @param aCollaborationChannelLabel Origin channel which is subject of possible
	 *                                   routing to find.
	 * @return A recipient channel or null.
	 */
	public Enum<?> recipient(String aCollaborationChannelLabel) {
		if (aCollaborationChannelLabel != null && !"".equals(aCollaborationChannelLabel)) {
			// Find existing UIS channel routing
			return routingMap.get(aCollaborationChannelLabel);
		}
		return null;
	}
}
