package org.cybnity.application.asset_control.ui.system.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Test starting of HTTP server Verticle.
 *
 */
@ExtendWith(VertxExtension.class)
public class TestUICapabilityHTTPServer {

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		vertx.deployVerticle(new UICapabilityHTTPServer(), testContext.succeeding(id -> testContext.completeNow()));
	}

	@Test
	void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
		testContext.completeNow();
	}
}
