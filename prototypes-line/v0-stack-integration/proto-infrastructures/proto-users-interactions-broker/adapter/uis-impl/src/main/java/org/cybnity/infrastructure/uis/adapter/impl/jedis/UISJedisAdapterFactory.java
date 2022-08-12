package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import org.cybnity.infrastructure.uis.adapter.api.UISAdapter;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapterFactory;

/**
 * Redis adapter factory using Jedis client library.
 */
public class UISJedisAdapterFactory extends UISAdapterFactory {

	private static UISJedisClient instance = null;

	public UISJedisAdapterFactory() {
		super();
	}

	/**
	 * This implementation manage and return a singleton instance of Jedis client.
	 */
	@Override
	public UISAdapter create() throws InstantiationException {
		if (instance == null) {
			synchronized (UISJedisClient.class) {
				if (instance == null) {
					// Create singleton
					instance = new UISJedisClient();
				}
			}
		}
		return instance;
	}

}
