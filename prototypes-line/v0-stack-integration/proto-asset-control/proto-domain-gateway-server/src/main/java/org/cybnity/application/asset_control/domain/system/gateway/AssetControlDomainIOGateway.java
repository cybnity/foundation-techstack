package org.cybnity.application.asset_control.domain.system.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Start a composition of gateway Verticle supporting the Application security
 * services provided by the processing units of the Asset Control domain.
 */
public class AssetControlDomainIOGateway extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.deployVerticle(
				/* Set the routing manager regarding this domain api */
				AssetControlSecurityFeaturesDispatcher.class.getName(), event -> {
					if (event.succeeded()) {
						System.out.println(AssetControlSecurityFeaturesDispatcher.class.getSimpleName()
								+ " successly deployed: " + event.result());
						startPromise.complete();
					} else {
						System.out.println(
								AssetControlSecurityFeaturesDispatcher.class.getSimpleName() + " deployment failed: ");
						event.cause().printStackTrace();
						startPromise.fail(event.cause());
					}
				});
	}
}
