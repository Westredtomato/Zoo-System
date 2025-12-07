package org.example.controller;

import org.example.model.UserAccount;
import org.example.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for User management.
 * Example mappings:
 * - POST /api/users (create)
 * - GET /api/users/{id}
 * - GET /api/users
 * - POST /api/users/auth (authenticate)
 */
public class UserController {
    private final UserService service = new UserService();

    public void create(UserAccount u) throws SQLException { service.createUser(u); }
    public UserAccount getById(String id) throws SQLException { return service.getUserById(id); }
    public UserAccount getByUsername(String username) throws SQLException { return service.getUserByUsername(username); }
    public void update(UserAccount u) throws SQLException { service.updateUser(u); }
    public void delete(String id) throws SQLException { service.deleteUser(id); }
    public boolean authenticate(String username, String password, String ip, String ua) throws SQLException { return service.authenticate(username,password,ip,ua); }
}

