package movieappspring.security;

import movieappspring.PropertiesManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Class with methods for creating hash password with salt
 */
public class PasswordManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Integer PASSWORD_LENGTH;
    private PropertiesManager propertiesManager;

    @Autowired
    public PasswordManager(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
        try {
            PASSWORD_LENGTH = Integer.parseInt(propertiesManager.getProperty("password.minLength"));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e, e);
            throw new RuntimeException("Can't parse property value. " + e, e);
        }
    }

    /**
     * Encodes password by hashing with salt
     *
     * @param password password to encode
     * @return hashed password with salt
     * @throws IllegalArgumentException
     */
    public String getSaltedHashPassword(String password) throws IllegalArgumentException {
        byte[] salt = generateSalt(password);
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /**
     * Hashes given password with salt
     *
     * @param password password to hash
     * @param salt     password salt
     * @return string representing password in hashed form
     * @throws IllegalArgumentException
     */
    private String hash(String password, byte[] salt) throws IllegalArgumentException {
        SecretKeyFactory secretKeyFactory;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(propertiesManager.getProperty("password.secretKeyFactory"));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.fatal("No specified algorithm found. Error: " + e, e);
            throw new RuntimeException("No specified algorithm found. Error: " + e, e);
        }

        PBEKeySpec pbeKeySpec;
        try {
            pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt,
                    Integer.parseInt(propertiesManager.getProperty("password.hashIterations")),
                    Integer.parseInt(propertiesManager.getProperty("password.keyLength")));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e, e);
            throw new RuntimeException("Can't parse property value. " + e, e);
        }

        SecretKey secretKey;
        try {
            secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        } catch (InvalidKeySpecException e) {
            LOGGER.fatal("Can't find matching key spec. Error: " + e, e);
            throw new RuntimeException("Can't find matching key spec. Error: " + e, e);
        }

        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    /**
     * Generates salt with given password
     *
     * @param password password to generate salt
     * @return byte array of password with salt
     */
    private byte[] generateSalt(String password) {
        if (password == null || password.trim().equals("") || password.length() < PASSWORD_LENGTH) {
            LOGGER.error("Wrong password: " + password);
            throw new IllegalArgumentException("Password should not be empty and must have at least " + PASSWORD_LENGTH + " characters.");
        }
        byte[] bytes = String.valueOf(password.length() * 42 + 3).getBytes();
        byte[] bytes_ = password.getBytes();
        byte[] combined = new byte[bytes.length + bytes_.length];
        System.arraycopy(bytes, 0, combined, 0, bytes.length);
        System.arraycopy(bytes_, 0, combined, bytes.length, bytes_.length);
        return combined;
    }


}
