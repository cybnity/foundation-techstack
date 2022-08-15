package org.cybnity.infrastructure.common.event;

import static org.junit.Assert.assertNotNull;

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

	private class ConcreteCommandEvent extends CommandEvent {

		public ConcreteCommandEvent() {
			super();
			setCorrelationId("123abc");
			this.name = "createAsset";
			this.version = Integer.valueOf(1);
		}
	}

	@Test
	public void testCommandEventCreation() {
		CommandEvent command = new ConcreteCommandEvent();
		assertNotNull(command.occurredOn());
		assertNotNull(command.correlationId());
		assertNotNull(command.name());
		assertNotNull(command.type());
		assertNotNull(command.version());
	}

}
