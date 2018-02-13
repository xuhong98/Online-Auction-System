package util.helperClass;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author yingshi
 */
public class SecurityHelper {

    private final String DEFAULT_SECURE_RANDOM_ALGORITHM_NAME = "SHA1PRNG";

    public String generatePassword(String userInput) {
        String salt = generateRandomString(64);
        String hashedPassword = generateHashing(userInput, salt);
        return salt + hashedPassword;
    }

    private String generateHashing(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }
        return generatedPassword;
    }

    public Boolean verifyPassword(String userInput, String databaseString) {
        String recoverSalt = databaseString.substring(0, 64);
        String hashedPassword = databaseString.substring(64, 128);

        String thisHash = generateHashing(userInput, recoverSalt);

        return thisHash.equals(hashedPassword);
    }

    private String generateRandomString(int length) {
        String password = "";

        try {
            SecureRandom wheel = SecureRandom.getInstance(DEFAULT_SECURE_RANDOM_ALGORITHM_NAME);

            char[] alphaNumberic = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

            for (int i = 0; i < length; i++) {
                int random = wheel.nextInt(alphaNumberic.length);
                password += alphaNumberic[random];
            }

            return password;
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("********** Exception: " + ex);
            return null;
        }
    }
}
