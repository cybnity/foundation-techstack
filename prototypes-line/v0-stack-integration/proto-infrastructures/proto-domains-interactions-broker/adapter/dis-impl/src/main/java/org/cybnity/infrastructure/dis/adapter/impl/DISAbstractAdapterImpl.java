package org.cybnity.infrastructure.dis.adapter.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;

import org.cybnity.infrastructure.dis.adapter.api.DISAdapter;
import org.cybnity.infrastructure.dis.adapter.impl.kafka.DISKafkaClient;

public abstract class DISAbstractAdapterImpl implements DISAdapter {

	protected static String disConfigFileName = "dis-adapter-config.properties";
	protected Properties disAdapterSetting;
	protected String refName;

	public DISAbstractAdapterImpl() {
	}

	@Override
	public void init() throws Exception {
		// Define adapter's options allowing capabilities to discuss with domains
		// interactions space (don't use pool that avoid possible usable of channels
		// subscription by handlers)
		disAdapterSetting = new Properties();
		try (InputStream input = DISKafkaClient.class.getClassLoader().getResourceAsStream(disConfigFileName)) {
			if (input == null) {
				throw new MissingResourceException("Missing " + disConfigFileName + " file in classpath",
						DISKafkaClient.class.getName(), disConfigFileName);
			}
			// Load a properties file from class path, inside static method
			disAdapterSetting.load(input);
		} catch (IOException ex) {
			throw new MissingResourceException(ex.getMessage(), DISKafkaClient.class.getName(), disConfigFileName);
		}
		refName = this.getClass().getSimpleName();
	}

	/**
	 * Create a configured set of options allowing to discuss with Domains
	 * Interactions Space.
	 *
	 * @return An instance.
	 * @exception NullPointerException When a connection property is not found in
	 *                                 configuration file.
	 */
	protected String domainsInteractionsSpaceURL() throws NullPointerException {
		return null;
		/**
		 * return new
		 * StringBuffer("redis://").append(uisAdapterSetting.getProperty("redisBackendUserAccount",
		 * null))
		 * .append(":").append(uisAdapterSetting.getProperty("redisBackendUserPassword",
		 * null)).append("@") .append(uisAdapterSetting.getProperty("serverHost",
		 * null)).append(":") .append(uisAdapterSetting.getProperty("serverPort",
		 * null)).append("/") .append(uisAdapterSetting.getProperty("dbNumber",
		 * null)).toString();
		 **/
	}

	/**
	 * Get the default user account's password.
	 *
	 * @return A password or null.
	 */
	protected String authPassword() {
		return disAdapterSetting.getProperty("defaultUserPassword", null);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void checkOperationalState() {
	}

	abstract protected void sendAsyncCommand(String command) throws Exception;

	abstract protected void sendSyncCommand(String command) throws Exception;
}
