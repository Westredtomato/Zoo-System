-- 设置搜索路径
SET search_path TO manage;

-- OpenGauss使用EXECUTE PROCEDURE，不是EXECUTE FUNCTION
CREATE TRIGGER update_enclosure_updated_at
    BEFORE UPDATE ON enclosure
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();

CREATE TRIGGER update_animal_updated_at
    BEFORE UPDATE ON animal
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();

CREATE TRIGGER update_feed_updated_at
    BEFORE UPDATE ON feed
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();

CREATE TRIGGER update_feeding_record_updated_at
    BEFORE UPDATE ON feeding_record
    FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();

CREATE TRIGGER generate_feeding_record_id_trigger
    BEFORE INSERT ON feeding_record
    FOR EACH ROW
    EXECUTE PROCEDURE generate_feeding_record_id();