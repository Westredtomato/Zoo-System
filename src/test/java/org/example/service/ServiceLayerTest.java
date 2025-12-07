package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceLayerTest {

    @Test
    public void testUserAuthAndLocking() throws SQLException {
        UserService userService = new UserService();
        // 创建临时用户
        UserAccount u = new UserAccount();
        u.setUserId("tuser1");
        u.setUsername("tuser1");
        u.setPasswordHash("secret");
        u.setActive(true);
        try { userService.deleteUser("tuser1"); } catch (Exception ignored) {}
        userService.createUser(u);

        // 错误密码尝试 3 次后锁定
        boolean ok1 = userService.authenticate("tuser1", "bad", "127.0.0.1", "ua");
        assertFalse(ok1);
        boolean ok2 = userService.authenticate("tuser1", "bad", "127.0.0.1", "ua");
        assertFalse(ok2);
        boolean ok3 = userService.authenticate("tuser1", "bad", "127.0.0.1", "ua");
        assertFalse(ok3);

        UserAccount after = userService.getUserById("tuser1");
        assertTrue(after.isLocked() || after.getFailedAttempts() >= 3);

        // 解锁
        userService.unlockUser("tuser1");
        UserAccount after2 = userService.getUserById("tuser1");
        assertFalse(after2.isLocked());

        userService.deleteUser("tuser1");
    }

    @Test
    public void testPermissionAssignAndAuth() throws SQLException {
        PermissionService permissionService = new PermissionService();
        AuthService authService = new AuthService();
        Permission p = new Permission();
        p.setPermissionId("P_TEST");
        p.setPermissionCode("TEST_ACCESS");
        p.setPermissionName("Test Access");
        // use a valid module from schema: 'feeding'
        p.setModule("feeding");
        try { permissionService.deletePermission("P_TEST"); } catch (Exception ignored) {}
        permissionService.createPermission(p);

        // ensure 'sys' user exists for granted_by
        UserService userService = new UserService();
        UserAccount sysUser = new UserAccount();
        sysUser.setUserId("sys");
        sysUser.setUsername("sys");
        sysUser.setPasswordHash("sys");
        sysUser.setRole("super_admin");
        try { userService.deleteUser("sys"); } catch (Exception ignored) {}
        userService.createUser(sysUser);

        // 分配给 role
        permissionService.assignPermissionToRole("staff", "P_TEST", "sys");

        // 创建用户并设置 role
        UserAccount u = new UserAccount();
        u.setUserId("ptestuser");
        u.setUsername("ptestuser");
        u.setPasswordHash("pwd");
        u.setRole("staff");
        try { userService.deleteUser("ptestuser"); } catch (Exception ignored) {}
        userService.createUser(u);

        boolean has = authService.userHasPermission("ptestuser", "TEST_ACCESS");
        assertTrue(has);

        // cleanup
        permissionService.revokePermissionFromRole("staff", "P_TEST");
        permissionService.deletePermission("P_TEST");
        userService.deleteUser("ptestuser");
        userService.deleteUser("sys");
    }

    @Test
    public void testLogAndConfig() throws SQLException {
        LogService logService = new LogService();
        SystemConfigService cfgService = new SystemConfigService();
        UserService userService = new UserService();

        // ensure users exist for logs
        UserAccount nolog = new UserAccount();
        nolog.setUserId("nologuser");
        nolog.setUsername("nologuser");
        nolog.setPasswordHash("nopwd");
        try { userService.deleteUser("nologuser"); } catch (Exception ignored) {}
        userService.createUser(nolog);

        UserAccount sysUser = new UserAccount();
        sysUser.setUserId("sys");
        sysUser.setUsername("sys");
        sysUser.setPasswordHash("sys");
        try { userService.deleteUser("sys"); } catch (Exception ignored) {}
        userService.createUser(sysUser);

        // 写登录日志：使用 existing user
        LoginLog log = new LoginLog();
        log.setUserId("nologuser");
        log.setLoginTime(LocalDateTime.now());
        log.setIpAddress("127.0.0.1");
        log.setLoginStatus("failed");
        logService.logLogin(log);

        // 写操作日志
        OperationLog op = new OperationLog();
        op.setUserId("sys");
        op.setOperationTime(LocalDateTime.now());
        op.setModule("feeding");
        op.setOperationType("CREATE");
        op.setTableName("test_table");
        op.setDescription("unit test op");
        logService.logOperation(op);

        // 配置读写
        SystemConfig cfg = new SystemConfig();
        cfg.setConfigKey("test.key");
        cfg.setConfigValue("v1");
        cfg.setConfigType("string");
        cfg.setPublicConfig(true);
        cfg.setUpdatedBy("sys");
        cfgService.setConfig(cfg);

        SystemConfig loaded = cfgService.getConfig("test.key");
        assertNotNull(loaded);
        assertEquals("v1", loaded.getConfigValue());

        cfgService.deleteConfig("test.key");

        // cleanup users
        userService.deleteUser("nologuser");
        userService.deleteUser("sys");
    }
}
