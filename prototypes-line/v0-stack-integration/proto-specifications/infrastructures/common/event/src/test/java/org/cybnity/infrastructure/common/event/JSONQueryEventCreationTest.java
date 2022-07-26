package org.cybnity.infrastructure.common.event;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JSONQueryEventCreationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private class ConcreteCommandEvent extends AbstractEvent implements CommandEvent {

		public ConcreteCommandEvent(Calendar local) {
			super(local);
			setCorrelationId("123abc");
		}

		@Override
		public String name() {
			return "createAsset";
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
			return null;
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

	@Test
	public void testCommandEventCreation() {
		CommandEvent command = new ConcreteCommandEvent(null);
		assertNotNull(command.occurredOn());
		assertNotNull(command.correlationId());
		assertNotNull(command.name());
		assertNotNull(command.type());
	}

}
