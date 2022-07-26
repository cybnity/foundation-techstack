package org.cybnity.infrastructure.uis.adapter.impl.lettuce;

import org.cybnity.infrastructure.uis.adapter.api.UISAdapter;
import org.cybnity.infrastructure.uis.adapter.impl.UISAbstractAdapterImpl;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;

/**
 * Implementation client allowing discussion with Users Interactions Space
 * (Redis broker) via Lettuce integration library.
 */
public class UISLettuceClient extends UISAbstractAdapterImpl implements UISAdapter {

	private RedisClient redisClient;
	private StatefulRedisConnection<String, String> connection;

	public UISLettuceClient() {
		super();
	}

	/**
	 * Initialize the Lettuce instances allowing to provide/use connection to Redis.
	 * 
	 * @throws Exception When instantiation problem.
	 */
	public void init() throws Exception {
		super.init();
		// Get the connection and test usage of the database
		// Define a thread-safe connection to a Redis server that will maintain its
		// connection to the server and reconnect if needed. Once we have a connection,
		// we can use
		// it to execute Redis commands either synchronously or asynchronously
		redisClient = RedisClient.create(usersInteractionsSpaceURL());
		connection = redisClient.connect();
	}

	protected void addPubSubChannelListener(RedisPubSubListener<String, String> observer) {
		if (observer != null)
			redisClient().connectPubSub().addListener(observer);
	}

	/**
	 * Get original Redis client connected to space.
	 * 
	 * @return A client.
	 */
	public RedisClient redisClient() {
		return this.redisClient;
	}

	/**
	 * Get a new sync commands instance authorized.
	 * 
	 * @return An instance.
	 */
	public RedisCommands<String, String> newSyncCommands() {
		RedisCommands<String, String> syncCommands = connection.sync();
		String password = authPassword();
		if (password != null && !"".equals(password))
			syncCommands.auth(authPassword());
		return syncCommands;
	}

	/**
	 * Get a new asynchronous commands instance authorized.
	 * 
	 * @return An instance.
	 */
	public RedisAsyncCommands<String, String> newAsyncCommands() {
		RedisAsyncCommands<String, String> asyncCommands = connection.async();
		String password = authPassword();
		if (password != null && !"".equals(password))
			asyncCommands.auth(authPassword());
		return asyncCommands;
	}

	/**
	 * Get a new reactive commands instance.
	 * 
	 * @return An instance.
	 */
	public RedisStringReactiveCommands<String, String> newReactiveCommands() {
		RedisStringReactiveCommands<String, String> reactiveCommands = connection.reactive();
		return reactiveCommands;
	}

	@Override
	public void checkOperationalState() {
		// Test the connection to the space broker validating the operational state of
		// adapter connection
		RedisCommands<String, String> syncCommands = newSyncCommands();
		String pong = syncCommands.ping();
		if (pong != null && !pong.equals("")) {
			System.out.println(refName + " is in operational state (" + pong + " confirmed)");
		} else {
			System.out.println(refName + " is not operational!");
		}
	}

	/**
	 * Free Lettuce resources.
	 */
	public void dispose() {
		super.dispose();
		if (connection != null && connection.isOpen()) {
			connection.close();
		}
		if (redisClient != null) {
			redisClient.shutdown();
		}
	}

	protected void sendAsyncCommand(String command) throws Exception {
		System.out.println("send async command over the client connection...");
	}

	protected void sendSyncCommand(String command) throws Exception {
		System.out.println("send sync command over the client connection...");
		// see https://www.baeldung.com/java-redis-lettuce
	}
}
