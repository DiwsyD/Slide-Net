package app.connectionpool;

import app.service.language;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool = new ConnectionPool();

    private ConnectionPool() {}

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
            LOG.error("Can't get Connection! Something wrong with connection to DB (properties).");
        }
        return con;
    }

}
