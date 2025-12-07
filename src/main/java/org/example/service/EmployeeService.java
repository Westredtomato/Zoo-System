package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.dao.EnclosureDAO;
import org.example.model.Employee;
import org.example.model.Enclosure;

import java.sql.SQLException;
import java.util.List;

/**
 * 员工管理业务逻辑：
 * - 员工基本信息管理
 * - 员工分配展区
 */
public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final EnclosureDAO enclosureDAO = new EnclosureDAO();

    public void createEmployee(Employee employee) throws SQLException {
        // 若指定了展区，验证展区存在
        if (employee.getEnclosureId() != null) {
            Enclosure e = enclosureDAO.findById(employee.getEnclosureId());
            if (e == null) {
                throw new IllegalArgumentException("指定的展区不存在: " + employee.getEnclosureId());
            }
        }
        employeeDAO.insert(employee);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        if (employee.getEnclosureId() != null) {
            Enclosure e = enclosureDAO.findById(employee.getEnclosureId());
            if (e == null) {
                throw new IllegalArgumentException("指定的展区不存在: " + employee.getEnclosureId());
            }
        }
        employeeDAO.update(employee);
    }

    public void deleteEmployee(String employeeId) throws SQLException {
        employeeDAO.delete(employeeId);
    }

    public Employee getEmployee(String employeeId) throws SQLException {
        return employeeDAO.findById(employeeId);
    }

    public List<Employee> listAllEmployees() throws SQLException {
        return employeeDAO.findAll();
    }

    public int countEmployees() throws SQLException {
        return employeeDAO.countEmployees();
    }

    /** 为员工分配展区 */
    public void assignEnclosure(String employeeId, String enclosureId) throws SQLException {
        Employee emp = employeeDAO.findById(employeeId);
        if (emp == null) {
            throw new IllegalArgumentException("员工不存在: " + employeeId);
        }
        Enclosure enc = enclosureDAO.findById(enclosureId);
        if (enc == null) {
            throw new IllegalArgumentException("展区不存在: " + enclosureId);
        }
        emp.setEnclosureId(enclosureId);
        employeeDAO.update(emp);
    }
}

