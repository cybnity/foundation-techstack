package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

import org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceClient;

import io.lettuce.core.RedisClient;

public class AssetControlCreationCommandHandler extends UISecurityCapability {

	public AssetControlCreationCommandHandler() {
		super();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UIS events managed by this
		// capability
		UISLettuceClient space = uiSpace(); 
		RedisClient client = space.redisClient();
		// TODO CONTINUER ICI EN CRÉANT L'INTERFACE PUBSUB LISNETER GÉNÉRIQUE ET LA POSITIONER SUR UISAPDATER POUR L'APPELER
	}

	// Interpretation of the transport bridge event to identify the CQRS event to
	// process
	// Event evt = null;
	// see https://www.baeldung.com/java-redis-lettuce for synccommand send
}
