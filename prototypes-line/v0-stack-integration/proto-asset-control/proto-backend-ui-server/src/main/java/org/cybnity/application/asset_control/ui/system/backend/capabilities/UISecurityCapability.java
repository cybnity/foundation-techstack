package org.cybnity.application.asset_control.ui.system.backend.capabilities;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

public class UISecurityCapability extends AbstractVerticle {

	private String redisBackendUserAccount = "assetcontrolbackend",
			/*
			 * Defined by users.acl file setting in Redis container (see
			 * infrastructure/users-interactions-broker sub-project)
			 */redisBackendUserPassword = "kk6g52LmAR3n";
	private String defaultUserPassword = "PRg53nV82DQy";
	private String serverHost = "localhost", serverPort = "6379", dbNumber = "1";
	private RedisOptions redisOpts;
	private String refName;

	public UISecurityCapability() {
		super();
		// Define Redis options allowing capabilities to discuss with users interactions
		// space (don't use pool that avoid possible usable of channels subscription by
		// handlers)
		redisOpts = new RedisOptions().setConnectionString(new StringBuffer("redis://").append(redisBackendUserAccount)
				.append(":").append(redisBackendUserPassword).append("@").append(serverHost).append(":")
				.append(serverPort).append("/").append(dbNumber).toString()).setPassword(defaultUserPassword);
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

				// Confirm the operational state of this Verticle capable to collaborate with
				// users interactions space
				startPromise.complete();
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
}
