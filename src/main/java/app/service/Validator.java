package app.service;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Logger LOG = Logger.getLogger(Validator.class);

    private static Integer maxLogin = 12;
    private static Integer minLogin = 4;

    private static Integer maxPass = 16;
    private static Integer minPass = 4;

    public static long validateLogin(String login) {
        if (login.length() > maxLogin || login.length() < minLogin) {
            return -1;
        }
        long result = -1;
        try {
            result = Integer.parseInt(login);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
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
