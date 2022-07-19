package org.cybnity.application.asset_control.ui.system.backend.capabilities;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * Start a composition of autonomous Verticles supporting the UI capabilities
 * provided by the backend services (e.g via interaction over event bus)
 * regarding a domain.
 */
public class AssetControlVerticle extends AbstractVerticle {

	public AssetControlVerticle() {
		super();
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		// TODO change for inclusion of each UIcapability dedicated verticle in the list
		// in place of current tester empty verticle
		CompositeFuture
				.all(deployVerticle(UISecurityCapability.class.getName()),
						deployVerticle(UISecurityCapability.class.getName()))// Add others
				.onComplete(handler -> {
					if (handler.succeeded()) {
						// All components started
						System.out.println("All components are started with success");
					} else {
						// At least one component failed
						System.out.println("At least one component start failed");
					}
				}).onSuccess(handler -> {
					startPromise.complete();
				}).onFailure(f -> {
					startPromise.fail(f.getCause());
				});
	}

	Future<Void> deployVerticle(String verticleName) {
		Future<Void> retVal = Future.future(handler -> {
		});
		vertx.deployVerticle(verticleName, event -> {
			if (event.succeeded()) {
				retVal.isComplete();
				System.out.println("Successly deployed: " + event.result());
			} else {
				System.out.println("Deployment failed: ");
				event.cause().printStackTrace();
				retVal.failed();
			}
		});
		return retVal;
	}
}
