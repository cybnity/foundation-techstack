package org.cybnity.infrastructure.uis.adapter.impl.lettuce;

import org.cybnity.infrastructure.uis.adapter.api.UISAdapter;
import org.cybnity.infrastructure.uis.adapter.api.UISAdapterFactory;
import org.cybnity.infrastructure.uis.adapter.impl.jedis.UISJedisClient;

/**
 * Redis adapter factory using Lettuce client library.
 */
public class UISLettuceAdapterFactory extends UISAdapterFactory {

	private static UISLettuceClient instance = null;

	public UISLettuceAdapterFactory() {
		super();
	}

	/**
	 * This implementation manage and return a singleton instance of Lettuce client.
	 */
	@Override
	public UISAdapter create() throws InstantiationException {
		if (instance == null) {
			synchronized (UISJedisClient.class) {
				if (instance == null) {
					// Create singleton
					instance = new UISLettuceClient();
				}
			}
		}
		return instance;
	}

}
