package org.cybnity.infrastructure.common.event;

import java.util.Map;

/**
 * Provide input parameter regarding a topic (e.g event with readable
 * information).
 */
public interface InputParameterProvider {

	/**
	 * Get the incoming parameter available for only read about their value. If a
	 * parameter is passed by value, it is okay not passing variable as const as the
	 * function will have a local variable to perform operations. To avoid copying,
	 * passing by reference is preferred, therefore it is recommended to use const
	 * to prevent any changes. This set of parameters passed CANNOT be modified by
	 * the executed command.
	 * 
	 * @return A list of readable parameters. When existing, each parameter had been
	 *         initialized before being passed to the command executor. Null when
	 *         command without any input parameter.
	 */
	public Map<String, String> inParameters();
}
