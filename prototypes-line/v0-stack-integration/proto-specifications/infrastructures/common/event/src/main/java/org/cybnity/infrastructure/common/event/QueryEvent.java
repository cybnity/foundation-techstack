package org.cybnity.infrastructure.common.event;

import java.util.HashMap;

/**
 * Based on the CQRS (Command Query Responsibility Segregation) pattern, a query
 * event return a result and do not change the observable state of a topic.
 * 
 * When the parameter mode is IN/OUT, it signals that the function requires the
 * value passed in the parameter, read the value, then later modify it. Function
 * requires both input and output values.
 * 
 * Why are these notation necessary? These notations help programmers to write
 * simpler, more efficient, more maintainable code. Also help programmers be
 * more specific passing values to the functions to prevent unknown behavior.
 */
public interface QueryEvent extends Event {
	
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
	public HashMap<String, String> inParameters();

	/**
	 * Get the outgoing parameters that the function to execute does not require to
	 * read the value, parameter value is of no importance. In fact the parameters
	 * marked are needed to output multiple values. Such parameters are needed to be
	 * pointers, references and structures. This set of parameters passed MUST be
	 * modified by the executed command.
	 * 
	 * @return A list of output parameters (e.g return channel name) that should be
	 *         valued by the executed command. Null when none predetermined are
	 *         requested to be delivered.
	 */
	public HashMap<String, String> outParameters();

	/**
	 * Get the reference parameters that the function to execute does not require to
	 * read the value, parameter value is of no importance. In fact the parameters
	 * marked are maybe modified to output multiple values. Such parameters are
	 * needed to be pointers, references and structures. This set of parameters
	 * passed MAY be modified by the executed command.
	 * 
	 * @return A list of output parameters (e.g a result channel when a notification
	 *         of execution result can be published) that could be valued by the
	 *         executed command. When existing, each parameter had been initialized
	 *         before being passed to the command executor. Null when none
	 *         predetermined are requested to be delivered.
	 */
	public HashMap<String, String> refParameters();
}
