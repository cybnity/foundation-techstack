package org.cybnity.application.asset_control.ui.system.backend.routing;

/**
 * Channel allowing collaboration between the client side area (e.g reactive
 * event bus) and the server-side services (e.g backend capabilities).
 */
public enum CollaborationChannel {

	/**
	 * Generic channel regarding the areas and assets protection domain usable about
	 * notification events
	 */
	aap("aap"),

	/**
	 * Entry point of capabilities domain regarding areas and assets protection
	 * client to server
	 */
	aap_in("aap.in"),
	/**
	 * Output point of capabilities domain regarding areas and assets protection
	 * server to client
	 */
	aap_out("aap.out");

	private String label;

	private CollaborationChannel(String label) {
		this.label = label;
	}

	public String label() {
		return this.label;
	}
}