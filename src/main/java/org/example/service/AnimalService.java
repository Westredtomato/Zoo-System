package org.example.service;

import org.example.dao.AnimalDAO;
import org.example.dao.EnclosureDAO;
import org.example.model.Animal;
import org.example.model.Enclosure;

import java.sql.SQLException;
import java.util.List;

/**
 * 动物管理业务逻辑：
 * - 动物基本信息的增删改查
 * - 动物转移展区（包含容量检查和展区当前动物数维护）
 * - 健康状态更新
 */
public class AnimalService {

    private final AnimalDAO animalDAO = new AnimalDAO();
    private final EnclosureDAO enclosureDAO = new EnclosureDAO();

    /** 新增动物：会检查展区是否存在且未超容量，并更新展区 current_animal_count */
    public void createAnimal(Animal animal) throws SQLException {
        Enclosure enclosure = enclosureDAO.findById(animal.getEnclosureId());
        if (enclosure == null) {
            throw new IllegalArgumentException("展区不存在: " + animal.getEnclosureId());
        }
        if (enclosure.getCurrentAnimalCount() >= enclosure.getCapacity()) {
            throw new IllegalStateException("展区已满，无法添加动物: " + enclosure.getEnclosureId());
        }

        animalDAO.insert(animal);
        enclosure.setCurrentAnimalCount(enclosure.getCurrentAnimalCount() + 1);
        enclosureDAO.update(enclosure);
    }

    /** 更新动物基础信息（不负责转移展区） */
    public void updateAnimal(Animal animal) throws SQLException {
        animalDAO.update(animal);
    }

    /** 删除动物，并自动扣减所在展区的 current_animal_count */
    public void deleteAnimal(String animalId) throws SQLException {
        Animal animal = animalDAO.findById(animalId);
        if (animal == null) {
            return; // 已不存在，视为成功
        }
        String enclosureId = animal.getEnclosureId();
        animalDAO.delete(animalId);

        if (enclosureId != null) {
            Enclosure enclosure = enclosureDAO.findById(enclosureId);
            if (enclosure != null && enclosure.getCurrentAnimalCount() > 0) {
                enclosure.setCurrentAnimalCount(enclosure.getCurrentAnimalCount() - 1);
                enclosureDAO.update(enclosure);
            }
        }
    }

    /** 将动物从一个展区转移到另一个展区 */
    public void transferAnimal(String animalId, String targetEnclosureId) throws SQLException {
        Animal animal = animalDAO.findById(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("动物不存在: " + animalId);
        }
        String sourceEnclosureId = animal.getEnclosureId();
        if (targetEnclosureId.equals(sourceEnclosureId)) {
            return; // 不需要转移
        }

        Enclosure target = enclosureDAO.findById(targetEnclosureId);
        if (target == null) {
            throw new IllegalArgumentException("目标展区不存在: " + targetEnclosureId);
        }
        if (target.getCurrentAnimalCount() >= target.getCapacity()) {
            throw new IllegalStateException("目标展区已满: " + targetEnclosureId);
        }

        animal.setEnclosureId(targetEnclosureId);
        animalDAO.update(animal);

        if (sourceEnclosureId != null) {
            Enclosure source = enclosureDAO.findById(sourceEnclosureId);
            if (source != null && source.getCurrentAnimalCount() > 0) {
                source.setCurrentAnimalCount(source.getCurrentAnimalCount() - 1);
                enclosureDAO.update(source);
            }
        }

        target.setCurrentAnimalCount(target.getCurrentAnimalCount() + 1);
        enclosureDAO.update(target);
    }

    /** 更新动物健康状态 */
    public void updateHealthStatus(String animalId, String healthStatus) throws SQLException {
        Animal animal = animalDAO.findById(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("动物不存在: " + animalId);
        }
        animal.setHealthStatus(healthStatus);
        animalDAO.update(animal);
    }

    public Animal getAnimal(String animalId) throws SQLException {
        return animalDAO.findById(animalId);
    }

    public List<Animal> listAllAnimals() throws SQLException {
        return animalDAO.findAll();
    }

    public int countAnimals() throws SQLException {
        return animalDAO.countAnimals();
    }
}
