package org.cybnity.application.asset_control.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cybnity.application.asset_control.DomainProcessingFeature;
import org.cybnity.feature.asset_control.api.SecurityFeatureChannel;
import org.cybnity.infrastructure.common.event.CommandEvent;
import org.cybnity.infrastructure.common.event.CommonEvent;
import org.cybnity.infrastructure.common.event.Event;
import org.cybnity.infrastructure.common.event.InputParameterProvider;
import org.cybnity.infrastructure.common.event.NotificationEvent;
import org.cybnity.infrastructure.common.event.QueryEvent;

import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumer;

/**
 * Processing feature ensuring the realization of an asset creation.
 */
public class CreateAssetFeature extends DomainProcessingFeature {
	private SecurityFeatureChannel featureEntryPoint = SecurityFeatureChannel.ac_createAsset;
	private List<EntryPointEventsListener> featureInputListeners = new LinkedList<EntryPointEventsListener>();
	private List<KafkaConsumer<String, String>> activatedConsumers = new LinkedList<KafkaConsumer<String, String>>();
	private List<OutputPointEventsListener> featureOutputListeners = new LinkedList<OutputPointEventsListener>();

	public CreateAssetFeature() {
		super();
	}

	@Override
	protected void registerDomainsInteractionsSpaceHandlers() throws Exception {
		featureInputListeners.clear();
		activatedConsumers.clear();
		// Create the handlers of consumed events according to the use cases supported
		// by this feature
		featureInputListeners.add(new EntryPointEventsListener(featureEntryPoint.name()));

		// Install listeners on connection with UIS layer
		for (EntryPointEventsListener listener : featureInputListeners) {
			final KafkaConsumer<String, String> featureInputConsumer = diSpace().kafkaConsumer();
			// Define handling delegation to the entry point listener
			featureInputConsumer.handler(record -> {
				// System.out.println("Topic received record (key: " + record.key() + ", value:
				// " + record.value() + ")");

				// Execute the delegation to the channel listener
				// Serialize the message as non type object that is equals to a Map
				JsonObject json = new JsonObject(record.value());
				// Identify which type of class type is defined for the event type
				String eventType = json.getString("type", null);
				if (eventType != null) {
					switch (eventType) {
					case "CommandEvent":
						try {
							// delegate the event treatment to onMessage(String channel, Event event)
							listener.onMessage(listener.monitoredChannel(), json.mapTo(CommandEvent.class));
						} catch (Exception ioe) {
							ioe.printStackTrace();
						}
						break;
					case "QueryEvent":
						try {
							// delegate the event treatment to onMessage(String channel, Event event)
							listener.onMessage(listener.monitoredChannel(), json.mapTo(QueryEvent.class));
						} catch (Exception ioe) {
							ioe.printStackTrace();
						}
						break;
					case "NotificationEvent":
						try {
							// delegate the event treatment to onMessage(String channel, Event event)
							listener.onMessage(listener.monitoredChannel(), json.mapTo(NotificationEvent.class));
						} catch (Exception ioe) {
							ioe.printStackTrace();
						}
						break;
					default:
						try {
							// delegate the event treatment to onMessage(String channel, Event event)
							listener.onMessage(listener.monitoredChannel(), json.mapTo(CommonEvent.class));
						} catch (Exception ioe) {
							ioe.printStackTrace();
						}
						break;
					}

					// At least once delivery to be sure that the read messages are processed before
					// committing the offset
					featureInputConsumer.commit().onSuccess(v -> System.out
							.println("Last read message offset committed by " + this.getClass().getSimpleName()));
				} else {
					// Ignore event that is not supported by this feature queue
				}
			});

			featureInputConsumer.subscribe(listener.monitoredChannel()).onSuccess(v -> {
				System.out.println(CreateAssetFeature.class.getSimpleName() + " subscriptions: "
						+ listener.monitoredChannel() + " success");
				featureInputConsumer.resume();
			});

			// Keep reference of registered consumer
			activatedConsumers.add(featureInputConsumer);
		}
	}

	@Override
	public void stop() throws Exception {
		// Free resources
		for (KafkaConsumer<String, String> listener : activatedConsumers) {
			// Stop active subscription
			listener.unsubscribe().onFailure(e -> System.out.println("Failure during unsubscription: " + e.getCause()));
		}
		for (OutputPointEventsListener listener : featureOutputListeners) {
			// TODO Stop active subscription

			// TODO remove listener from connection

		}
		super.stop();
	}

	/**
	 * Handler of channel managed via a pub/sub mode that process events coming like
	 * entry data to the implemented feature from the DIS layer.
	 */
	private class EntryPointEventsListener
			extends org.cybnity.infrastructure.dis.adapter.impl.kafka.PubSubChannelListener {

		public EntryPointEventsListener(String channelLabel) {
			super(channelLabel);
		}

		/**
		 * Execute a Routing Slip pattern implementation regarding the entry point of
		 * the AC features domain.
		 */
		@Override
		public void onMessage(String channel, Event event) {
			// - Sequence of validation (e.g event
			// conformity, complementary information injection on event)

			// - Identification of feature use case to execute (e.g reconfigure the route of
			// event to follow into dedicated channel relative to a Security feature's use
			// cases)
			if (InputParameterProvider.class.isAssignableFrom(event.getClass())) {
				InputParameterProvider command = (InputParameterProvider) event;
				Map<String, String> inParams = command.inParameters();
				if (inParams != null) {
					String domainName = inParams.getOrDefault("domain", null);
					if (domainName != null) {
						// System.out.println("Domain of processed events is: " + domainName);
					} else {
						System.out.println("Ignored event caused by missing domain name in event (correlationId: "
								+ event.correlationId() + ")!");
					}
				}
			}

			// Simulate event processing and feature realization
			System.out.println("Event (correlationId: " + event.correlationId()
					+ ") entry into ACPU was successfully processed with success by "
					+ CreateAssetFeature.class.getSimpleName() + " via " + featureEntryPoint.name() + " channel");
		}
	}

	/**
	 * Handler of channel managed via a pub/sub mode that collect results of
	 * processed events by this feature like output data from the DIS layer and
	 * optionally create output events to DIS space.
	 */
	private class OutputPointEventsListener
			extends org.cybnity.infrastructure.dis.adapter.impl.kafka.PubSubChannelListener {
		public OutputPointEventsListener(String channelLabel) {
			super(channelLabel);
		}

		@Override
		public void onMessage(String channel, Event event) {
			System.out.println("Event (correlationId: " + event.correlationId()
					+ ") output from ACPU.createAsset feature via the " + channel + " channel");
		}
	}
}
