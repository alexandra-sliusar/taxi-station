package ua.taxistation.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.exceptions.ServerAppException;

public class PasswordHashing {
	private static final Logger LOGGER = LogManager.getLogger(PasswordHashing.class);

	private static final String RANDOM_SALT_ALGORYTHM = "SHA1PRNG";

	public static String generateHash(String password, byte[] salt) {
		return DigestUtils.sha256Hex(DigestUtils.sha256Hex(password) + DigestUtils.sha256Hex(salt));
	}

	public static byte[] generateSalt() {
		try {
			Random sr = SecureRandom.getInstance(RANDOM_SALT_ALGORYTHM);
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SecureRandom salt generation failed", e);
			throw new ServerAppException(e);
		}
	}
}
