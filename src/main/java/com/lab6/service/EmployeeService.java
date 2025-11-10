package com.lab6.service;

import com.lab6.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

@Service
public class EmployeeService {
    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final Map<String, Employee> employeesByUsername = new ConcurrentHashMap<>();
    private final Map<String, Employee> employeesByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    public Employee createEmployee(Employee employee) {
        validateEmployeeData(employee);

        if (employeesByUsername.containsKey(employee.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("Username already exists: " + employee.getUsername());
        }
        if (employeesByEmail.containsKey(employee.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }

        Employee newEmployee = new Employee();
        newEmployee.setId(idGenerator.getAndIncrement());
        newEmployee.setUsername(employee.getUsername().trim());
        newEmployee.setEmail(employee.getEmail().trim().toLowerCase());
        newEmployee.setFirstName(employee.getFirstName() != null ? employee.getFirstName().trim() : "");
        newEmployee.setLastName(employee.getLastName() != null ? employee.getLastName().trim() : "");
        newEmployee.setCreatedAt(LocalDateTime.now());
        newEmployee.setUpdatedAt(LocalDateTime.now());

        employees.put(newEmployee.getId(), newEmployee);
        employeesByUsername.put(newEmployee.getUsername().toLowerCase(), newEmployee);
        employeesByEmail.put(newEmployee.getEmail().toLowerCase(), newEmployee);

        return newEmployee;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employees.get(id);
        if (existingEmployee == null) {
            throw new NoSuchElementException("Employee with id " + id + " not found");
        }

        if (employee.getUsername() != null && !employee.getUsername().trim().isEmpty()) {
            String newUsername = employee.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(existingEmployee.getUsername())) {
                if (employeesByUsername.containsKey(newUsername.toLowerCase())) {
                    throw new IllegalArgumentException("Username already exists: " + newUsername);
                }
                employeesByUsername.remove(existingEmployee.getUsername().toLowerCase());
                existingEmployee.setUsername(newUsername);
                employeesByUsername.put(newUsername.toLowerCase(), existingEmployee);
            }
        }

        if (employee.getEmail() != null && !employee.getEmail().trim().isEmpty()) {
            String newEmail = employee.getEmail().trim().toLowerCase();
            if (!newEmail.equals(existingEmployee.getEmail())) {
                if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
                    throw new IllegalArgumentException("Invalid email format: " + newEmail);
                }
                if (employeesByEmail.containsKey(newEmail)) {
                    throw new IllegalArgumentException("Email already exists: " + newEmail);
                }
                employeesByEmail.remove(existingEmployee.getEmail());
                existingEmployee.setEmail(newEmail);
                employeesByEmail.put(newEmail, existingEmployee);
            }
        }

        if (employee.getFirstName() != null) {
            existingEmployee.setFirstName(employee.getFirstName().trim());
        }
        if (employee.getLastName() != null) {
            existingEmployee.setLastName(employee.getLastName().trim());
        }
        existingEmployee.setUpdatedAt(LocalDateTime.now());

        return existingEmployee;
    }

    public void deleteEmployee(Long id) {
        Employee removed = employees.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Employee with id " + id + " not found");
        }
        employeesByUsername.remove(removed.getUsername().toLowerCase());
        employeesByEmail.remove(removed.getEmail().toLowerCase());
    }

    public Optional<Employee> getEmployeeByUsername(String username) {
        return Optional.ofNullable(employeesByUsername.get(username.toLowerCase()));
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return Optional.ofNullable(employeesByEmail.get(email.toLowerCase()));
    }

    private void validateEmployeeData(Employee employee) {
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(employee.getEmail().trim()).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + employee.getEmail());
        }
    }
}

