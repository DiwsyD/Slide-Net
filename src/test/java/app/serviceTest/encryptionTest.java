package app.serviceTest;

import app.service.Encryption;
import com.mysql.cj.protocol.Message;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class encryptionTest {

    @Test
    public void encryptionTest() {
        String expected = "Some#Pass!";

        String actual = Encryption.encrypt(expected);

        assertNotEquals(expected, actual);
    }

}
