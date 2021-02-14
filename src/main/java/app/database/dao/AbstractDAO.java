package app.database.dao;

import app.database.conf.ConnectionPool;

public abstract class AbstractDAO {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
}
