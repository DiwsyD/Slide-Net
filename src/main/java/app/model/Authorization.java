package app.model;

import app.entity.Account;

public class Authorization {

    /**.
     * Get User object with data from DB and
     * if its not null, add it to pool of users and return true - that means
     * user is valid, received data is valid.
     * */
    public static boolean validateUser(String login, String password) {
        int loginInt = Validator.validateLogin(login.trim());
        if (loginInt == -1) {
            return false;
        }
        //Find User and add it to UsersPool
        Account account = AccountDataManager.findAccountByLoginOrNull(loginInt);

        boolean isEqualPassword = false;
        if (account != null && Validator.validatePassword(password.trim())) {
            isEqualPassword = account.getPassword().equals(Encryption.encrypt(password.trim()));
        }
        return isEqualPassword;
    }


}
