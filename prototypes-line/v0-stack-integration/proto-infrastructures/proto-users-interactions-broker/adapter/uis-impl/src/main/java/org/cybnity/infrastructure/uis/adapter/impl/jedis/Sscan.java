package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 * read all the members of a set
 *
 */
public class Sscan implements ScanStrategy<String> {

	private String key;

	public ScanResult<String> scan(Jedis jedis, String cursor, ScanParams scanParams) {
		return jedis.sscan(key, cursor, scanParams);
	}
}