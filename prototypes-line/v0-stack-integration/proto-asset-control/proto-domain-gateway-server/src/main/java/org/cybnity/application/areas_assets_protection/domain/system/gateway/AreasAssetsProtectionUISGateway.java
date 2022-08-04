package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities.DownloadReportProcessingCapabilityHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * Start a composition of autonomous Verticles supporting the UI capabilities
 * provided by the backend services (e.g via interaction over event bus)
 * regarding a cockpit foundation's capability domain.
 */
public class AreasAssetsProtectionUISGateway extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		CompositeFuture.all(
				/* Set the routing manager regarding this capabilities domain for UI */
				deployHelper(AreasAssetsProtectionSecurityCapabilitiesDispatcher.class.getName()),

				/*
				 * Set each handler of UI capability supported by this UI capabilities boundary
				 */
				deployHelper(DownloadReportProcessingCapabilityHandler.class.getName()))// Add others

				.onComplete(handler -> {
					if (handler.succeeded()) {
						// All components started
						System.out.println(AreasAssetsProtectionUISGateway.class.getSimpleName()
								+ " components are started with success");
						startPromise.complete();
					} else {
						// At least one component failed
						System.out.println(AreasAssetsProtectionUISGateway.class.getSimpleName()
								+ " > at least one component start failed");
					}
				});
	}

	Future<Void> deployHelper(String verticleName) {
		Promise<Void> retVal = Promise.promise();
		vertx.deployVerticle(verticleName, event -> {
			if (event.succeeded()) {
				System.out.println("AAP successly deployed: " + event.result());
				retVal.complete();
			} else {
				System.out.println("AAP deployment failed: ");
				event.cause().printStackTrace();
				retVal.fail(event.cause());
			}
		});
		return retVal.future();
	}
}
