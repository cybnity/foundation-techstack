package org.cybnity.feature.areas_assets_protection.api;

/**
 * Channel regarding a Capabilities Domain exposed as an entry point via the
 * Redis space
 */
public enum APIChannel {

	/**
	 * Domain regarding areas and assets protection
	 */
	areas_assets_protection,
	/**
	 * Capabilities provided by the Areas and Assets Protection boundary
	 */
	aap,
	/**
	 * UI Capability generating and downloading a report about a subject
	 */
	aap_downloadReport;
}
