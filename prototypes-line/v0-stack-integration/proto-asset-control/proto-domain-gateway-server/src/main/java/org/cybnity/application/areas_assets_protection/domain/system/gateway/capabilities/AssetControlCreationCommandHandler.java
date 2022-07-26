package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

public class AssetControlCreationCommandHandler extends UISecurityCapability {

	public AssetControlCreationCommandHandler() {
		super();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UIS events managed by this
		// capability

	}

	// Interpretation of the transport bridge event to identify the CQRS event to
	// process
	// Event evt = null;
}
