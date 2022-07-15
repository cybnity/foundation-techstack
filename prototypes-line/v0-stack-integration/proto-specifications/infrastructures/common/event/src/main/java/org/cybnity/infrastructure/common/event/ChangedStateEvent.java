package org.cybnity.infrastructure.common.event;

/**
 * Contract about an event regarding a change observed on a topic. This type of
 * event can be pushed by a domain regarding a modified attribute value
 * performed on an entity under its responsibility.
 */
public interface ChangedStateEvent extends Event {

}