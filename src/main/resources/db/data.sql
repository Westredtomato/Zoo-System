-- 设置搜索路径
SET search_path TO manage;

-- 1. 展区数据
INSERT INTO enclosure (enclosure_id, name, capacity, status, description) VALUES
                                                                              ('E001', '猴山', 20, '可用', '灵长类动物展区'),
                                                                              ('E002', '熊猫馆', 5, '可用', '大熊猫专属展区'),
                                                                              ('E003', '猛兽区', 15, '可用', '大型食肉动物展区'),
                                                                              ('E004', '水族馆', 50, '维护中', '海洋生物展区'),
                                                                              ('E005', '飞禽园', 30, '可用', '鸟类展区');

-- 2. 动物数据
INSERT INTO animal (animal_id, name, species, gender, birth_date, enclosure_id, health_status) VALUES
                                                                                                   ('A001', '乐乐', '金丝猴', 'M', '2020-05-10', 'E001', '健康'),
                                                                                                   ('A002', '奇奇', '猕猴', 'M', '2021-03-15', 'E001', '健康'),
                                                                                                   ('A003', '花花', '大熊猫', 'F', '2019-07-20', 'E002', '健康'),
                                                                                                   ('A004', '壮壮', '东北虎', 'M', '2018-11-05', 'E003', '健康'),
                                                                                                   ('A005', '飞飞', '金刚鹦鹉', 'F', '2022-01-30', 'E005', '健康');

-- 3. 员工数据
INSERT INTO employee (employee_id, name, position, department, phone, email, hire_date, enclosure_id) VALUES
                                                                                                          ('EMP001', '张伟', '饲养员', '动物管理部', '13800138001', 'zhangwei@zoo.com', '2020-01-15', 'E001'),
                                                                                                          ('EMP002', '王芳', '兽医', '医疗保健部', '13800138002', 'wangfang@zoo.com', '2019-05-20', NULL),
                                                                                                          ('EMP003', '李强', '管理员', '运营管理部', '13800138003', 'liqiang@zoo.com', '2018-03-10', NULL),
                                                                                                          ('EMP004', '刘洋', '饲养员', '动物管理部', '13800138004', 'liuyang@zoo.com', '2021-06-25', 'E002');

-- 4. 饲料数据
INSERT INTO feed (feed_id, name, category, stock_quantity, unit, safety_stock, unit_price) VALUES
                                                                                               ('F001', '香蕉', '水果', 100.5, '千克', 20, 5.5),
                                                                                               ('F002', '竹子', '植物', 200.0, '千克', 50, 3.2),
                                                                                               ('F003', '鲜肉', '肉类', 50.0, '千克', 10, 25.0),
                                                                                               ('F004', '鱼饲料', '水产', 80.0, '千克', 15, 8.0),
                                                                                               ('F005', '鸟食', '谷物', 60.0, '千克', 12, 6.5);

-- 5. 权限数据（简化版）
INSERT INTO permission (permission_id, permission_code, permission_name, description, module) VALUES
                                                                                                  ('P001', 'animal:view', '查看动物', '查看动物信息', 'animal'),
                                                                                                  ('P002', 'animal:create', '创建动物', '添加新动物', 'animal'),
                                                                                                  ('P003', 'animal:update', '修改动物', '修改动物信息', 'animal'),
                                                                                                  ('P004', 'animal:delete', '删除动物', '删除动物记录', 'animal'),
                                                                                                  ('P011', 'enclosure:view', '查看展区', '查看展区信息', 'enclosure'),
                                                                                                  ('P012', 'enclosure:create', '创建展区', '添加新展区', 'enclosure'),
                                                                                                  ('P013', 'enclosure:update', '修改展区', '修改展区信息', 'enclosure'),
                                                                                                  ('P014', 'enclosure:delete', '删除展区', '删除展区记录', 'enclosure'),
                                                                                                  ('P021', 'employee:view', '查看员工', '查看员工信息', 'employee'),
                                                                                                  ('P022', 'employee:create', '创建员工', '添加新员工', 'employee'),
                                                                                                  ('P023', 'employee:update', '修改员工', '修改员工信息', 'employee'),
                                                                                                  ('P024', 'employee:delete', '删除员工', '删除员工记录', 'employee'),
                                                                                                  ('P031', 'feed:view', '查看饲料', '查看饲料信息', 'feed'),
                                                                                                  ('P032', 'feed:create', '创建饲料', '添加新饲料', 'feed'),
                                                                                                  ('P033', 'feed:update', '修改饲料', '修改饲料信息', 'feed'),
                                                                                                  ('P034', 'feed:delete', '删除饲料', '删除饲料记录', 'feed'),
                                                                                                  ('P041', 'feeding:view', '查看投喂记录', '查看投喂记录', 'feeding'),
                                                                                                  ('P042', 'feeding:create', '创建投喂记录', '添加投喂记录', 'feeding'),
                                                                                                  ('P043', 'feeding:update', '修改投喂记录', '修改投喂记录', 'feeding'),
                                                                                                  ('P044', 'feeding:delete', '删除投喂记录', '删除投喂记录', 'feeding'),
                                                                                                  ('P045', 'feeding:approve', '审核投喂记录', '审核投喂记录', 'feeding');

-- 6. 用户数据（简化密码）
INSERT INTO user_account (user_id, username, password_hash, employee_id, role, email, is_active) VALUES
                                                                                                     ('SUPER001', 'superadmin', 'admin123', 'EMP003', 'super_admin', 'superadmin@zoo.com', TRUE),
                                                                                                     ('ADMIN001', 'admin', 'admin123', 'EMP002', 'admin', 'admin@zoo.com', TRUE),
                                                                                                     ('STAFF001', 'zhangwei', 'staff123', 'EMP001', 'staff', 'zhangwei@zoo.com', TRUE);

-- 7. 角色权限（简化版）
INSERT INTO role_permission (role, permission_id, granted_by) VALUES
                                                                  ('super_admin', 'P001', 'SUPER001'),
                                                                  ('super_admin', 'P002', 'SUPER001'),
                                                                  ('super_admin', 'P003', 'SUPER001'),
                                                                  ('super_admin', 'P004', 'SUPER001'),
                                                                  ('super_admin', 'P011', 'SUPER001'),
                                                                  ('super_admin', 'P012', 'SUPER001'),
                                                                  ('super_admin', 'P013', 'SUPER001'),
                                                                  ('super_admin', 'P014', 'SUPER001'),
                                                                  ('super_admin', 'P021', 'SUPER001'),
                                                                  ('super_admin', 'P022', 'SUPER001'),
                                                                  ('super_admin', 'P023', 'SUPER001'),
                                                                  ('super_admin', 'P024', 'SUPER001'),
                                                                  ('super_admin', 'P031', 'SUPER001'),
                                                                  ('super_admin', 'P032', 'SUPER001'),
                                                                  ('super_admin', 'P033', 'SUPER001'),
                                                                  ('super_admin', 'P034', 'SUPER001'),
                                                                  ('super_admin', 'P041', 'SUPER001'),
                                                                  ('super_admin', 'P042', 'SUPER001'),
                                                                  ('super_admin', 'P043', 'SUPER001'),
                                                                  ('super_admin', 'P044', 'SUPER001'),
                                                                  ('super_admin', 'P045', 'SUPER001');

-- 8. 投喂记录数据（提供record_id，避免触发生成器）
INSERT INTO feeding_record (record_id, feeding_time, animal_id, feed_id, employee_id, quantity, status, verified_by) VALUES
                                                                                                                         ('202412010001', '2024-12-01 09:30:00', 'A001', 'F001', 'EMP001', 2.0, 'approved', 'ADMIN001'),
                                                                                                                         ('202412010002', '2024-12-01 10:15:00', 'A002', 'F001', 'EMP001', 1.5, 'approved', 'ADMIN001'),
                                                                                                                         ('202412010003', '2024-12-01 14:20:00', 'A003', 'F002', 'EMP004', 5.0, 'approved', 'ADMIN001'),
                                                                                                                         ('202412010004', '2024-12-01 16:00:00', 'A004', 'F003', 'EMP001', 3.0, 'pending', NULL),
                                                                                                                         ('202412010005', '2024-12-02 08:45:00', 'A005', 'F005', 'EMP004', 1.0, 'approved', 'ADMIN001');

-- 9. 系统配置
INSERT INTO system_config (config_key, config_value, config_type, description, is_public) VALUES
                                                                                              ('system.name', '动物园管理系统', 'string', '系统名称', TRUE),
                                                                                              ('system.version', '1.0.0', 'string', '系统版本', TRUE),
                                                                                              ('security.password.min_length', '8', 'number', '密码最小长度', FALSE),
                                                                                              ('security.login.max_attempts', '5', 'number', '最大登录尝试次数', FALSE),
                                                                                              ('backup.enabled', 'true', 'boolean', '是否启用自动备份', FALSE);