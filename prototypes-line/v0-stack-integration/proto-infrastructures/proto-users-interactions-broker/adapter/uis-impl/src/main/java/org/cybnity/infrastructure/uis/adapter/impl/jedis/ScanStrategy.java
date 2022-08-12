package org.cybnity.infrastructure.uis.adapter.impl.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 * SCAN STRATEGIES Sscan can be used for iterating through sets Hscan helps us
 * iterate through pairs of field-value in a hash Zscan allows an iteration
 * through members stored in a sorted set
 * 
 * search-pattern and result-size, to effectively control the scanning –
 * ScanParams makes this happen
 * 
 * see sample at https://www.baeldung.com/redis-list-available-keys
 * 
 **/
public interface ScanStrategy<T> {
	ScanResult<T> scan(Jedis jedis, String cursor, ScanParams scanParams);
}