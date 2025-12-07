package org.example.dao;

import org.example.model.Animal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnimalDAO extends BaseDAO<Animal> {

    // 插入新动物（需要调用方传入业务主键 animal_id，如 A001）
    public int insert(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (animal_id, name, species, gender, birth_date, enclosure_id, " +
                "health_status, description, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return executeUpdate(sql,
                animal.getAnimalId(),
                animal.getName(),
                animal.getSpecies(),
                animal.getGender(),
                animal.getBirthDate() != null ? Date.valueOf(animal.getBirthDate()) : null,
                animal.getEnclosureId(),
                animal.getHealthStatus(),
                animal.getDescription(),
                animal.getCreatedBy()
        );
    }

    public Animal findById(String animalId) throws SQLException {
        String sql = "SELECT * FROM animal WHERE animal_id = ?";
        return queryForObject(sql, animalId);
    }

    public List<Animal> findAll() throws SQLException {
        String sql = "SELECT * FROM animal ORDER BY animal_id";
        return queryForList(sql);
    }

    public List<Animal> findByEnclosure(String enclosureId) throws SQLException {
        String sql = "SELECT * FROM animal WHERE enclosure_id = ? ORDER BY animal_id";
        return queryForList(sql, enclosureId);
    }

    public List<Animal> findBySpecies(String species) throws SQLException {
        String sql = "SELECT * FROM animal WHERE species = ? ORDER BY animal_id";
        return queryForList(sql, species);
    }

    public int update(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET name = ?, species = ?, gender = ?, birth_date = ?, enclosure_id = ?, " +
                "health_status = ?, description = ?, updated_by = CURRENT_USER, updated_at = CURRENT_TIMESTAMP " +
                "WHERE animal_id = ?";

        return executeUpdate(sql,
                animal.getName(),
                animal.getSpecies(),
                animal.getGender(),
                animal.getBirthDate() != null ? Date.valueOf(animal.getBirthDate()) : null,
                animal.getEnclosureId(),
                animal.getHealthStatus(),
                animal.getDescription(),
                animal.getAnimalId()
        );
    }

    public int delete(String animalId) throws SQLException {
        String sql = "DELETE FROM animal WHERE animal_id = ?";
        return executeUpdate(sql, animalId);
    }

    public int countAnimals() throws SQLException {
        String sql = "SELECT COUNT(*) FROM animal";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected Animal mapResultSetToEntity(ResultSet rs) throws SQLException {
        Animal a = new Animal();
        a.setAnimalId(rs.getString("animal_id"));
        a.setName(rs.getString("name"));
        a.setSpecies(rs.getString("species"));
        a.setGender(rs.getString("gender"));

        Date birth = rs.getDate("birth_date");
        if (birth != null) {
            a.setBirthDate(birth.toLocalDate());
        }

        a.setEnclosureId(rs.getString("enclosure_id"));
        a.setHealthStatus(rs.getString("health_status"));
        a.setDescription(rs.getString("description"));
        a.setCreatedBy(rs.getString("created_by"));

        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            a.setCreatedAt(createdAt.toLocalDateTime());
        }

        a.setUpdatedBy(rs.getString("updated_by"));
        java.sql.Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            a.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return a;
    }
}