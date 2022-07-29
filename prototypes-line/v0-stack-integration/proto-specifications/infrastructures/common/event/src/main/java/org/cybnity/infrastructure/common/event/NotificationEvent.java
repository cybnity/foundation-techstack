package org.cybnity.infrastructure.common.event;

/**
 * Contract about an event regarding a change observed on a topic. This type of
 * event can be pushed by a domain regarding a modified attribute value
 * performed on an entity under its responsibility, and acts as a change event
 * allowing to possible observers (e.g domain consumers) to be informed about a
 * change fact that could interested (e.g for synchronization of their local
 * domain or external domain reference upgraded information).
 */
public interface NotificationEvent extends Event {
}