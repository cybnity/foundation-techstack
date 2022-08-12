package org.cybnity.infrastructure.common.event;

import java.util.List;
import java.util.Map;

/**
 * Based on the CQRS (Command Query Responsibility Segregation) pattern, a
 * command event change the state of a topic (e.g entity, system, domain) but do
 * not necessarily return a value. It's an intent that allow setting for request
 * of a change with one or several ordered sub-operations (e.g implementation of
 * a responsibility chain pattern) with possible management of reversibility of
 * operations.
 * 
 * When the parameter mode is IN/OUT, it signals that the function requires the
 * value passed in the parameter, read the value, then later modify it. Function
 * requires both input and output values.
 * 
 * Why are these notation necessary? These notations help programmers to write
 * simpler, more efficient, more maintainable code. Also help programmers be
 * more specific passing values to the functions to prevent unknown behavior.
 */
public class CommandEvent extends CommonEvent implements InputParameterProvider {
	public List<CommandEvent> successor;
	public Map<String, String> outParameters;
	public Map<String, String> refParameters;
	public Map<String, String> inParameters;

	public CommandEvent() {
		super();
	}

	/**
	 * Get ordered list of operations that are requested to be executed.
	 * 
	 * @return A list of next operations (e.g instances of event including each one
	 *         a channel name as InParameter supporting a responsibility chain of
	 *         operations to perform) to execute. Null if none (only this current
	 *         command event is requested to be treated).
	 */
	public List<CommandEvent> successor() {
		return this.successor;
	}

	/**
	 * Get the outgoing parameters that the function to execute does not require to
	 * read the value, parameter value is of no importance. In fact the parameters
	 * marked are needed to output multiple values. Such parameters are needed to be
	 * pointers, references and structures. This set of parameters passed MUST be
	 * modified by the executed command.
	 * 
	 * @return A list of output parameters (e.g entity reference that should be
	 *         changed as ordered by this command to execute) that should be valued
	 *         by the executed command. Null when none predetermined are requested
	 *         to be delivered.
	 */
	public Map<String, String> outParameters() {
		return this.outParameters;
	}

	/**
	 * Get the reference parameters that the function to execute does not require to
	 * read the value, parameter value is of no importance. In fact the parameters
	 * marked are maybe modified to output multiple values. Such parameters are
	 * needed to be pointers, references and structures. This set of parameters
	 * passed MAY be modified by the executed command.
	 * 
	 * @return A list of output parameters (e.g entity reference that could be
	 *         changed as ordered by this command to execute) that could be valued
	 *         by the executed command. When existing, each parameter had been
	 *         initialized before being passed to the command executor. Null when
	 *         none predetermined are requested to be delivered.
	 */
	public Map<String, String> refParameters() {
		return this.refParameters;
	}

	@Override
	public Map<String, String> inParameters() {
		return this.inParameters;
	}
}
