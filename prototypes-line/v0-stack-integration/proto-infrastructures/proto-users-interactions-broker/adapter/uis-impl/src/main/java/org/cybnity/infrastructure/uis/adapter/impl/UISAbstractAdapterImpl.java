package org.cybnity.infrastructure.uis.adapter.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;

import org.cybnity.infrastructure.uis.adapter.api.UISAdapter;
import org.cybnity.infrastructure.uis.adapter.impl.jedis.UISJedisClient;

public abstract class UISAbstractAdapterImpl implements UISAdapter {

	protected static String uisConfigFileName = "uis-adapter-config.properties";
	protected Properties uisAdapterSetting;
	protected String refName;

	public UISAbstractAdapterImpl() {
	}

	@Override
	public void init() throws Exception {
		// Define adapter's options allowing capabilities to discuss with users
		// interactions space (don't use pool that avoid possible usable of channels
		// subscription by handlers)
		uisAdapterSetting = new Properties();
		try (InputStream input = UISJedisClient.class.getClassLoader().getResourceAsStream(uisConfigFileName)) {
			if (input == null) {
				throw new MissingResourceException("Missing " + uisConfigFileName + " file in classpath",
						UISJedisClient.class.getName(), uisConfigFileName);
			}
			// Load a properties file from class path, inside static method
			uisAdapterSetting.load(input);
		} catch (IOException ex) {
			throw new MissingResourceException(ex.getMessage(), UISJedisClient.class.getName(), uisConfigFileName);
		}
		refName = this.getClass().getSimpleName();
	}

	/**
	 * Create a configured set of options allowing to discuss with Users
	 * Interactions Space.
	 * 
	 * @return An instance.
	 * @exception NullPointerException When a connection property is not found in
	 *                                 configuration file.
	 */
	protected String usersInteractionsSpaceURL() throws NullPointerException {
		return new StringBuffer("redis://").append(uisAdapterSetting.getProperty("redisBackendUserAccount", null))
				.append(":").append(uisAdapterSetting.getProperty("redisBackendUserPassword", null)).append("@")
				.append(uisAdapterSetting.getProperty("serverHost", null)).append(":")
				.append(uisAdapterSetting.getProperty("serverPort", null)).append("/")
				.append(uisAdapterSetting.getProperty("dbNumber", null)).toString();
	}

	/**
	 * Get the default user account's password.
	 * 
	 * @return A password or null.
	 */
	protected String authPassword() {
		return uisAdapterSetting.getProperty("defaultUserPassword", null);
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
