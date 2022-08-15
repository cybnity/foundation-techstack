package org.cybnity.application.asset_control.ui.system.backend.routing;

import java.util.Calendar;
import java.util.HashMap;

import org.cybnity.infrastructure.common.event.QueryEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class JSONQueryEventCreationTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	private class ConcreteQueryEvent extends QueryEvent {

		public ConcreteQueryEvent(Calendar local) {
			super(local);
			this.name = "findAssets";
			setCorrelationId("123abc");
		}

		@Override
		public HashMap<String, String> inParameters() {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("domain", "assetcontrol");
			params.put("name", "cybnity-backend-server2");
			params.put("type", "asset");
			params.put("description", "Development backend server");
			return params;
		}

		@Override
		public Integer version() {
			return Integer.valueOf(1);
		}

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommandEventCreation() {
		ConcreteQueryEvent command = new ConcreteQueryEvent(null);
		assertNotNull(command.occurredOn());
		assertNotNull(command.correlationId());
		assertNotNull(command.type());
		assertNotNull(command.name());
		assertNotNull(command.version());

		// Check input parameters existing
		System.out.println(command.inParameters());
	}

}
