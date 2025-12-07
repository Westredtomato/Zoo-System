-- 设置搜索路径
SET search_path TO manage;

-- 1. 展区使用情况视图
CREATE OR REPLACE VIEW v_enclosure_usage AS
SELECT
    e.enclosure_id,
    e.name AS 展区名称,
    e.capacity AS 最大容量,
    e.current_animal_count AS 当前动物数,
    e.status AS 状态,
    ROUND(e.current_animal_count * 100.0 / e.capacity, 2) AS 使用率百分比,
    (e.capacity - e.current_animal_count) AS 剩余容量
FROM enclosure e
ORDER BY e.enclosure_id;

-- 2. 投喂记录详情视图
CREATE OR REPLACE VIEW v_feeding_detail AS
SELECT
    fr.record_id,
    fr.feeding_time,
    a.animal_id,
    a.name AS animal_name,
    a.species,
    e.enclosure_id,
    e.name AS enclosure_name,
    f.feed_id,
    f.name AS feed_name,
    emp.employee_id,
    emp.name AS employee_name,
    fr.quantity,
    fr.status
FROM feeding_record fr
         JOIN animal a ON fr.animal_id = a.animal_id
         JOIN enclosure e ON a.enclosure_id = e.enclosure_id
         JOIN feed f ON fr.feed_id = f.feed_id
         JOIN employee emp ON fr.employee_id = emp.employee_id
ORDER BY fr.feeding_time DESC;