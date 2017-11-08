package de.altenerding.biber.pinkie.business.login.control;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class SecurityProvider {

	private static final int KEY_LENGTH = 256;
	private static final int ITERATIONS = 500;

	public String hashPassword(final char[] password, final byte[] salt) {

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
			SecretKey key = skf.generateSecret(spec);
			byte[] hashedPassword = key.getEncoded();
			return Base64.getEncoder().encodeToString(hashedPassword);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}
