package org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

public abstract class UISecurityCapability extends AbstractVerticle {

	private RedisOptions redisOpts;
	private String refName;

	public UISecurityCapability() {
		super();
		// Define Redis options allowing capabilities to discuss with users interactions
		// space (don't use pool that avoid possible usable of channels subscription by
		// handlers)
		// TODO CONTINUER ICI
		// redisOpts = RedisOptionFactory.createUsersInteractionsSpaceOptions();
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		start();
		refName = this.getClass().getSimpleName() + " (" + this.deploymentID() + ")";

		// Create and test connector to Users Interactions Space
		System.out.println(this.getClass().getSimpleName() + " (" + this.deploymentID()
				+ ") initialize User Interactions Space connector...");

		Redis.createClient(vertx, redisOpts).connect().onSuccess(conn -> {
			// Test connection operational state
			conn.send(Request.cmd(Command.PING)).onSuccess(info -> {
				// PONG received
				System.out.println(refName + " is operational and connected to UIS broker");
				try {
					// Create the handler monitoring the CQRS event coming from the backend that are
					// relative to the capability domain
					registerUsersInteractionsSpaceHandlers();

					// Confirm the operational state of this Verticle capable to collaborate with
					// users interactions space
					startPromise.complete();
				} catch (Exception e) {
					System.out.println(refName + " UIS handlers registration failed!");
					e.printStackTrace();
				}
			}).onFailure(error -> {
				System.out.println(refName + " connection to UIS broker failed: " + error.getCause());
				// Notify problem of start
				startPromise.fail(error.getCause());
			});
		}).onFailure(fail -> {
			System.out.println(refName + " UIS broker connection failed: ");
			fail.printStackTrace();
			// Notify problem of start
			startPromise.fail(fail.getCause());
		});
	}

	/**
	 * Define and set components which consume and manage events (e.g command/query
	 * events coming from the backend server) eligible for processing.
	 * 
	 * @throws Exception When registration problem of handlers.
	 */
	protected abstract void registerUsersInteractionsSpaceHandlers() throws Exception;
}
