package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import org.cybnity.infrastructure.uis.adapter.api.ChannelListener;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapter;
import org.cybnity.infrastructure.uis.adapter.impl.UISAbstractAdapterImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Implementation client allowing discussion with Users Interactions Space
 * (Redis broker) via Jedis integration library.
 *
 */
public class UISJedisClient extends UISAbstractAdapterImpl implements UISAdapter {

	private JedisPool jedisPool;

	public UISJedisClient() {
		super();
	}

	/**
	 * Initialize the Jedis instances allowing to provide/use connection to Redis.
	 * 
	 * @throws Exception When instantiation problem.
	 */
	public void init() throws Exception {
		super.init();
		// Create a connection pool for interactions with Redis broker
		jedisPool = new JedisPool(usersInteractionsSpaceURL());
	}

	@Override
	public void checkOperationalState() {
		// Test the connection to the space broker validating the operational state of
		// adapter connection

		// Get the pool and use the database
		try (Jedis jedis = jedisPool.getResource()) {
			if (!jedis.isConnected()) {
				System.out.println(refName + " is not operational!");
			} else {
				String pong = jedis.ping();
				System.out.println(refName + " is in operational state (" + pong + " confirmed)");
			}
		}
	}

	/**
	 * Free Jedis resources (e.g close connection pool)
	 */
	public void dispose() {
		super.dispose();
		if (jedisPool != null && !jedisPool.isClosed()) {
			jedisPool.close();
		}
	}

	@Override
	public void addListener(ChannelListener channel, String observabilityPattern, ChannelMode mode) {
		// TODO
	}

	protected void sendAsyncCommand(String command) throws Exception {
		System.out.println("send async command over the client connection...");
	}

	protected void sendSyncCommand(String command) throws Exception {
		System.out.println("send sync command over the client connection...");
		try (Jedis jedis = jedisPool.getResource()) {

			/*
			 * jedis.set("mykey", "Hello from Jedis"); String value = jedis.get("mykey");
			 * System.out.println(value);
			 * 
			 * jedis.zadd("vehicles", 0, "car"); jedis.zadd("vehicles", 0, "bike");
			 * Set<String> vehicles = jedis.zrange("vehicles", 0, -1);
			 * System.out.println(vehicles);
			 */
			/**
			 * Redis.createClient(vertx, redisOpts).connect().onSuccess(conn -> { // Test
			 * connection operational state
			 * conn.send(Request.cmd(Command.PING)).onSuccess(info -> { // PONG received
			 * System.out.println(refName + " is operational and connected to UIS broker");
			 * try { // Create the handler monitoring the CQRS event coming from the backend
			 * that are // relative to the capability domain
			 * registerUsersInteractionsSpaceHandlers();
			 * 
			 * // Confirm the operational state of this Verticle capable to collaborate with
			 * // users interactions space startPromise.complete(); } catch (Exception e) {
			 * System.out.println(refName + " UIS handlers registration failed!");
			 * e.printStackTrace(); } }).onFailure(error -> { System.out.println(refName + "
			 * connection to UIS broker failed: " + error.getCause()); // Notify problem of
			 * start startPromise.fail(error.getCause()); }); }).onFailure(fail -> {
			 * System.out.println(refName + " UIS broker connection failed: ");
			 * fail.printStackTrace(); // Notify problem of start
			 * startPromise.fail(fail.getCause()); });
			 */
		}
	}
}
