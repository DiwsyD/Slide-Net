package app.service;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Logger LOG = Logger.getLogger(Validator.class);

    private static final Integer maxLogin = 12;
    private static final Integer minLogin = 4;

    private static final Integer maxPass = 16;
    private static final Integer minPass = 4;

    public static long validateLogin(String login) {
        long result = -1;
        if (login.length() > maxLogin || login.length() < minLogin) {
            return result;
        }
        try {
            result = Integer.parseInt(login);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid Login format. Access denied!");
        } finally {
            return result;
        }
    }

    public static boolean validatePassword(String password) {
        if (password.length() > maxPass || password.length() < minPass) {
            return false;
        }
        String regex = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{" + minPass + "," + maxPass + "}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
