package org.example.service;

import org.example.dao.EnclosureDAO;
import org.example.model.Enclosure;

import java.sql.SQLException;
import java.util.List;

/**
 * 展区管理业务逻辑：
 * - 展区增删改查
 * - 容量与当前动物数量维护
 * - 展区状态管理
 */
public class EnclosureService {

    private final EnclosureDAO enclosureDAO = new EnclosureDAO();

    /** 创建展区，要求初始 current_animal_count 不大于 capacity 且容量 > 0 */
    public void createEnclosure(Enclosure enclosure) throws SQLException {
        if (enclosure.getCapacity() <= 0) {
            throw new IllegalArgumentException("展区容量必须大于 0");
        }
        if (enclosure.getCurrentAnimalCount() > enclosure.getCapacity()) {
            throw new IllegalArgumentException("当前动物数不能超过容量");
        }
        enclosureDAO.insert(enclosure);
    }

    /** 更新展区基础信息（同样校验容量约束） */
    public void updateEnclosure(Enclosure enclosure) throws SQLException {
        if (enclosure.getCapacity() <= 0) {
            throw new IllegalArgumentException("展区容量必须大于 0");
        }
        if (enclosure.getCurrentAnimalCount() > enclosure.getCapacity()) {
            throw new IllegalArgumentException("当前动物数不能超过容量");
        }
        enclosureDAO.update(enclosure);
    }

    /** 删除展区：调用前应确保没有动物在此展区，或由调用方负责业务校验 */
    public void deleteEnclosure(String enclosureId) throws SQLException {
        enclosureDAO.delete(enclosureId);
    }

    public Enclosure getEnclosure(String enclosureId) throws SQLException {
        return enclosureDAO.findById(enclosureId);
    }

    public List<Enclosure> listAllEnclosures() throws SQLException {
        return enclosureDAO.findAll();
    }

    public int countEnclosures() throws SQLException {
        return enclosureDAO.countEnclosures();
    }

    /** 判断展区是否可用：状态为“可用”且未满 */
    public boolean isEnclosureAvailable(Enclosure enclosure) {
        if (enclosure == null) return false;
        if (!"可用".equals(enclosure.getStatus())) return false;
        return enclosure.getCurrentAnimalCount() < enclosure.getCapacity();
    }
}
