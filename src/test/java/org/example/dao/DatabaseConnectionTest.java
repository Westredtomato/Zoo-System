package org.example.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    public void testSingletonInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testConnection() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection conn = db.getConnection();
        assertNotNull(conn);
    }

    @Test
    public void testAnimalDaoBasic() throws Exception {
        AnimalDAO dao = new AnimalDAO();
        dao.countAnimals();
        dao.findAll();
    }

    @Test
    public void testEnclosureDaoBasic() throws Exception {
        EnclosureDAO dao = new EnclosureDAO();
        dao.countEnclosures();
        dao.findAll();
    }

    @Test
    public void testEmployeeDaoBasic() throws Exception {
        EmployeeDAO dao = new EmployeeDAO();
        dao.countEmployees();
        dao.findAll();
    }

    @Test
    public void testFeedDaoBasic() throws Exception {
        FeedDAO dao = new FeedDAO();
        dao.countFeeds();
        dao.findAll();
    }

    @Test
    public void testFeedingRecordDaoBasic() throws Exception {
        FeedingRecordDAO dao = new FeedingRecordDAO();
        dao.countRecords();
        dao.findAll();
    }

    @Test
    public void testUserAccountDaoBasic() throws Exception {
        UserAccountDAO dao = new UserAccountDAO();
        dao.countUsers();
        dao.findAll();
    }

    @Test
    public void testPermissionDaoBasic() throws Exception {
        PermissionDAO dao = new PermissionDAO();
        dao.countPermissions();
        dao.findAll();
    }

    @Test
    public void testRolePermissionDaoBasic() throws Exception {
        RolePermissionDAO dao = new RolePermissionDAO();
        dao.countRolePermissions();
        dao.findAll();
    }

    @Test
    public void testLoginLogDaoBasic() throws Exception {
        LoginLogDAO dao = new LoginLogDAO();
        dao.countLogs();
        dao.findAll();
    }

    @Test
    public void testOperationLogDaoBasic() throws Exception {
        OperationLogDAO dao = new OperationLogDAO();
        dao.countLogs();
        dao.findAll();
    }

    @Test
    public void testSystemConfigDaoBasic() throws Exception {
        SystemConfigDAO dao = new SystemConfigDAO();
        dao.countConfigs();
        dao.findAll();
    }
}