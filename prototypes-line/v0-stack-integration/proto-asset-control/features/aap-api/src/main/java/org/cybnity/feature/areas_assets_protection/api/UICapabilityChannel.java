package org.cybnity.feature.areas_assets_protection.api;

/**
 * Channel regarding a Capabilities Domain exposed as an entry point via the
 * Redis space
 */
public enum UICapabilityChannel {

	/**
	 * Boundary regarding the capabilities of Areas and Assets Protection
	 */
	areas_assets_protection("aap"),
	/**
	 * UI Capability generating and downloading a report about a subject
	 */
	aap_downloadReport("aapdp");

	private String acronym;

	private UICapabilityChannel(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Get the label of acronym regarding this channel.
	 * 
	 * @return An acronym or null.
	 */
	public String acronym() {
		return this.acronym;
	}
}
