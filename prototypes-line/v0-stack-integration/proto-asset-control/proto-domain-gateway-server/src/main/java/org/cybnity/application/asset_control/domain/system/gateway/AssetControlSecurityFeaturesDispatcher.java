package org.cybnity.application.asset_control.domain.system.gateway;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cybnity.application.asset_control.DomainSecurityFeature;
import org.cybnity.feature.asset_control.api.SecurityFeatureChannel;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.common.event.Identifiable;
import org.cybnity.infrastructure.common.event.InputParameterProvider;
import org.cybnity.infrastructure.uis.adapter.impl.lettuce.PubSubChannelListener;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.vertx.core.json.Json;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

/**
 * Handler of Asset Control main entry points (channel) that collect any event
 * requesting security features execution, and which manage the event security
 * validity, the identification of the specific feature (service) to forward and
 * optional enhancement of the event required by the Security features boundary.
 */
public class AssetControlSecurityFeaturesDispatcher extends DomainSecurityFeature {

	private FeaturesBoundaryDestinationList routes;
	private SecurityFeatureChannel apiEntryPoint = SecurityFeatureChannel.asset_control;
	private StatefulRedisPubSubConnection<String, String> connection;
	private List<EntryPointEventsListener> capabilitiesLayerListeners = new LinkedList<EntryPointEventsListener>();
	private List<OutputPointEventsListener> domainsLayerListeners = new LinkedList<OutputPointEventsListener>();

	public AssetControlSecurityFeaturesDispatcher() {
		super();
		routes = new FeaturesBoundaryDestinationList();
	}

	@Override
	protected void registerUsersInteractionsSpaceHandlers() throws Exception {
		// Create the subscription consumer attached to UIS events managed by this
		// features domain
		// Attach the listener of channel to delegate treatment of each event relative
		// to any feature
		connection = uiSpace().redisClient().connectPubSub();
		// Create set of listener managed by this handler
		capabilitiesLayerListeners.add(new EntryPointEventsListener(apiEntryPoint.name()));

		// Install listeners on connection with UIS layer
		for (EntryPointEventsListener listener : capabilitiesLayerListeners) {
			connection.addListener(listener); // listener installed
			connection.async().subscribe(listener.monitoredChannel()); // channel subscribed
		}
	}

	@Override
	protected void registerDomainsInteractionsSpaceHandlers() throws Exception {
		KafkaProducer<String, String> sharedProducer = diSpace().kafkaSharedProducer();

		// TODO Create the subscription consumer attached to DIS events managed by this
		// domain (regarding domain events to promote on the UI space)
		// TODO Attach the listener of channel to delegated notification/answer of
		// domain event relative to any feature

		// TODO create set of listener managed by this handler

		// TODO Install listeners on connection with DIS layer

	}

	@Override
	public void stop() throws Exception {
		// Free resources
		for (EntryPointEventsListener listener : capabilitiesLayerListeners) {
			// Stop active subscription
			connection.async().unsubscribe(listener.monitoredChannel());
			// remove listener from connection
			connection.removeListener(listener);
		}
		for (OutputPointEventsListener listener : domainsLayerListeners) {
			// TODO Stop active subscription

			// DOTO remove listener from connection

		}
		super.stop();
	}

	/**
	 * Handler of channel managed via a pub/sub mode that collect and forward events
	 * processed like output data from the DIS layer.
	 *
	 */
	private class OutputPointEventsListener
			extends org.cybnity.infrastructure.dis.adapter.impl.kafka.PubSubChannelListener {
		public OutputPointEventsListener(String channelLabel) {
			super(channelLabel);
		}

		@Override
		public void onMessage(String channel, Event event) {
			System.out.println("Event (correlationId: " + event.correlationId() + ") output from AC boundary via the "
					+ channel + " channel");
			// TODO Promote the domain events coming from the domain space to the output UIS
			// channel (UI layer)
		}
	}

	/**
	 * Handler of channel managed via a pub/sub mode that process events coming like
	 * entry data from the UIS layer.
	 */
	private class EntryPointEventsListener extends PubSubChannelListener {

		public EntryPointEventsListener(String channelLabel) {
			super(channelLabel);
		}

		/**
		 * Execute a Routing Slip pattern implementation regarding the entry point of
		 * the AC features domain.
		 */
		@Override
		public void onMessage(String channel, Event event) {
			System.out.println("Event (correlationId: " + event.correlationId() + ") entry into AC boundary via the "
					+ channel + " channel");
			// - Sequence of validation (e.g event
			// conformity, complementary information injection on event)

			// - Identification of unit capability to start (e.g reconfigure the route of
			// event to follow into dedicated channel relative to a Security features
			if (InputParameterProvider.class.isAssignableFrom(event.getClass())) {
				InputParameterProvider command = (InputParameterProvider) event;
				Map<String, String> inParams = command.inParameters();
				if (inParams != null) {
					String domainName = inParams.getOrDefault("domain", null);
					if (domainName != null) {
						if (apiEntryPoint.name().equals(domainName) || apiEntryPoint.acronym().equals(domainName)) {
							// Command event need to be treated by this Security feature domain

							// Identify the entry point (channel) supporting the service
							if (Identifiable.class.isAssignableFrom(event.getClass())) {
								SecurityFeatureChannel toForwardAt = routes.recipient(((Identifiable) event).name());
								if (toForwardAt != null) {
									// Forward the treatment to the feature's dedicated channel on Domains
									// Interactions Space
									KafkaProducerRecord<String, String> record = KafkaProducerRecord
											.create(toForwardAt.name(), Json.encode(event));
									KafkaProducer<String, String> sharedProducer = diSpace().kafkaSharedProducer();
									sharedProducer.write(record);

									System.out.println("Event (correlationId: " + event.correlationId()
											+ ") delegated to RT stream compuation unit (channel: " + toForwardAt.name()
											+ ") for processing");

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
							System.out.println(
									"Error on UIS entry point listening configuration because the event is not supported by this AC handler!");
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
