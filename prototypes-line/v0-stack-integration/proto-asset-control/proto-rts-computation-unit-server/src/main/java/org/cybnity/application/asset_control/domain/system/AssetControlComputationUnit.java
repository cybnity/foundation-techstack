package org.cybnity.application.asset_control.domain.system;

import org.cybnity.application.asset_control.services.CreateAssetFeature;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Start a composition of gateway Verticle supporting the Application security
 * services provided by the processing units of the Asset Control domain.
 */
public class AssetControlComputationUnit extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.deployVerticle(
				/* Set each feature unit regarding this domain api */
				CreateAssetFeature.class.getName(), event -> {
					if (event.succeeded()) {
						System.out.println(
								CreateAssetFeature.class.getSimpleName() + " successly deployed: " + event.result());
						startPromise.complete();
					} else {
						System.out.println(CreateAssetFeature.class.getSimpleName() + " deployment failed: ");
						event.cause().printStackTrace();
						startPromise.fail(event.cause());
					}
				});
	}
}
