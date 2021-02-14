package app.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static Integer maxLogin = 12;
    private static Integer minLogin = 4;

    private static Integer maxPass = 16;
    private static Integer minPass = 4;

    public static int validateLogin(String login) {
        if (login.length() > maxLogin && login.length() < minLogin) {
            return -1;
        }
        int result = -1;
        try {
            result = Integer.parseInt(login);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } finally {
            return result;
        }
    }

    public static boolean validatePassword(String password) {
        if (password.length() > maxPass && password.length() < minPass) {
            return false;
        }
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*(?:[a-zA-Z0-9-\\.]){2,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
