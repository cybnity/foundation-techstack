package org.cybnity.infrastructure.common.event;

import java.util.Calendar;
import java.util.Map;

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
public class QueryEvent extends CommonEvent implements InputParameterProvider {

	public Map<String, String> inParameters;

	public QueryEvent(Calendar local) {
		super(local);
	}

	public QueryEvent() {
		super();
	}

	@Override
	public Map<String, String> inParameters() {
		return this.inParameters;
	}

}
