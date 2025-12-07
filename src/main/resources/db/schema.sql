-- 设置搜索路径
SET search_path TO manage;

-- 1. 展区表
CREATE TABLE enclosure (
                           enclosure_id VARCHAR(10) PRIMARY KEY,
                           name VARCHAR(50) NOT NULL,
                           capacity INT NOT NULL,
                           current_animal_count INT DEFAULT 0,
                           status VARCHAR(20) DEFAULT '可用',
                           description TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE enclosure
    ADD CONSTRAINT enclosure_capacity_check CHECK (capacity > 0);
ALTER TABLE enclosure
    ADD CONSTRAINT enclosure_count_check CHECK (current_animal_count <= capacity);
ALTER TABLE enclosure
    ADD CONSTRAINT enclosure_status_check
        CHECK (status IN ('可用', '维护中', '已满', '关闭'));

-- 2. 动物表
CREATE TABLE animal (
                        animal_id VARCHAR(10) PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        species VARCHAR(50) NOT NULL,
                        gender CHAR(1) NOT NULL,
                        birth_date DATE,
                        enclosure_id VARCHAR(10) NOT NULL,
                        health_status VARCHAR(20) DEFAULT '健康',
                        description TEXT,
                        created_by VARCHAR(20),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_by VARCHAR(20),
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE animal
    ADD CONSTRAINT animal_gender_check CHECK (gender IN ('M', 'F'));
ALTER TABLE animal
    ADD CONSTRAINT animal_enclosure_fk
        FOREIGN KEY (enclosure_id) REFERENCES enclosure(enclosure_id) ON UPDATE CASCADE;

-- 3. 员工表
CREATE TABLE employee (
                          employee_id VARCHAR(10) PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          position VARCHAR(30) NOT NULL,
                          department VARCHAR(30),
                          phone VARCHAR(20),
                          email VARCHAR(100),
                          hire_date DATE,
                          enclosure_id VARCHAR(10),
                          created_by VARCHAR(20),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE employee
    ADD CONSTRAINT employee_enclosure_fk
        FOREIGN KEY (enclosure_id) REFERENCES enclosure(enclosure_id) ON UPDATE CASCADE;

-- 4. 饲料表
CREATE TABLE feed (
                      feed_id VARCHAR(10) PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      category VARCHAR(30),
                      stock_quantity NUMERIC(10,2) NOT NULL DEFAULT 0,
                      unit VARCHAR(10) NOT NULL,
                      safety_stock NUMERIC(10,2) DEFAULT 10,
                      supplier VARCHAR(100),
                      unit_price NUMERIC(10,2),
                      created_by VARCHAR(20),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_by VARCHAR(20),
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE feed
    ADD CONSTRAINT feed_stock_check CHECK (stock_quantity >= 0);

-- 5. 投喂记录表
CREATE TABLE feeding_record (
                                record_id VARCHAR(15) PRIMARY KEY,
                                feeding_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                animal_id VARCHAR(10) NOT NULL,
                                feed_id VARCHAR(10) NOT NULL,
                                employee_id VARCHAR(10) NOT NULL,
                                quantity NUMERIC(8,2) NOT NULL,
                                notes TEXT,
                                status VARCHAR(20) DEFAULT 'pending',
                                created_by VARCHAR(20),
                                verified_by VARCHAR(20),
                                verification_time TIMESTAMP,
                                verification_notes TEXT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE feeding_record
    ADD CONSTRAINT feeding_quantity_check CHECK (quantity > 0);
ALTER TABLE feeding_record
    ADD CONSTRAINT feeding_status_check
        CHECK (status IN ('pending', 'approved', 'rejected'));
ALTER TABLE feeding_record
    ADD CONSTRAINT feeding_animal_fk
        FOREIGN KEY (animal_id) REFERENCES animal(animal_id) ON UPDATE CASCADE;
ALTER TABLE feeding_record
    ADD CONSTRAINT feeding_feed_fk
        FOREIGN KEY (feed_id) REFERENCES feed(feed_id) ON UPDATE CASCADE;
ALTER TABLE feeding_record
    ADD CONSTRAINT feeding_employee_fk
        FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE;

-- 6. 用户账户表（简化密码约束）
CREATE TABLE user_account (
                              user_id VARCHAR(20) PRIMARY KEY,
                              username VARCHAR(50) UNIQUE NOT NULL,
                              password_hash VARCHAR(255) NOT NULL,
                              employee_id VARCHAR(10),
                              role VARCHAR(20) DEFAULT 'staff',
                              email VARCHAR(100) UNIQUE,
                              phone VARCHAR(20),
                              avatar_url VARCHAR(255),
                              is_active BOOLEAN DEFAULT TRUE,
                              is_locked BOOLEAN DEFAULT FALSE,
                              failed_attempts INT DEFAULT 0,
                              locked_until TIMESTAMP,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_login TIMESTAMP,
                              password_changed_at TIMESTAMP
);

ALTER TABLE user_account
    ADD CONSTRAINT user_role_check CHECK (role IN ('super_admin', 'admin', 'staff'));
ALTER TABLE user_account
    ADD CONSTRAINT user_employee_fk
        FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE;

-- 7. 权限表
CREATE TABLE permission (
                            permission_id VARCHAR(20) PRIMARY KEY,
                            permission_code VARCHAR(50) UNIQUE NOT NULL,
                            permission_name VARCHAR(100) NOT NULL,
                            description TEXT,
                            module VARCHAR(50) NOT NULL
);

ALTER TABLE permission
    ADD CONSTRAINT permission_module_check
        CHECK (module IN ('animal', 'enclosure', 'employee', 'feed', 'feeding', 'report', 'system', 'user'));

-- 8. 角色权限关联表
CREATE TABLE role_permission (
                                 role VARCHAR(20) NOT NULL,
                                 permission_id VARCHAR(20) NOT NULL,
                                 granted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 granted_by VARCHAR(20),
                                 PRIMARY KEY (role, permission_id)
);

ALTER TABLE role_permission
    ADD CONSTRAINT role_permission_fk
        FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON DELETE CASCADE;
ALTER TABLE role_permission
    ADD CONSTRAINT role_permission_user_fk
        FOREIGN KEY (granted_by) REFERENCES user_account(user_id) ON DELETE SET NULL;

-- 9. 登录日志表
CREATE SEQUENCE login_log_seq START 1;
CREATE TABLE login_log (
                           log_id INT PRIMARY KEY DEFAULT nextval('login_log_seq'),
                           user_id VARCHAR(20) NOT NULL,
                           login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           ip_address VARCHAR(45),
                           user_agent TEXT,
                           login_status VARCHAR(20) NOT NULL,
                           failure_reason VARCHAR(200)
);

ALTER TABLE login_log
    ADD CONSTRAINT login_status_check CHECK (login_status IN ('success', 'failed', 'locked'));
ALTER TABLE login_log
    ADD CONSTRAINT login_log_user_fk FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE;

-- 10. 操作日志表
CREATE SEQUENCE operation_log_seq START 1;
CREATE TABLE operation_log (
                               log_id INT PRIMARY KEY DEFAULT nextval('operation_log_seq'),
                               user_id VARCHAR(20) NOT NULL,
                               operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               module VARCHAR(50) NOT NULL,
                               operation_type VARCHAR(20) NOT NULL,
                               table_name VARCHAR(50),
                               record_id VARCHAR(50),
                               old_values TEXT,
                               new_values TEXT,
                               ip_address VARCHAR(45),
                               description TEXT
);

ALTER TABLE operation_log
    ADD CONSTRAINT operation_type_check
        CHECK (operation_type IN ('CREATE', 'UPDATE', 'DELETE', 'QUERY', 'LOGIN', 'LOGOUT', 'APPROVE', 'REJECT'));
ALTER TABLE operation_log
    ADD CONSTRAINT operation_log_user_fk FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE;

-- 11. 系统配置表
CREATE TABLE system_config (
                               config_key VARCHAR(50) PRIMARY KEY,
                               config_value TEXT NOT NULL,
                               config_type VARCHAR(20) DEFAULT 'string',
                               description VARCHAR(200),
                               is_public BOOLEAN DEFAULT FALSE,
                               updated_by VARCHAR(20),
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE system_config
    ADD CONSTRAINT config_type_check CHECK (config_type IN ('string', 'number', 'boolean', 'json', 'date'));
ALTER TABLE system_config
    ADD CONSTRAINT config_user_fk FOREIGN KEY (updated_by) REFERENCES user_account(user_id) ON DELETE SET NULL;