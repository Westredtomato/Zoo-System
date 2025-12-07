package org.example.dao;

import org.example.model.Employee;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDAO extends BaseDAO<Employee> {

    public int insert(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (employee_id, name, position, department, phone, email, hire_date, enclosure_id, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                employee.getEmployeeId(),
                employee.getName(),
                employee.getPosition(),
                employee.getDepartment(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate() != null ? Date.valueOf(employee.getHireDate()) : null,
                employee.getEnclosureId(),
                employee.getCreatedBy()
        );
    }

    public Employee findById(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        return queryForObject(sql, employeeId);
    }

    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM employee ORDER BY employee_id";
        return queryForList(sql);
    }

    public List<Employee> findByPosition(String position) throws SQLException {
        String sql = "SELECT * FROM employee WHERE position = ? ORDER BY employee_id";
        return queryForList(sql, position);
    }

    public int update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, position = ?, department = ?, phone = ?, email = ?, hire_date = ?, enclosure_id = ? " +
                "WHERE employee_id = ?";
        return executeUpdate(sql,
                employee.getName(),
                employee.getPosition(),
                employee.getDepartment(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate() != null ? Date.valueOf(employee.getHireDate()) : null,
                employee.getEnclosureId(),
                employee.getEmployeeId()
        );
    }

    public int delete(String employeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return executeUpdate(sql, employeeId);
    }

    public int countEmployees() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee";
        Object result = executeScalar(sql);
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    protected Employee mapResultSetToEntity(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(rs.getString("employee_id"));
        e.setName(rs.getString("name"));
        e.setPosition(rs.getString("position"));
        e.setDepartment(rs.getString("department"));
        e.setPhone(rs.getString("phone"));
        e.setEmail(rs.getString("email"));

        Date hire = rs.getDate("hire_date");
        if (hire != null) {
            e.setHireDate(hire.toLocalDate());
        }
        e.setEnclosureId(rs.getString("enclosure_id"));
        e.setCreatedBy(rs.getString("created_by"));

        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            e.setCreatedAt(createdAt.toLocalDateTime());
        }
        return e;
    }
}