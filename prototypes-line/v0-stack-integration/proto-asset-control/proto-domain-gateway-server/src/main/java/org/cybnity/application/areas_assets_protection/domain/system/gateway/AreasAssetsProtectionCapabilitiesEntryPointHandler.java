package org.cybnity.application.areas_assets_protection.domain.system.gateway;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cybnity.application.areas_assets_protection.domain.system.gateway.capabilities.UISecurityCapability;
import org.cybnity.feature.areas_assets_protection.api.DomainsBoundaryDestinationList;
import org.cybnity.feature.areas_assets_protection.api.DomainsBoundaryDestinationList.DelegatedDomainAPIChannel;
import org.cybnity.feature.areas_assets_protection.api.UICapabilityChannel;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.common.event.Identifiable;
import org.cybnity.infrastructure.common.event.InputParameterProvider;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.PubSubChannelListener;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.UISLettuceClient;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.vertx.core.json.Json;

/**
 * Handler of Areas and Assets Protection main entry points (channel) that
 * collect any event requesting UI capability execution, and which manage the
 * event security validity, the identification of the specific capability to
 * forward and optional enhancement of the event required by the UI capabilities
 * boundary.
 */
public class AreasAssetsProtectionCapabilitiesEntryPointHandler extends UISecurityCapability {

	private CapabilitiesBoundaryDestinationList routes;
	private DomainsBoundaryDestinationList otherDomainsChannels;
	private UICapabilityChannel apiEntryPoint = UICapabilityChannel.areas_assets_protection;
	private StatefulRedisPubSubConnection<String, String> connection;
	private List<EntryPointEventsListener> activeListeners = new LinkedList<EntryPointEventsListener>();

	public AreasAssetsProtectionCapabilitiesEntryPointHandler() {
		super();
		routes = new CapabilitiesBoundaryDestinationList();
		otherDomainsChannels = new DomainsBoundaryDestinationList();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UIS events managed by this
		// capabilities domain
		UISLettuceClient space = uiSpace();
		RedisClient client = space.redisClient();
		// Attach the listener of channel to delegate treatment of each event relative
		// to any capability
		connection = client.connectPubSub();
		// Create set of listener manager by this handler
		activeListeners.add(new EntryPointEventsListener(apiEntryPoint.name()));

		// Install listeners on connection
		for (EntryPointEventsListener listener : activeListeners) {
			connection.addListener(listener); // listener installed
			connection.async().subscribe(listener.monitoredChannel()); // channel subscribed
		}
	}

	@Override
	public void stop() throws Exception {
		// Free resources
		for (EntryPointEventsListener listener : activeListeners) {
			// Stop active subscription
			connection.async().unsubscribe(listener.monitoredChannel());
			// remove listener from connection
			connection.removeListener(listener);
		}
		if (connection != null && connection.isOpen()) {
			connection.close();
		}
		super.stop();
	}

	/**
	 * Handler of channel managed via a pub/sub mode that process.
	 */
	private class EntryPointEventsListener extends PubSubChannelListener {

		public EntryPointEventsListener(String channelLabel) {
			super(channelLabel);
		}

		/**
		 * Execute a Routing Slip pattern implementation regarding the entry point of
		 * the AAP capabilities domain.
		 */
		@Override
		public void onMessage(String channel, Event event) {
			System.out.println("Event (correlationId: " + event.correlationId() + ") entry into AAP boundary via the "
					+ channel + " channel");
			// - Sequence of validation (e.g event
			// conformity, complementary information injection on event)

			// - Identification of unit capability to start (e.g reconfigure the route of
			// event to follow into dedicated channel relative to a UI capabilities and/or
			// to a specific Application Domain feature)
			if (InputParameterProvider.class.isAssignableFrom(event.getClass())) {
				InputParameterProvider command = (InputParameterProvider) event;
				Map<String, String> inParams = command.inParameters();
				if (inParams != null) {
					String domainName = inParams.getOrDefault("domain", null);
					if (domainName != null) {
						// Validate if the event should be treated by this UI capabilities boundary
						boolean delegatedEvent = false;
						if (apiEntryPoint.name().equals(domainName) || apiEntryPoint.acronym().equals(domainName)) {
							// Command event need to be treated by this UI capability domain

							// Identify the entry point (channel) supporting the feature
							if (Identifiable.class.isAssignableFrom(event.getClass())) {
								UICapabilityChannel toForwardAt = routes.recipient(((Identifiable) event).name());
								if (toForwardAt != null) {
									// Forward the treatment to the feature's dedicated channel on Users
									// Interactions Space
									UISLettuceClient space = uiSpace();
									RedisClient client = space.redisClient();
									StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
									// Publish the event to channel
									connection.async().publish(toForwardAt.name(), Json.encode(event));

									/*
									 * System.out.println("Event (correlationId: " + event.correlationId() +
									 * ") delegated to capability's channel (" + toForwardAt.name() +
									 * ") for treatment");
									 */

									// confirm dispatched event
									delegatedEvent = true;
								} else {
									System.out.println(
											"None feature's channel/route identified regarding the support of the event (correlationId: "
													+ event.correlationId() + ")");
								}
							} else {
								System.out.println("The unidentifiable event (correlationId: " + event.correlationId()
										+ ") can't be treated!");
							}
						} else {
							// Search if event can be delegated to any other domain's entry point which is
							// integrated with the AAP capabilities facade
							for (DelegatedDomainAPIChannel delegation : otherDomainsChannels.entryPointChannels()) {
								if (delegation.name().equalsIgnoreCase(domainName)) {
									// Command event
									// Forward the treatment to another application domain integrated by this UI
									// capability boundary
									UISLettuceClient space = uiSpace();
									RedisClient client = space.redisClient();
									StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
									// Publish the event to channel
									connection.async().publish(delegation.name(), Json.encode(event));

									System.out.println("Event (correlationId: " + event.correlationId()
											+ ") dispatched to feature's channel (" + delegation.name()
											+ ") for treatment");

									// confirm dispatched event
									delegatedEvent = true;
								}
							}
						}
						if (!delegatedEvent) {
							System.out.println(
									"Error on UIS entry point listening configuration because the event is not supported by this AAP handler!");
						}
					} else {
						System.out.println("Ignored event caused by missing domain name in event (correlationId: "
								+ event.correlationId() + ")!");
					}
				}
			}
		}
	}
}
