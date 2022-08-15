package org.cybnity.feature.asset_control.api;

/**
 * Channel regarding a Features Domain exposed as an entry point via the Redis
 * space
 */
public enum SecurityFeatureChannel {

	/**
	 * Boundary regarding the features of Asset Control domain
	 */
	asset_control("ac"),
	/**
	 * Security feature searching existent assets.
	 */
	ac_findAssets("acfa"),
	/**
	 * Security feature managing the creation/adding of new asset in the domain.
	 */
	ac_createAsset("acca");

	private String acronym;

	private SecurityFeatureChannel(String acronym) {
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
