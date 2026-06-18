package com.EMPMANAGE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.EMPMANAGE.Entity.Employee;
import com.EMPMANAGE.Repository.EmployeeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository; // Injects your MySQL database gateway

    // 1. CREATE EMPLOYEE (Writes directly to MySQL)
    // POST http://localhost:9000/api/employees/create
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_EMP')")
    public ResponseEntity<?> createEmployee(@RequestBody Map<String, Object> employeeData) {
        
        Employee employee = new Employee();
        employee.setFirstName((String) employeeData.get("firstName"));
        employee.setLastName((String) employeeData.get("lastName"));
        employee.setEmail((String) employeeData.get("email"));
        employee.setPhone((String) employeeData.get("phone"));
        employee.setDepartment((String) employeeData.get("department"));
        employee.setDesignation((String) employeeData.get("designation"));
        employee.setJoiningDate((String) employeeData.get("joiningDate"));
        
        Number salaryNum = (Number) employeeData.get("salary");
        employee.setSalary(salaryNum != null ? salaryNum.doubleValue() : 0.0);
        employee.setStatus((String) employeeData.get("status"));

        // Save to DB (Hibernate automatically sets the auto-increment ID on savedEmployee)
        Employee savedEmployee = employeeRepository.save(employee);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Employee account created successfully.");
        
        Map<String, Object> dataReceived = new HashMap<>();
        dataReceived.put("id", savedEmployee.getId()); // ID is now visible in Postman!
        dataReceived.put("fullName", savedEmployee.getFirstName() + " " + savedEmployee.getLastName());
        dataReceived.put("email", savedEmployee.getEmail());
        dataReceived.put("phone", savedEmployee.getPhone());
        dataReceived.put("department", savedEmployee.getDepartment());
        dataReceived.put("designation", savedEmployee.getDesignation());
        dataReceived.put("joiningDate", savedEmployee.getJoiningDate());
        dataReceived.put("salary", savedEmployee.getSalary());
        dataReceived.put("status", savedEmployee.getStatus());
        
        response.put("data", dataReceived);

        return ResponseEntity.status(201).body(response);
    }

    // 2. UPDATE EMPLOYEE (Updates row records by ID)
    // PUT http://localhost:9000/api/employees/update/{id}
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_EMP')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Map<String, Object> employeeData) {
        
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Employee with ID " + id + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Employee employee = empOpt.get();

        if (employeeData.containsKey("firstName")) employee.setFirstName((String) employeeData.get("firstName"));
        if (employeeData.containsKey("lastName")) employee.setLastName((String) employeeData.get("lastName"));
        if (employeeData.containsKey("email")) employee.setEmail((String) employeeData.get("email"));
        if (employeeData.containsKey("phone")) employee.setPhone((String) employeeData.get("phone"));
        if (employeeData.containsKey("department")) employee.setDepartment((String) employeeData.get("department"));
        if (employeeData.containsKey("designation")) employee.setDesignation((String) employeeData.get("designation"));
        if (employeeData.containsKey("joiningDate")) employee.setJoiningDate((String) employeeData.get("joiningDate"));
        if (employeeData.containsKey("status")) employee.setStatus((String) employeeData.get("status"));
        
        if (employeeData.containsKey("salary")) {
            Number salaryNum = (Number) employeeData.get("salary");
            employee.setSalary(salaryNum != null ? salaryNum.doubleValue() : null);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Employee with ID " + id + " updated successfully.");
        response.put("data", updatedEmployee);

        return ResponseEntity.ok(response);
    }

    // 3. VIEW EMPLOYEE (Pulls real rows out of MySQL)
    // GET http://localhost:9000/api/employees/view/{id}
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('VIEW_EMP')")
    public ResponseEntity<?> viewEmployee(@PathVariable Long id) {
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Employee with ID " + id + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }
        return ResponseEntity.ok(empOpt.get());
    }

    // 4. DELETE EMPLOYEE (Deletes rows from your database completely)
    // DELETE http://localhost:9000/api/employees/delete/{id}
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_EMP')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Employee with ID " + id + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        employeeRepository.deleteById(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Employee record " + id + " successfully deleted from database records.");
        return ResponseEntity.ok(response);
    }
}