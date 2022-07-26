package org.cybnity.application.asset_control.ui.system.backend.routing;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.cybnity.infrastructure.common.event.AbstractEvent;
import org.cybnity.infrastructure.common.event.CommandEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class JSONQueryEventCreationTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	private class ConcreteCommandEvent extends AbstractEvent implements CommandEvent {

		public ConcreteCommandEvent(Calendar local) {
			super(local);
			setCorrelationId("123abc");
		}

		@Override
		public String body() {
			return null;
		}

		@Override
		public List<CommandEvent> successor() {
			return null;
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
		public HashMap<String, String> outParameters() {
			return null;
		}

		@Override
		public HashMap<String, String> refParameters() {
			return null;
		}

		@Override
		public String name() {
			return "findAssets";
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommandEventCreation() {
		CommandEvent command = new ConcreteCommandEvent(null);
		assertNotNull(command.occurredOn());
		assertNotNull(command.correlationId());
		assertNotNull(command.type());
		assertNotNull(command.name());

		// Check input parameters existing
		System.out.println(command.inParameters());
	}

}
