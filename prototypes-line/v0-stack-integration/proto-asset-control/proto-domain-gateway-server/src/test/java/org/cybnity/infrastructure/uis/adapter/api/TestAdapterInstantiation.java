package org.cybnity.infrastructure.uis.adapter.api;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdapterInstantiation {

	private UISAdapterAbstractFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new UISAdapterAbstractFactory();
	}

	@After
	public void tearDown() throws Exception {
		factory = null;
	}

	@Test
	public void testJedisAdapterCreation() throws Exception {
		UISAdapterFactory af = factory.getInstance(UISAdapterAbstractFactory.AdapterType.JEDIS_ADAPTER);
		assertNotNull("Supported Jedis client factory not found!", af);
		assertNotNull("instance should be created!", af.create());
	}

	@Test
	public void testLettuceAdapterCreation() throws Exception {
		UISAdapterFactory af = factory.getInstance(UISAdapterAbstractFactory.AdapterType.LETTUCE_ADAPTER);
		assertNotNull("Supported Lettuce client factory not found!", af);
		assertNotNull("instance should be created!", af.create());
	}

}
