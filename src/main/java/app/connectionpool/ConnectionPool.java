package app.connectionpool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static ConnectionPool connectionPool = new ConnectionPool();

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        if(connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        Context ctx;
        Connection con = null;
        try {
            ctx = new InitialContext();
            DataSource dts = (DataSource) ctx.lookup("java:comp/env/jdbc/slidenetdb");
            con = dts.getConnection();
        } catch (NamingException | SQLException e) {
            System.out.println(e.getMessage()); //Use Logger
        } finally {
            return con;
        }
    }

}
