package app.dao;

import app.entity.Role;

public interface RoleDAO {

    Role getRoleById(long id);

    public Role getRoleByName(String name);
}

