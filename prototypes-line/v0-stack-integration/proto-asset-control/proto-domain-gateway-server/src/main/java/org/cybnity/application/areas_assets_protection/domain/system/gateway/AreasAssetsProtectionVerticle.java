package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities.AssetControlAliveQueryHandler;
import org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities.AssetControlCreationCommandHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * Start a composition of autonomous Verticles supporting the UI capabilities
 * provided by the backend services (e.g via interaction over event bus)
 * regarding a cockpit foundation's capability domain.
 */
public class AreasAssetsProtectionVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		CompositeFuture
				.all(deployHelper(AssetControlCreationCommandHandler.class.getName()),
						deployHelper(AssetControlAliveQueryHandler.class.getName()))// Add others
				.onComplete(handler -> {
					if (handler.succeeded()) {
						// All components started
						System.out.println("All components are started with success");
						startPromise.complete();
					} else {
						// At least one component failed
						System.out.println("At least one component start failed");
					}
				})/*
					 * .onSuccess(handler -> { startPromise.complete(); }).onFailure(f -> {
					 * startPromise.fail(f.getCause()); })
					 */;
	}

	Future<Void> deployHelper(String verticleName) {
		Promise<Void> retVal = Promise.promise();
		vertx.deployVerticle(verticleName, event -> {
			if (event.succeeded()) {
				System.out.println("Successly deployed: " + event.result());
				retVal.complete();
			} else {
				System.out.println("Deployment failed: ");
				event.cause().printStackTrace();
				retVal.fail(event.cause());
			}
		});
		return retVal.future();
	}
}
