-- 设置搜索路径
SET search_path TO manage;

-- 1. 动物表索引
CREATE INDEX idx_animal_species ON animal(species);
CREATE INDEX idx_animal_enclosure ON animal(enclosure_id);

-- 2. 展区表索引
CREATE INDEX idx_enclosure_status ON enclosure(status);

-- 3. 员工表索引
CREATE INDEX idx_employee_position ON employee(position);

-- 4. 饲料表索引
CREATE INDEX idx_feed_category ON feed(category);

-- 5. 投喂记录表索引
CREATE INDEX idx_feeding_time ON feeding_record(feeding_time);
CREATE INDEX idx_feeding_animal ON feeding_record(animal_id);
CREATE INDEX idx_feeding_employee ON feeding_record(employee_id);
CREATE INDEX idx_feeding_status ON feeding_record(status);

-- 6. 用户账户表索引
CREATE INDEX idx_user_username ON user_account(username);
CREATE INDEX idx_user_role ON user_account(role);