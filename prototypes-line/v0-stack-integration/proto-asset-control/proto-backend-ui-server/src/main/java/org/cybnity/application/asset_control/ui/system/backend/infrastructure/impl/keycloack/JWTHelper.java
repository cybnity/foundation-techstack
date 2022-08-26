package org.cybnity.application.asset_control.ui.system.backend.infrastructure.impl.keycloack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

/**
 * Utility class for JWT management
 */
public class JWTHelper {

	private CryptoHelper crypto;

	public JWTHelper() {
		crypto = new CryptoHelper();
	}

	/**
	 * Create a JWTAuth instance based on private and public keys stored in the
	 * service folder or application project root.
	 * 
	 * @param vertx     Mandatory context.
	 * @param algorithm Mandatory algorithm (e.g ES256, RS256, HS256) used for
	 *                  signing of the JWT token.
	 * @return Instance of authentication provider which can manage authentication
	 *         of token.
	 * @throws IllegalArgumentException When problem during instantiation (e.g null
	 *                                  parameter).
	 * @throws IOException              When inaccessible public or private key file
	 *                                  from project folder.
	 */
	public JWTAuth create(Vertx vertx, String algorithm) throws IllegalArgumentException, IOException {
		if (vertx == null || algorithm == null || "".equals(algorithm))
			throw new IllegalArgumentException("Missing parameter!");

		return JWTAuth.create(vertx,
				new JWTAuthOptions()
						.addPubSecKey(new PubSecKeyOptions().setAlgorithm(algorithm).setBuffer(crypto.publicKey()))
						.addPubSecKey(new PubSecKeyOptions().setAlgorithm(algorithm).setBuffer(crypto.privateKey())));
	}

	/**
	 * Help for management of server public and private keys used for JWT signing
	 * during event bus communications.
	 */
	class CryptoHelper {
		public String publicKey() throws IOException {
			return read("public_key.pem");
		}

		public String privateKey() throws IOException {
			return read("private_key.pem");
		}

		private String read(String file) throws IOException {
			Path path = Paths.get("public-api", file);
			if (!path.toFile().exists()) {
				path = Paths.get("..", "public-api", file);
			}
			return String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8));
		}
	}

}
