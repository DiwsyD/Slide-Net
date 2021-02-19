package app.service;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Encryption {
    private static final Logger LOG = Logger.getLogger(Encryption.class);

    public static String encrypt(String line) {
        StringBuilder result = new StringBuilder(line.toLowerCase(Locale.ROOT));
        result.append(result.reverse().toString().toLowerCase(Locale.ROOT));
        byte[] bytes = null;
        try {
            MessageDigest msdt = MessageDigest.getInstance("MD5");
            msdt.update(result.toString().getBytes(StandardCharsets.UTF_8));
            bytes = msdt.digest();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Encryption method MD5 was not found!");
        }
        BigInteger bigint = new BigInteger(1, bytes);
        return bigint.toString(16).toLowerCase(Locale.ROOT);
    }
}
