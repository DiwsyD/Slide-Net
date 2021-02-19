package app.serviceTest;

import app.service.Validator;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class validatorTest {

    private static final OutputStream output = new ByteArrayOutputStream();
    public static final PrintStream STD_OUT = System.out;
    public static final PrintStream STD_ERR = System.out;

    @Test
    public void loginValidationTest() {
        long expectedInvalid = -1;
        long expectedValid = 85465203;

        long result1;
        long result2;
        long result3;

        result1 = Validator.validateLogin("SimpleLogin");
        result2 = Validator.validateLogin("85465203");
        result3 = Validator.validateLogin("123456879101");

        assertNotEquals(result1, result2);

        assertEquals(expectedInvalid, result1);
        assertEquals(expectedValid, result2);
        assertEquals(expectedInvalid, result3);
    }

    public void passwordValidationTest() {

    }

}
