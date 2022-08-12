package org.cybnity.application;

import org.cybnity.application.areas_assets_protection.domain.system.gateway.AreasAssetsProtectionUISGateway;
import org.cybnity.application.asset_control.domain.system.gateway.AssetControlDomainIOGateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * Start a composition of gateway Verticles implemented for the POC as unique
 * system simulator of multiple gateway server (e.g UI capabilities domain
 * gateway, Security features domain gateway).
 */
public class MultipleDomainGateway extends AbstractVerticle {

	/**
	 * Default start method regarding the server.
	 * 
	 * @param args None pre-required.
	 */
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MultipleDomainGateway());
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		CompositeFuture
				.all(deployHelper(AreasAssetsProtectionUISGateway.class.getName()),
						deployHelper(AssetControlDomainIOGateway.class.getName()))// Add others

				.onComplete(handler -> {
					if (handler.succeeded()) {
						// All components started
						System.out.println("All gateways are started with success");
						startPromise.complete();
					} else {
						// At least one component failed
						System.out.println("At least one gateway start failed");
					}
				});
	}

	Future<Void> deployHelper(String verticleName) {
		Promise<Void> retVal = Promise.promise();
		vertx.deployVerticle(verticleName, event -> {
			if (event.succeeded()) {
				retVal.complete();
			} else {
				event.cause().printStackTrace();
				retVal.fail(event.cause());
			}
		});
		return retVal.future();
	}
}
