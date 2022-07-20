package org.cybnity.application.asset_control.ui.system.backend.infrastructure.impl.redis;

import io.vertx.redis.client.RedisOptions;

/**
 * Factory of Redis adapter based on Vert.x Redis plugin.
 */
public class RedisOptionFactory {

	static private String redisBackendUserAccount = "assetcontrolbackend",
			/*
			 * Defined by users.acl file setting in Redis container (see
			 * infrastructure/users-interactions-broker sub-project)
			 */redisBackendUserPassword = "kk6g52LmAR3n";
	static private String defaultUserPassword = "PRg53nV82DQy";
	static private String serverHost = "localhost", serverPort = "6379", dbNumber = "1";

	public RedisOptionFactory() {
	}

	/**
	 * Create a configured set of options allowing to discuss with Users
	 * Interactions Space.
	 * 
	 * @return An instance.
	 */
	static public RedisOptions createUsersInteractionsSpaceOptions() {
		return new RedisOptions().setConnectionString(new StringBuffer("redis://").append(redisBackendUserAccount)
				.append(":").append(redisBackendUserPassword).append("@").append(serverHost).append(":")
				.append(serverPort).append("/").append(dbNumber).toString()).setPassword(defaultUserPassword);
	}
}
