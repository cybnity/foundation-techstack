package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Test starting of areas and assets protection domain of UI cockpit
 * capabilities.
 */
@ExtendWith(VertxExtension.class)
public class TestAssetControlUICapabilities {

	private Vertx vertx;
	private VertxTestContext context;

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		this.vertx = vertx;
		this.context = testContext;
		vertx.deployVerticle(new AreasAssetsProtectionVerticle(),
				testContext.succeeding(id -> testContext.completeNow()));
	}

	@Test
	void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
		System.out.println("Verticles deployment test finalized with success");
		testContext.completeNow();
	}

	@AfterEach
	void tearDown() throws Exception {
		vertx.close();
		this.vertx = null;
		this.context = null;
	}

}
