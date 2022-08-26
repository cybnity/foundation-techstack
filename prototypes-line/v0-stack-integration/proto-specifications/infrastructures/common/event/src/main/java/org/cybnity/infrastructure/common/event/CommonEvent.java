package org.cybnity.infrastructure.common.event;

import java.util.Calendar;
import java.util.Date;

/**
 * Common features proposed by an event, that can be reused by application
 * domains' model for specialization of specific custom domain events.
 */
public class CommonEvent implements Event, Identifiable {

	public String name, type;
	public Date occurredOn;
	public String correlationId;
	public String id;
	public String body, tenantId;
	public Integer version;
	public AuthenticationCredential authenticationCredential;

	/**
	 * Constructor of event using a specific time area.
	 * 
	 * @param local A specific time area. If null, a default Calendar is used for
	 *              initialization of the event date initialized.
	 */
	public CommonEvent(Calendar local) {
		Calendar current = (local != null) ? local : Calendar.getInstance();
		wasOccurredOn(current.getTime());
	}

	public CommonEvent() {
		wasOccurredOn(Calendar.getInstance().getTime());
	}

	@Override
	public AuthenticationCredential authenticationCredential() {
		return this.authenticationCredential;
	}

	@Override
	public String name() {
		return this.name;
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
	 * Define the credential that is attached to this event regarding the producer
	 * who generated him.
	 * 
	 * @param credential A credential.
	 */
	protected void setAuthenticationCredential(AuthenticationCredential credential) {
		this.authenticationCredential = credential;
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
		return (type != null) ? type : this.getClass().getSimpleName();
	}

	@Override
	public Integer version() {
		return this.version;
	}

	@Override
	public String tenantId() {
		return this.tenantId;
	}

	@Override
	public String body() {
		return this.body;
	}
}
