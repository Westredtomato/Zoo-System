package org.example.controller;

import org.example.model.Employee;
import org.example.service.EmployeeService;

import java.sql.SQLException;
import java.util.List;
public class EmployeeController {
    private final EmployeeService service = new EmployeeService();

    public List<Employee> listAll() throws SQLException { return service.listAllEmployees(); }
    public Employee get(String id) throws SQLException { return service.getEmployee(id); }
    public void create(Employee e) throws SQLException { service.createEmployee(e); }
    public void update(Employee e) throws SQLException { service.updateEmployee(e); }
    public void delete(String id) throws SQLException { service.deleteEmployee(id); }
    public void assignEnclosure(String employeeId, String enclosureId) throws SQLException { service.assignEnclosure(employeeId, enclosureId); }
}
