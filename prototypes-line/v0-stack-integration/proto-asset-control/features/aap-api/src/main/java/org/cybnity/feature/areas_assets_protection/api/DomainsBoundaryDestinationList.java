package org.cybnity.feature.areas_assets_protection.api;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Identify the domain applications channels that are supported by the AAP
 * capabilities (e.g for forwarding and/or coupling over Users Interactions
 * Space channels.
 */
public class DomainsBoundaryDestinationList {

	private Collection<DelegatedDomainAPIChannel> list;

	public DomainsBoundaryDestinationList() {
		// Initialize the routing destination tables that link a Users Interactions
		// Space's channel with
		// an UI capability name
		list = new ArrayList<DelegatedDomainAPIChannel>();

		// Set each supported other domain
		list.add(DelegatedDomainAPIChannel.asset_control);
	}

	/**
	 * Get the list of domains channels that are entry points of domain application
	 * as API from the Users Interactions Space.
	 * 
	 * @return
	 */
	public Collection<DelegatedDomainAPIChannel> entryPointChannels() {
		return this.list;
	}

	/**
	 * Catalog of channels relative to integrated channels of other domains (e.g
	 * applications).
	 */
	public enum DelegatedDomainAPIChannel {
		/**
		 * Features provided by Asset Control application domain
		 */
		asset_control;
	}
}
