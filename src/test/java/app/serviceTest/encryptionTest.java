package app.serviceTest;

import app.service.Encryption;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class encryptionTest {


    @Test
    public void encryptionTest() {
        String expected = "Some#Pass!";

        String actual = Encryption.encrypt(expected);

        assertNotEquals(expected, actual);
    }

}
