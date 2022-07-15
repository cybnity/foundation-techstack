package org.cybnity.infrastructure.common.event;

import java.util.Date;

/**
 * This type of common event is not owned by specific domain, but can be reused
 * between infrastructure layer's components.
 */
public interface Event {

	/**
	 * Get unique identifier that is added to the very first interaction (incoming
	 * request) to identify the context and its passed to all components that are
	 * involved in the transaction flow. Correlation ID becomes the glue that binds
	 * the transaction together and helps to draw an overall picture of the event.
	 * 
	 * @return An identifier or null.
	 */
	public String correlationId();

	/**
	 * When the event was observed.
	 * 
	 * @return A date.
	 */
	public Date occurredOn();

	/**
	 * Get unique identifier of this event.
	 * 
	 * @return An identifier or null.
	 */
	public String id();

	/**
	 * Get detailed information relative to this event as its body that contain data
	 * usable to understand and/or to treat the event.
	 * 
	 * @return An information structure. Null when none event detail is known.
	 */
	public String body();

	/**
	 * Get the type name of this event (e.g that help to understand the nature of
	 * this event).
	 * 
	 * @return A type of this event.
	 */
	public String type();
}
