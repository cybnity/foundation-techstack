/**
 * This module provides routing service elements like Vert.x Router objects which maintains zero or more Routes, and Handler(s).
 * <p>
 * A router will pause the incoming HttpServerRequest to ensure that the request body or any protocol upgrades are not lost.
 * Second, it will find the first matching route for that request, and passes the request to that route.
 * The route can have a handler associated with it, which then receives the request.
 * You then do something with the request, and then, either end it or pass it to the next matching handler.
 * Read https://vertx.io/docs/vertx-web/java/#_basic_vert_x_web_concepts for more details on Vert.x implementation of routing capabilities.
 * </p>
 *
 * @since 1.0
 * @author olivier_lemee
 * @version 1.0
 */
package org.cybnity.application.asset_control.ui.system.backend.routing;