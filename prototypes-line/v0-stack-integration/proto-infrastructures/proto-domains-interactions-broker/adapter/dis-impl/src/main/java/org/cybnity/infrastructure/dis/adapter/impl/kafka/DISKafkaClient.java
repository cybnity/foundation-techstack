package org.cybnity.infrastructure.dis.adapter.impl.kafka;

import java.util.Collections;
import java.util.Map.Entry;

import org.cybnity.infrastructure.dis.adapter.api.ChannelListener;
import org.cybnity.infrastructure.dis.adapter.api.DISAdapter;
import org.cybnity.infrastructure.dis.adapter.impl.DISAbstractAdapterImpl;

import io.vertx.core.Vertx;
import io.vertx.kafka.admin.KafkaAdminClient;
import io.vertx.kafka.admin.NewTopic;
import io.vertx.kafka.admin.TopicDescription;
import io.vertx.kafka.client.common.ConfigResource;
import io.vertx.kafka.client.common.TopicPartitionInfo;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.producer.KafkaProducer;

/**
 * Implementation client allowing discussion with Domains Interactions Space
 * (Kafka broker) via Vert.x Kafka integration library.
 */
public class DISKafkaClient extends DISAbstractAdapterImpl implements DISAdapter {
	private Vertx context;
	private String sharedProducerName = "dis-shared-producer";
	private KafkaProducer<String, String> sharedProducer;

	public DISKafkaClient(Vertx context) {
		super();
		this.context = context;
	}

	/**
	 * Initialize the Lettuce instances allowing to provide/use connection to Kafka.
	 *
	 * @throws Exception When instantiation problem.
	 */
	public void init() throws Exception {
		super.init();
		// Define a thread-safe connection to a Kafka server that will maintain its
		// connection to the server and reconnect if needed. Once we have a connection,
		// we can use
		// it to execute Kafka commands either synchronously or asynchronously
		sharedProducer = KafkaProducer.createShared(context, sharedProducerName, disAdapterProducerSetting);
	}

	@Override
	public void createChannel(String channelName) throws IllegalArgumentException {
		if (channelName == null || channelName.equals(""))
			throw new IllegalArgumentException("Channel name parameter must be defined!");
		KafkaAdminClient admin = KafkaAdminClient.create(context, disAdapterConsumerSetting);
		// Verify if the topic is already existing for need to be updated in place of to
		// be created
		admin.listTopics().onSuccess(topics -> {
			boolean existingTopic = topics.contains(channelName);
			// For partition help, see example at https://dzone.com/articles/partitioning-with-apache-kafka-and-vertx 
			int numPartitions = 2; // Create 2 partitions about the channel (partition zero for single-event processor or all-event processor; partition 1 for all-event processor and multiple-event processor)
			short replicationFactor = (short) 1;
			if (!existingTopic) {
				// Define the configuration of the topic according to the cluster of brokers
				NewTopic topic = new NewTopic(channelName /** topic name **/
						, numPartitions /** partitions number **/
						, replicationFactor/** factor replication **/
				);
				

				// Create the topic
				admin.createTopics(Collections.singletonList(topic)).onSuccess(v -> {
					System.out.println(channelName + " kafka channel (topic) successfully created");
				}).onFailure(cause -> {
					System.out.println("Failed channel (topic) creation: " + cause);
				});
			} else {
				System.out.println(channelName + " is already existing");

				admin.describeTopics(Collections.singletonList(channelName)).onSuccess(channels -> {
					for (Entry<String, TopicDescription> item : channels.entrySet()) {
						TopicDescription description = item.getValue();
						// Read description
						System.out.println("Topic name=" + description.getName() + " isInternal="
								+ description.isInternal() + " partitions=" + description.getPartitions().size());
						// Identify detailed partition info
						for (TopicPartitionInfo topicPartInfo : description.getPartitions()) {
							System.out.println("Partition id=" + topicPartInfo.getPartition() + " leaderId="
									+ topicPartInfo.getLeader().getId() + " replicas=" + topicPartInfo.getReplicas()
									+ " isr=" + topicPartInfo.getIsr());
						}
						// Verify if required to alter the topic configuration

						// Update the existing topic configuration
						admin.describeConfigs(Collections.singletonList(new ConfigResource(
								org.apache.kafka.common.config.ConfigResource.Type.TOPIC, channelName)))
								.onSuccess(configs -> {
									System.out.println("Existing " + channelName + " channel (topic) config");
								});

					}
				});
			}
		}).onFailure(cause -> {
			System.out.println("Topic listing problem: " + cause);
		});
	}

	@Override
	public void addListener(ChannelListener channel, String observabilityPattern, ChannelMode mode) {
		if (mode != null && DISAdapter.ChannelMode.PUB_SUB_MODE == mode) {
			// Create observer utility instance
			PubSubChannelListener observer = new PubSubChannelTraceListener(channel.monitoredChannel());

			// TODO Connect and add listener on connection PubSub mode

			// TODO Create subscription to channel
		}
	}

	/**
	 * Get a Kafka consumer connected to space.
	 * 
	 * @return A new instance of consumer.
	 */
	public KafkaConsumer<String, String> kafkaConsumer() {
		return KafkaConsumer.create(context, disAdapterConsumerSetting);
	}

	/**
	 * Get a Kafka producer connected to space.
	 *
	 * @return A producer.
	 */
	public KafkaProducer<String, String> kafkaProducer() {
		return KafkaProducer.create(context, disAdapterProducerSetting);
	}

	/**
	 * Get shared Kafka producer connected to space.
	 *
	 * @return A producer.
	 */
	public KafkaProducer<String, String> kafkaSharedProducer() {
		return this.sharedProducer;
	}

	@Override
	public void checkOperationalState() {
		// TODO Test the connection to the space broker validating the operational state
		// of adapter connection
	}

	/**
	 * Free Kafka client resources.
	 */
	public void dispose() {
		if (sharedProducer != null)
			sharedProducer.close();
		super.dispose();
	}

	protected void sendAsyncCommand(String command) throws Exception {
		System.out.println("send async command over the client connection...");
	}

	protected void sendSyncCommand(String command) throws Exception {
		System.out.println("send sync command over the client connection...");
	}
}
