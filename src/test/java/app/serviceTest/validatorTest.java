package app.serviceTest;

import app.entity.Account;
import app.service.Encryption;
import app.service.Validator;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class validatorTest {

    @Test
    public void loginValidationTest() {
        long expectedInvalid = -1;
        long expectedValid = 85465203;

        long result1 = Validator.validateLogin("SimpleLogin");
        long result2 = Validator.validateLogin("85465203");
        long result3 = Validator.validateLogin("1234568791010");
        long result4 = Validator.validateLogin("1");

        assertNotEquals(result1, result2);

        assertEquals(expectedInvalid, result1);
        assertEquals(expectedValid, result2);
        assertEquals(expectedInvalid, result3);
        assertEquals(expectedInvalid, result4);
    }

    @Test
    public void passwordValidationTest() {

        boolean result1 = Validator.validatePassword("samplePass");
        boolean result2 = Validator.validatePassword("1Correct@PasswordButTooLong");
        boolean result3 = Validator.validatePassword("Hell@worl9");
        boolean result4 = Validator.validatePassword("sho");

        assertFalse(result1);
        assertFalse(result2);
        assertTrue(result3);
        assertFalse(result4);
    }

    @Test
    public void validateAccountWithIncorrectDataTest() {
        Account account = mock(Account.class);

        String login = "123456789";
        String password = "incorrectp";

        when(account.getPassword()).thenReturn(password);

        boolean result = Validator.validateAccount(account, login, password);

        assertFalse(result);
    }

}


















