package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.authorization.KeycloakAuthorization;
import io.vertx.ext.auth.oauth2.providers.KeycloakAuth;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

/**
 * Handler of UI events and interactions regarding one boundary of cockpit
 * capabilities including Access Control Layer (ACL) ensuring the check of RBAC
 * and permissions according to the requester.
 */
public class SecuredUICapabilityContextBoundaryHandler extends UICapabilityContextBoundaryHandler {

	public SecuredUICapabilityContextBoundaryHandler(EventBus eventBus, SharedData sessionStore,
			String cqrsResponseChannel, Vertx vertx) {
		super(eventBus, sessionStore, cqrsResponseChannel, vertx);
	}

	/**
	 * Redefined implementation which check the ACL conditions relative to the
	 * requester of the event treatment request to control.
	 */
	@Override
	protected boolean authorizedResourceAccess(BridgeEvent event) throws SecurityException {
		// Access token is previously and automatically controlled by the
		// KeycloakAuthorization provider plugged on the event bus
		try {
			boolean authorizedProcessing = false;
			if (event != null) {
				JsonObject message = event.getRawMessage();
				if (message != null) {
					JsonObject body = message.getJsonObject("body", null);
					AuthorizationProvider authProvider = KeycloakAuthorization.create();
					// Read the event authentication credential required by the ACL process
					JsonObject authenticationCredential = body.getJsonObject("authenticationCredential", null);
					if (authenticationCredential != null) {
						String accessType = authenticationCredential.getString("accessType", null);
						JsonObject attributes = authenticationCredential.getJsonObject("attributes", null);
						String accessTokenRaw = attributes.getString("accessToken", null);

						// TODO VALIDER QUE LE TOKEN EST VALID ET EN EXTRAIRE LES INFOS DE L'UTILISATEUR
						// POUR EN LIRE SES AUTHORISATIONS/ROLES ETC...

						String realm = "cybnity";

						// Handling authentication in the application (common handler for auth handler
						// that use the `Authorization` HTTP header)
						// JWTAuthHandler jwtAuthHandler =
						// JWTAuthHandler.create(keycloackTokenAuthProvider, realm);

						// Integrate OAuth2 sso checking with Keycloak server about user's JWT Token
						// See https://vertx.io/docs/vertx-auth-oauth2/java/
						// Initialize the OAuth2 library
						OAuth2Auth oauth2 = KeycloakAuth.create(this.context(), OAuth2FlowType.AUTH_JWT,
								getKeycloakJson());
						System.out.println("token treatment to implement");
						// JWTAuth authProvider = JWTAuth.create(vertx, getKeycloakJson());

						/**
						 * oauth2.authenticate( new JsonObject().put("access_token", "<<jwt received
						 * from client side as valid access token>>")) .onSuccess(user -> { // Token is
						 * valid // Tokens are usually fetched from the server and cached, in this case
						 * when used // later they might have already expired and be invalid boolean
						 * isExpired = user.expired(); if (!isExpired) { // Authorized access token is
						 * valid // user name from the JWT token String subject =
						 * user.principal().getString("sub", null);
						 * 
						 * // Check specific authorities according the features authorized to the user
						 * in a // Role Based Access Control // See
						 * https://vertx.io/docs/vertx-auth-oauth2/java/#_keycloak_jwt documentation
						 * 
						 * // TODO voir si plutôt dans le UI capability handler qui lit l'event et peux
						 * // checker si la feature demandée est autorisée pour cet utilisateur
						 * demandeur
						 * 
						 * } else { // Expired token requiring refresh from user // Notify event in
						 * Event bus allowing to inform the user about the impossible // request
						 * treatment caused by expired access token // TODO envoyer un eventbus event
						 * pour l'informer du rejet de son token expiré } }).onFailure(error -> { //
						 * Invalid token // Notify event in Event bus allowing to inform the user about
						 * the impossible // request treatment caused by invalid token // TODO envoyer
						 * un eventbu s event pour l'informer du rejet de son token });
						 **/

						authorizedProcessing = true;
					}
				}
			}

			return authorizedProcessing;
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * Read the Keycloak client options from resource file exported of Keycloak
	 * Admin UI
	 * 
	 * @return
	 */
	private JsonObject getKeycloakJson() {
		// TODO replace with read of resource file exported from Keycloak client
		// instance en utilisant l'adapter sso d'infrastructure
		// qui lit le fichier de resource keylclok.json comme impl retenue

		return new JsonObject("{\n" + "  \"realm\": \"cybnity\",\n" + "  \"public-client\": true,\n"
				+ "  \"auth-server-url\": \"http://localhost:8082/\",\n" + "  \"ssl-required\": \"external\",\n"
				+ "  \"resource\": \"cybnity-frontend-ui\",\n" + "  \"confidential-port\": 0\n" + "}");
	}
}
