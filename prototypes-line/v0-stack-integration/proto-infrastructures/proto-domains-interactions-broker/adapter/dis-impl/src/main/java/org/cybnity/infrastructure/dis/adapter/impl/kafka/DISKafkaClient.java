package org.cybnity.infrastructure.dis.adapter.impl.kafka;

import org.cybnity.infrastructure.dis.adapter.api.ChannelListener;
import org.cybnity.infrastructure.dis.adapter.api.DISAdapter;
import org.cybnity.infrastructure.dis.adapter.impl.DISAbstractAdapterImpl;

import io.vertx.core.Vertx;
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
		sharedProducer = KafkaProducer.createShared(context, sharedProducerName, disAdapterSetting);
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
