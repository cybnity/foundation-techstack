package org.cybnity.application.asset_control.ui.system.backend.routing;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;

import org.cybnity.application.asset_control.ui.system.backend.infrastructure.impl.keycloack.JWTHelper;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.authorization.Authorizations;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.authorization.KeycloakAuthorization;
import io.vertx.ext.auth.oauth2.providers.KeycloakAuth;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of UI events and interactions regarding one boundary of cockpit
 * capabilities including Access Control Layer (ACL) ensuring the check of RBAC
 * and permissions according to the requester.
 */
public class SecuredUICapabilityContextBoundaryHandler extends UICapabilityContextBoundaryHandler {

	private JWTHelper jwtHelper;
	private OAuth2Auth oauth2Client;
	private AuthorizationProvider authProvider;

	public SecuredUICapabilityContextBoundaryHandler(EventBus eventBus, SharedData sessionStore,
			String cqrsResponseChannel, Vertx vertx) {
		super(eventBus, sessionStore, cqrsResponseChannel, vertx);
		jwtHelper = new JWTHelper();
		JsonObject keycloakConfig = getKeycloakJson();
		authProvider = KeycloakAuthorization.create();
		// Integrate OAuth2 sso checking with Keycloak server about user's JWT Token
		oauth2Client = KeycloakAuth.create(vertx, keycloakConfig);
	}

	/**
	 * Redefined implementation which check the ACL conditions relative to the
	 * requester of the event treatment request to control.
	 */
	@Override
	protected void authorizedResourceAccess(BridgeEvent event, Handler<AsyncResult<Boolean>> callback)
			throws SecurityException {
		if (callback == null)
			throw new SecurityException("Callback parameter must be defined!");
		// Access token is previously and automatically controlled by the
		// KeycloakAuthorization provider plugged on the event bus
		try {
			if (event != null) {
				JsonObject message = event.getRawMessage();
				if (message != null) {
					JsonObject body = message.getJsonObject("body", null);

					// Read the event authentication credential required by the ACL process
					JsonObject authenticationCredential = body.getJsonObject("authenticationCredential", null);
					if (authenticationCredential != null) {
						String accessType = authenticationCredential.getString("accessType", null);
						JsonObject attributes = authenticationCredential.getJsonObject("attributes", null);
						String accessTokenRaw = attributes.getString("accessToken", null);
						/*
						 * System.out.println( "Encoded Token:\naccessType: " + accessType +
						 * "\naccessTokenRaw: " + accessTokenRaw);
						 */

						// Create a JWT provider based on private/public keys of the application
						// JWTAuth jwtAuthProvider = jwtHelper.create(this.context(), "RS256");
						// JWTAuth authProvider = JWTAuth.create(vertx, getKeycloakJson());

						JsonObject tokenCredentials = new JsonObject()
								.put("access_token", /* Base64 encoded token */ accessTokenRaw)
								.put("options", new JsonObject().put("ignoreExpiration", false));

						// Read the token credential about user identification
						// The provider is checking:
						// - token signature is valid against internal private key
						// - fields exp, iat, nbf, audience, issuer are valid according to the config
						oauth2Client.authenticate(tokenCredentials).onSuccess(user -> {
							// All checked values are considered good and read user information is returned
							System.out.println("User: " + user.principal());

							// User level authentication
							oauth2Client.authenticate(user.principal()).onSuccess(authenticatedUser -> {
								// Token is valid
							});

							authProvider.getAuthorizations(user).onSuccess(result -> {
								// Read the authorizations
								Authorizations userAuth = user.authorizations();
							});

							// Check the user role and permission regarding the accessed feature via the
							// event message
							callback.handle(new ResourceAccessAuthorizationResult<Boolean>(Boolean.TRUE, null));
						}).onFailure(err -> {
							System.out.println("Authentication error: " + err);
							callback.handle(new ResourceAccessAuthorizationResult<Boolean>(Boolean.FALSE, err));
						});
					}
				}
			}
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * Read the Keycloak client options from resource file exported of Keycloak
	 * Admin UI.
	 * 
	 * @return
	 * @throws MissingResourceException When missing keycloak.json file in resources
	 *                                  folder (classpath embedded).
	 */
	private JsonObject getKeycloakJson() throws MissingResourceException {
		String keycloakOIDCJsonFileName = "keycloak.json";
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(keycloakOIDCJsonFileName)) {
			if (input == null) {
				throw new MissingResourceException("Missing " + keycloakOIDCJsonFileName + " file in classpath",
						this.getClass().getName(), keycloakOIDCJsonFileName);
			}
			// Load the configuration file
			BufferedInputStream bis = new BufferedInputStream(input);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			for (int result = bis.read(); result != -1; result = bis.read()) {
				buf.write((byte) result);
			}
			// StandardCharsets.UTF_8.name() > JDK 7
			return new JsonObject(buf.toString("UTF-8"));
		} catch (IOException ex) {
			throw new MissingResourceException(ex.getMessage(), this.getClass().getName(), keycloakOIDCJsonFileName);
		}
	}
}
