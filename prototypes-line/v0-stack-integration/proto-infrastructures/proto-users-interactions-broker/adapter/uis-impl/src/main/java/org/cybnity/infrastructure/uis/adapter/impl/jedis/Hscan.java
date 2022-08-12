package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 * Read all the field keys and field values of a particular hash key
 *
 */
public class Hscan implements ScanStrategy<Map.Entry<String, String>> {

	private String key;

	@Override
	public ScanResult<Entry<String, String>> scan(Jedis jedis, String cursor, ScanParams scanParams) {
		return jedis.hscan(key, cursor, scanParams);
	}
}