package org.cybnity.infrastructure.common.event;

import java.util.Calendar;
import java.util.Date;

/**
 * Common features proposed by an event, that can be reused by application
 * domains' model for specialization of specific custom domain events.
 */
public abstract class AbstractEvent implements Event {

	private Date occurredOn;
	private String correlationId;
	private String id;

	/**
	 * Constructor of event using a specific time area.
	 * 
	 * @param local A specific time area. If null, a default Calendar is used for
	 *              initialization of the event date initialized.
	 */
	public AbstractEvent(Calendar local) {
		Calendar current = (local != null) ? local : Calendar.getInstance();
		wasOccurredOn(current.getTime());
	}

	@Override
	public String id() {
		return this.id;
	}

	/**
	 * Define the unique identifier of this event.
	 * 
	 * @param uid An identifier.
	 */
	protected void identifiedBy(String uid) {
		this.id = uid;
	}

	@Override
	public String correlationId() {
		return this.correlationId;
	}

	/**
	 * Define a correlation identifier.
	 * 
	 * @param id An identifier.
	 */
	protected void setCorrelationId(String id) {
		this.correlationId = id;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

	/**
	 * Define when this event was occurred.
	 * 
	 * @param when A mandatory date.
	 * @throws IllegalArgumentException If date parameter is not defined.
	 */
	protected void wasOccurredOn(Date when) throws IllegalArgumentException {
		if (when == null) {
			throw new IllegalArgumentException("Date parameter must be defined !");
		}
		this.occurredOn = when;
	}

	/**
	 * Default implementation that return the class simple name regarding the
	 * implemented class event. Recommended to override this method by child class
	 * for ensure control of the naming convention supported by systems producing
	 * and supporting each event type.
	 */
	@Override
	public String type() {
		return this.getClass().getSimpleName();
	}
}
