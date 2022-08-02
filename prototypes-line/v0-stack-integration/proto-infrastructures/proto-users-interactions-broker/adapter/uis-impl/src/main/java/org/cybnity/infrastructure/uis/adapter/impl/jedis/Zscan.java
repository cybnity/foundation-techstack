package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

/**
 * read the members along with their scores in the form of Tuples
 *
 */
public class Zscan implements ScanStrategy<Tuple> {

	private String key;

	@Override
	public ScanResult<Tuple> scan(Jedis jedis, String cursor, ScanParams scanParams) {
		return jedis.zscan(key, cursor, scanParams);
	}
}