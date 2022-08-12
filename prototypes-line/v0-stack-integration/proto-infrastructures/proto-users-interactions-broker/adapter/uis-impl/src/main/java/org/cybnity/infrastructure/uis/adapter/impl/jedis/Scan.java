package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 * simplest scan strategy, which is independent of the collection-type and reads
 * the keys, but not the value of the keys
 * 
 */
public class Scan implements ScanStrategy<String> {
	public ScanResult<String> scan(Jedis jedis, String cursor, ScanParams scanParams) {
		return jedis.scan(cursor, scanParams);
	}
}