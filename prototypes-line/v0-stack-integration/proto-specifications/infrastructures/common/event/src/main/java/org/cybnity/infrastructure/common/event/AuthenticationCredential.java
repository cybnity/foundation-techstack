package org.cybnity.infrastructure.common.event;

import java.util.Map;

public class AuthenticationCredential {

	/**
	 * E.g Bearer type.
	 */
	public String accessType;
	
	/**
	 * List of attributes/properties defining the credential.
	 */
	public Map<String, String> attributes;

	public AuthenticationCredential() {
	}

	public Map<String, String> attributes() {
		return this.attributes;
	}
}
