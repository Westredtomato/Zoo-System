package org.example.dao;

import org.example.model.Enclosure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnclosureDAO extends BaseDAO<Enclosure> {

    public int insert(Enclosure enclosure) throws SQLException {
        String sql = "INSERT INTO enclosure (enclosure_id, name, capacity, current_animal_count, status, description) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                enclosure.getEnclosureId(),
                enclosure.getName(),
                enclosure.getCapacity(),
                enclosure.getCurrentAnimalCount(),
                enclosure.getStatus(),
                enclosure.getDescription()
        );
    }

    public Enclosure findById(String enclosureId) throws SQLException {
        String sql = "SELECT * FROM enclosure WHERE enclosure_id = ?";
        return queryForObject(sql, enclosureId);
    }

    public List<Enclosure> findAll() throws SQLException {
        String sql = "SELECT * FROM enclosure ORDER BY enclosure_id";
        return queryForList(sql);
    }

    public int update(Enclosure enclosure) throws SQLException {
        String sql = "UPDATE enclosure SET name = ?, capacity = ?, current_animal_count = ?, status = ?, description = ?, " +
                "updated_at = CURRENT_TIMESTAMP WHERE enclosure_id = ?";
        return executeUpdate(sql,
                enclosure.getName(),
                enclosure.getCapacity(),
                enclosure.getCurrentAnimalCount(),
                enclosure.getStatus(),
                enclosure.getDescription(),
                enclosure.getEnclosureId()
        );
    }

    public int delete(String enclosureId) throws SQLException {
        String sql = "DELETE FROM enclosure WHERE enclosure_id = ?";
        return executeUpdate(sql, enclosureId);
    }

    public int countEnclosures() throws SQLException {
        String sql = "SELECT COUNT(*) FROM enclosure";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected Enclosure mapResultSetToEntity(ResultSet rs) throws SQLException {
        Enclosure e = new Enclosure();
        e.setEnclosureId(rs.getString("enclosure_id"));
        e.setName(rs.getString("name"));
        e.setCapacity(rs.getInt("capacity"));
        e.setCurrentAnimalCount(rs.getInt("current_animal_count"));
        e.setStatus(rs.getString("status"));
        e.setDescription(rs.getString("description"));

        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            e.setCreatedAt(createdAt.toLocalDateTime());
        }
        java.sql.Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            e.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return e;
    }
}