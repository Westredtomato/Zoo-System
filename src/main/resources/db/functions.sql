-- 设置搜索路径
SET search_path TO manage;

-- 1. 自动更新updated_at字段
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 2. 生成投喂记录ID（现在表已经有数据了）
CREATE OR REPLACE FUNCTION generate_feeding_record_id()
RETURNS TRIGGER AS $$
DECLARE
date_part VARCHAR(8);
    seq_num INT := 1;
BEGIN
    IF NEW.record_id IS NULL THEN
        date_part := TO_CHAR(CURRENT_DATE, 'YYYYMMDD');

        -- 现在表已存在且有数据，可以安全查询
SELECT COALESCE(MAX(SUBSTRING(record_id FROM 9)::INT), 0) + 1
INTO seq_num
FROM feeding_record
WHERE record_id LIKE date_part || '%';

NEW.record_id := date_part || LPAD(seq_num::VARCHAR, 4, '0');
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;