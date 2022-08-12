package org.cybnity.application.asset_control.domain.system.gateway;

import java.util.HashMap;
import java.util.Map;

import org.cybnity.feature.asset_control.api.SecurityFeatureChannel;

/**
 * Utility class allowing to map each capability name (e.g command event's
 * name), with each name of the dedicated Security Feature entry point (redis
 * stream channel) allowing to bridge the events forwarding. This implementation
 * helps to implement the Content-Based Router pattern where the recipient
 * channel is identified from the message content to forward.
 */
public class FeaturesBoundaryDestinationList {

	private Map<String, SecurityFeatureChannel> routingMap;

	/**
	 * Default constructor initializing the routing table. This configuration
	 * implementation example of events linked channels could be replaced by a
	 * routing configuration file more easy to maintain (e.g with possible hot
	 * change supported) with settings hosted by the domains-interacts-broker module
	 * (e.g as configuration api).
	 */
	public FeaturesBoundaryDestinationList() {
		// Initialize the routing destination tables that link a Users Interactions
		// Space's channel with a Security Feature name
		routingMap = new HashMap<String, SecurityFeatureChannel>();

		// Set each destination path
		routingMap.put(CapabilitySupportedEvent.createAsset.name(), SecurityFeatureChannel.ac_createAsset);
		routingMap.put(CapabilitySupportedEvent.findAssets.name(), SecurityFeatureChannel.ac_findAssets);
	}

	/**
	 * Find a route to Users Interactions Space's channel according to an event
	 * type.
	 * 
	 * @param eventName CapabilitySupportedEvent name which is subject of possible
	 *                  routing to find.
	 * @return A recipient channel or null.
	 */
	public SecurityFeatureChannel recipient(String eventName) {
		if (eventName != null) {
			// Find existing UIS channel routing
			return routingMap.get(eventName);
		}
		return null;
	}
}
