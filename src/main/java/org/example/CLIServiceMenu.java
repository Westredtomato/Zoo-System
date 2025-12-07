package org.example;

import org.example.model.*;
import org.example.service.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CLIServiceMenu {

    private final AnimalService animalService = new AnimalService();
    private final EnclosureService enclosureService = new EnclosureService();
    private final FeedService feedService = new FeedService();
    private final FeedingService feedingService = new FeedingService();
    private final UserService userService = new UserService();
    private final PermissionService permissionService = new PermissionService();
    private final AuthService authService = new AuthService();
    private final LogService logService = new LogService();
    private final SystemConfigService configService = new SystemConfigService();

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> manageAnimals();
                    case "2" -> manageEnclosures();
                    case "3" -> manageFeeds();
                    case "4" -> manageFeedings();
                    case "5" -> manageUsers();
                    case "6" -> managePermissions();
                    case "7" -> manageLogs();
                    case "8" -> manageConfigs();
                    case "0" -> exit = true;
                    default -> System.out.println("未知选项，请重试。");
                }
            } catch (Exception e) {
                System.out.println("发生错误: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        System.out.println("退出程序。\n");
    }

    private void printMainMenu() {
        System.out.println("=== 动物园管理系统 CLI 菜单 ===");
        System.out.println("1. 动物管理");
        System.out.println("2. 展区管理");
        System.out.println("3. 饲料管理");
        System.out.println("4. 投喂管理");
        System.out.println("5. 用户管理 / 登录测试");
        System.out.println("6. 权限分配测试");
        System.out.println("7. 日志测试");
        System.out.println("8. 系统配置");
        System.out.println("0. 退出");
        System.out.print("请输入选项: ");
    }

    private void manageAnimals() throws SQLException {
        System.out.println("-- 动物管理 --");
        System.out.println("a. 列出所有动物");
        System.out.println("b. 添加动物");
        System.out.println("c. 删除动物");
        System.out.println("d. 转移动物");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                List<Animal> list = animalService.listAllAnimals();
                list.forEach(System.out::println);
            }
            case "b" -> {
                Animal a = new Animal();
                System.out.print("animalId: "); a.setAnimalId(scanner.nextLine().trim());
                System.out.print("name: "); a.setName(scanner.nextLine().trim());
                System.out.print("species: "); a.setSpecies(scanner.nextLine().trim());
                System.out.print("gender (M/F): "); a.setGender(scanner.nextLine().trim());
                System.out.print("birthDate (yyyy-MM-dd) 或回车: ");
                String bd = scanner.nextLine().trim();
                if (!bd.isEmpty()) a.setBirthDate(LocalDate.parse(bd));
                System.out.print("enclosureId: "); a.setEnclosureId(scanner.nextLine().trim());
                animalService.createAnimal(a);
                System.out.println("已添加动物。");
            }
            case "c" -> {
                System.out.print("animalId: ");
                String id = scanner.nextLine().trim();
                animalService.deleteAnimal(id);
                System.out.println("已删除（若存在）。");
            }
            case "d" -> {
                System.out.print("animalId: "); String id = scanner.nextLine().trim();
                System.out.print("targetEnclosureId: "); String tid = scanner.nextLine().trim();
                animalService.transferAnimal(id, tid);
                System.out.println("转移完成（如无异常）。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageEnclosures() throws SQLException {
        System.out.println("-- 展区管理 --");
        System.out.println("a. 列出所有展区");
        System.out.println("b. 添加展区");
        System.out.println("c. 删除展区");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                List<Enclosure> list = enclosureService.listAllEnclosures();
                list.forEach(System.out::println);
            }
            case "b" -> {
                Enclosure e = new Enclosure();
                System.out.print("enclosureId: "); e.setEnclosureId(scanner.nextLine().trim());
                System.out.print("name: "); e.setName(scanner.nextLine().trim());
                System.out.print("capacity: "); e.setCapacity(Integer.parseInt(scanner.nextLine().trim()));
                enclosureService.createEnclosure(e);
                System.out.println("已创建展区。");
            }
            case "c" -> {
                System.out.print("enclosureId: "); String id = scanner.nextLine().trim();
                enclosureService.deleteEnclosure(id);
                System.out.println("删除完成（如无异常）。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageFeeds() throws SQLException {
        System.out.println("-- 饲料管理 --");
        System.out.println("a. 列出所有饲料");
        System.out.println("b. 添加饲料");
        System.out.println("c. 增加库存");
        System.out.println("d. 减少库存");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                List<Feed> list = feedService.listAllFeeds();
                list.forEach(System.out::println);
            }
            case "b" -> {
                Feed f = new Feed();
                System.out.print("feedId: "); f.setFeedId(scanner.nextLine().trim());
                System.out.print("name: "); f.setName(scanner.nextLine().trim());
                System.out.print("unit: "); f.setUnit(scanner.nextLine().trim());
                System.out.print("stockQuantity (数字): "); f.setStockQuantity(new BigDecimal(scanner.nextLine().trim()));
                feedService.createFeed(f);
                System.out.println("饲料已添加。");
            }
            case "c" -> {
                System.out.print("feedId: "); String id = scanner.nextLine().trim();
                System.out.print("qty: "); BigDecimal q = new BigDecimal(scanner.nextLine().trim());
                feedService.increaseStock(id, q);
                System.out.println("库存已增加。");
            }
            case "d" -> {
                System.out.print("feedId: "); String id = scanner.nextLine().trim();
                System.out.print("qty: "); BigDecimal q = new BigDecimal(scanner.nextLine().trim());
                feedService.decreaseStock(id, q);
                System.out.println("库存已减少。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageFeedings() throws SQLException {
        System.out.println("-- 投喂管理 --");
        System.out.println("a. 列出所有投喂记录");
        System.out.println("b. 添加投喂记录");
        System.out.println("c. 审批记录");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                List<FeedingRecord> list = feedingService.listAllFeedingRecords();
                list.forEach(System.out::println);
            }
            case "b" -> {
                FeedingRecord r = new FeedingRecord();
                String val;
                val = safeReadLine("recordId: "); if (val == null) return; r.setRecordId(val);
                val = safeReadLine("animalId: "); if (val == null) return; r.setAnimalId(val);
                val = safeReadLine("feedId: "); if (val == null) return; r.setFeedId(val);
                val = safeReadLine("employeeId: "); if (val == null) return; r.setEmployeeId(val);
                val = safeReadLine("quantity: "); if (val == null) return; r.setQuantity(new BigDecimal(val));
                feedingService.createFeedingRecord(r);
                System.out.println("投喂记录已创建，并已扣减库存（如无异常）。");
            }
            case "c" -> {
                String rid = safeReadLine("recordId: "); if (rid == null) return;
                String vid = safeReadLine("verifierId: "); if (vid == null) return;
                String notes = safeReadLine("notes: "); if (notes == null) notes = "";
                feedingService.approveFeeding(rid, vid, notes);
                System.out.println("审批完成（如无异常）。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageUsers() throws SQLException {
        System.out.println("-- 用户管理/登录测试 --");
        System.out.println("a. 创建用户");
        System.out.println("b. 登录测试");
        System.out.println("c. 解锁用户");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                UserAccount u = new UserAccount();
                String val;
                val = safeReadLine("userId: "); if (val == null) return; u.setUserId(val);
                val = safeReadLine("username: "); if (val == null) return; u.setUsername(val);
                val = safeReadLine("password: "); if (val == null) return; u.setPasswordHash(val);
                val = safeReadLine("role (super_admin/admin/staff): "); if (val == null) return; u.setRole(val);
                userService.createUser(u);
                System.out.println("用户已创建。");
            }
            case "b" -> {
                String uname = safeReadLine("username: "); if (uname == null) return;
                String pwd = safeReadLine("password: "); if (pwd == null) return;
                boolean ok = userService.authenticate(uname, pwd, "127.0.0.1", "cli");
                System.out.println(ok ? "登录成功" : "登录失败");
            }
            case "c" -> {
                String uid = safeReadLine("userId: "); if (uid == null) return;
                userService.unlockUser(uid);
                System.out.println("已解锁（如存在）。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void managePermissions() throws SQLException {
        System.out.println("-- 权限分配测试 --");
        System.out.println("a. 创建权限并分配给 role");
        System.out.println("b. 检查用户权限");
        System.out.print("请选择: ");
        String s = scanner.nextLine().trim();
        switch (s) {
            case "a" -> {
                Permission p = new Permission();
                String val;
                val = safeReadLine("permissionId: "); if (val == null) return; p.setPermissionId(val);
                val = safeReadLine("permissionCode: "); if (val == null) return; p.setPermissionCode(val);
                val = safeReadLine("permissionName: "); if (val == null) return; p.setPermissionName(val);
                val = safeReadLine("description (可选): "); if (val == null) val = ""; p.setDescription(val);
                val = safeReadLine("module (animal/enclosure/employee/feed/feeding/report/system/user): "); if (val == null) return; p.setModule(val);
                String gb = safeReadLine("grantedBy userId: "); if (gb == null) return;
                // 如果 permissionName 为空则用 permissionCode 作为名称
                if (p.getPermissionName() == null || p.getPermissionName().isEmpty()) {
                    p.setPermissionName(p.getPermissionCode());
                    System.out.println("未填写 permissionName，已使用 permissionCode 作为默认名称。");
                }
                // 简单校验
                if (p.getPermissionId() == null || p.getPermissionId().isEmpty() || p.getPermissionCode() == null || p.getPermissionCode().isEmpty()) {
                    System.out.println("permissionId 和 permissionCode 为必填项，创建失败。返回主菜单。");
                } else {
                    permissionService.createPermission(p);
                    permissionService.assignPermissionToRole("staff", p.getPermissionId(), gb);
                    System.out.println("创建并分配完成。");
                }
            }
            case "b" -> {
                String uid = safeReadLine("userId: "); if (uid == null) return;
                String code = safeReadLine("permissionCode: "); if (code == null) return;
                boolean ok = authService.userHasPermission(uid, code);
                System.out.println(ok ? "有权限" : "无权限");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageLogs() throws SQLException {
        System.out.println("-- 日志测试 --");
        System.out.println("a. 写登录日志");
        System.out.println("b. 写操作日志");
        System.out.print("请选择: ");
        String s = safeReadLine("");
        if (s == null) return;
        switch (s) {
            case "a" -> {
                LoginLog log = new LoginLog();
                String uid = safeReadLine("userId: "); if (uid == null) return; log.setUserId(uid);
                log.setLoginTime(LocalDateTime.now());
                log.setIpAddress("127.0.0.1");
                log.setLoginStatus("failed");
                logService.logLogin(log);
                System.out.println("登录日志已写入。");
            }
            case "b" -> {
                OperationLog op = new OperationLog();
                String uid = safeReadLine("userId: "); if (uid == null) return; op.setUserId(uid);
                op.setOperationTime(LocalDateTime.now());
                String module = safeReadLine("module: "); if (module == null) return; op.setModule(module);
                String type = safeReadLine("operationType (CREATE/UPDATE/DELETE/QUERY/LOGIN/LOGOUT/APPROVE/REJECT): "); if (type == null) return; op.setOperationType(type);
                op.setTableName(safeReadLine("tableName (可选): "));
                op.setRecordId(safeReadLine("recordId (可选): "));
                op.setDescription(safeReadLine("description (可选): "));
                logService.logOperation(op);
                System.out.println("操作日志已写入。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private void manageConfigs() throws SQLException {
        System.out.println("-- 系统配置 --");
        System.out.println("a. 读取配置");
        System.out.println("b. 写入/更新配置");
        System.out.println("c. 删除配置");
        System.out.print("请选择: ");
        String s = safeReadLine("");
        if (s == null) return;
        switch (s) {
            case "a" -> {
                String key = safeReadLine("configKey: "); if (key == null) return;
                SystemConfig cfg = configService.getConfig(key);
                System.out.println(cfg == null ? "未找到配置" : cfg);
            }
            case "b" -> {
                SystemConfig cfg = new SystemConfig();
                String key = safeReadLine("configKey: "); if (key == null) return; cfg.setConfigKey(key);
                String value = safeReadLine("configValue: "); if (value == null) return; cfg.setConfigValue(value);
                String type = safeReadLine("configType (string/number/boolean/json/date): "); if (type == null) return; cfg.setConfigType(type);
                String updatedBy = safeReadLine("updatedBy userId (可选): "); if (updatedBy != null && !updatedBy.isEmpty()) cfg.setUpdatedBy(updatedBy);
                configService.setConfig(cfg);
                System.out.println("配置已写入/更新。");
            }
            case "c" -> {
                String key = safeReadLine("configKey: "); if (key == null) return;
                configService.deleteConfig(key);
                System.out.println("配置已删除（如存在）。");
            }
            default -> System.out.println("未识别操作。返回主菜单。");
        }
    }

    private String safeReadLine(String prompt) {
        System.out.print(prompt);
        try {
            String line = scanner.nextLine();
            if (line == null) return null;
            return line.trim();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("输入被终止，返回主菜单。");
            return null;
        }
    }
}
