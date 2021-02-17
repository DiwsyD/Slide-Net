package app.dao.DAOImpl;

import app.dao.CPool.ConnectionPool;

public abstract class AbstractDAOImpl {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
}
