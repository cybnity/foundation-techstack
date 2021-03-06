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
			HashMap<String, String> params = new HashMap();
			params.put("domain", "assetcontrol");
			params.put("name", "an asset label");
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCommandEventCreation() {
		CommandEvent command = new ConcreteCommandEvent(null);
		assertNotNull(command.occurredOn());
		assertNotNull(command.correlationId());
		// Check input parameters existing
		System.out.println(command.inParameters());
	}

}
