package org.cybnity.application.asset_control.ui.system.backend.infrastructure.impl.keycloack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for JWT management
 */
public class JWTHelper {

	private CryptoHelper crypto;

	public JWTHelper() {
		crypto = new CryptoHelper();
	}

	/**
	 * Help for management of server public and private keys used for JWT cyphering
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
