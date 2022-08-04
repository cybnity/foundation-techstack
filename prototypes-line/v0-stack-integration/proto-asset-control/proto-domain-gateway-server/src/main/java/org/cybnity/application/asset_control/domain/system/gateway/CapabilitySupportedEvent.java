package org.cybnity.application.asset_control.domain.system.gateway;

/**
 * Catalog of event names that are implemented by the Asset Control boundary.
 *
 */
public enum CapabilitySupportedEvent {
	/**
	 * Security feature responsible of new asset adding in domain
	 */
	createAsset,
	/**
	 * Security feature responsible of search regarding existent assets from the
	 * domain
	 */
	findAssets;
}