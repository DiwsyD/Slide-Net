package app.constants;
/**.
 * DataBase Constant Queries.
 *
 * All queries that you gonna need you should write here as a
 * 'public static final String' field.
 *
 * Queries divided into blocks by
 * parts of application.
 *
 * */
public class ConstantQuery {

    /**.
     * All Tables
     * */
    public static final String ACCOUNT_TABLE = "account";
    public static final String SERVICE_TABLE = "service";
    public static final String TARIFF_TABLE = "tariff";
    public static final String ACCOUNT_SERVICE_TARIFF_TABLE = "account_service_tariff";

    /**.
     * Common values
     * */
    public static final String ID = "id";

    /**.
     * Account Table columns
     * */
    public static final String MAX_ACCOUNT_ID = "maxId";

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ROLE_ID = "role_id";

    public static final String FIRST_NAME = "fname";
    public static final String LAST_NAME = "lname";
    public static final String SECOND_NAME = "sname";

    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String IP_ADDRESS = "ip_address";
    public static final String BALANCE = "balance";

    public static final String ACCOUNT_STATUS = "account_status";

    /**.
     * Role table columns
     * */
    public static final String ROLE_NAME = "name";

    /**.
     * Service table columns
     * */
    public static final String SERVICE_NAME = "name";

    /**.
     * Tariff table columns
     * */
    public static final String TARIFF_NAME = "name";
    public static final String TARIFF_DESCRIPTION = "description";
    public static final String TARIFF_PRICE = "price";

    /**.
     * Account Services columns
     * */
    public static final String ACCOUNT_ID = "account_id";
    public static final String SERVICE_ID = "service_id";
    public static final String TARIFF_ID = "tariff_id";
    public static final String ACTIVATION_DATE = "activation_date";
    public static final String ENABLE_STATUS = "enable_status";
    public static final String NEXT_PAYMENT_DAY = "next_payment_day";
    public static final String PAYED = "payed";
    public static final String PAYMENT_AMOUNT = "payment_amount";

    /**.
     * Account Queries
     * */
    //Getters
    public static final String GET_LIMITED_ACCOUNTS = "SELECT * FROM account " +
            "ORDER BY id LIMIT ? OFFSET ?";

    public static final String GET_ACCOUNT_BY_ID =
            "SELECT * " +
                    "FROM account " +
                    "WHERE id = ?";

    public static final String GET_ACCOUNT_BY_LOGIN =
            "SELECT * " +
                    "FROM account " +
                    "WHERE login = ?";

    public static final String GET_ACCOUNT_COUNT = "SELECT COUNT(*) AS account FROM account";

    public static final String ADD_NEW_ACCOUNT = "INSERT INTO account " +
            "(role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ACCOUNT_DATA = "UPDATE account " +
            "SET role_id = ?, " +
            "login = ?, " +
            "password = ?, " +
            "fname = ?, " +
            "lname = ?, " +
            "sname = ?, " +
            "address = ?, " +
            "phone_number = ?, " +
            "ip_address = ?, " +
            "balance = ?, " +
            "account_status = ? " +
            "WHERE id = ?";
    public static final String GET_LAST_ACCOUNT_ID = "SELECT max(id) as maxId FROM account";

    /**.
     * Role Queries
     * */
    public static final String GET_ROLE_BY_ID = "SELECT * " +
            "FROM role " +
            "WHERE id = ?";

    public static final String GET_ROLE_BY_NAME = "SELECT * " +
            "FROM role " +
            "WHERE name = ?";

    /**.
     * Service Queries
     * */
    public static final String GET_ALL_SERVICES = "SELECT * FROM service ORDER BY id";

    public static final String GET_SERVICE_BY_ID = "SELECT * " +
            "FROM service " +
            "WHERE id = ?";

    public static final String GET_SERVICE_COUNT = "SELECT COUNT(*) AS service FROM service";


    /**.
     * Tariff Queries
     * */
    public static final String GET_SERVICE_TARIFF_COUNT = "SELECT COUNT(*) AS tariffs " +
            "FROM tariff " +
            "WHERE service_id = ?";
    public static final String GET_TARIFF_BY_ID= "SELECT * " +
            "FROM tariff " +
            "WHERE id = ?";
    public static final String GET_ALL_TARIFF_BY_SERVICEID= "SELECT * " +
            "FROM tariff " +
            "WHERE service_id = ?";

    public static final String GET_PART_TARIFFS_BY_SERVICEID = "SELECT * FROM tariff " +
            "WHERE service_id = ? " +
            "ORDER BY myTable myDesc LIMIT ? OFFSET ?";

    public static final String CHECK_EXIST_TARIFF = "SELECT id " +
            "FROM tariff " +
            "WHERE name = ?";

    public static final String ADD_NEW_TARIFF = "INSERT INTO tariff " +
            "(service_id, name, description, price) " +
            "VALUES (?, ?, ?, ?)";

    public static final String REMOVE_TARIFF = "DELETE FROM tariff " +
            "WHERE name = ?";

    public static final String EDIT_TARIFF = "UPDATE tariff SET " +
            "name = ?, " +
            "description = ?, " +
            "price = ? " +
            "WHERE id = ?";

    public static final String GET_TARIFF_COUNT = "SELECT COUNT(*) AS tariff FROM tariff";

    /**.
     * Account Services
     * */
    public static final String GET_ALL_ACCOUNT_SERVICES = "SELECT * FROM account_service_tariff " +
            "WHERE account_id = ?";

    public static final String GET_ACCOUNT_SERVICE = "SELECT * FROM account_service_tariff " +
            "WHERE account_id = ? AND service_id = ?";

    public static final String GET_ACTIVE_SERVICE_COUNT = "SELECT COUNT(*) AS account_service_tariff " +
            "FROM account_service_tariff " +
            "WHERE account_id = ?";

    public static final String ADD_SERVICE_ACCOUNT = "INSERT INTO account_service_tariff " +
            "(account_id, service_id, tariff_id, activation_date, enable_status, next_payment_day, payed, payment_amount) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SERVICE_ACCOUNT = "UPDATE account_service_tariff " +
            "SET tariff_id = ?, " +
            "activation_date = ?, " +
            "enable_status = ?, " +
            "next_payment_day = ?, " +
            "payment_amount = ?, " +
            "payed = ? " +
            "WHERE account_id = ? " +
            "AND service_id = ?";

    public static final String REMOVE_SERVICE_ACCOUNT = "DELETE FROM account_service_tariff " +
            "WHERE account_id = ? AND service_id = ?";
}















