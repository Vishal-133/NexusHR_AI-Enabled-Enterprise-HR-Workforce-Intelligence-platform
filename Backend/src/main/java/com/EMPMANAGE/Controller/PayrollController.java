package com.EMPMANAGE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private com.EMPMANAGE.Repository.PayrollSlip payrollSlipRepository; 

    // 1. RUN PAYROLL
    // POST http://localhost:9000/api/payroll/run
    @PostMapping("/run")
    @PreAuthorize("hasAuthority('RUN_PAYROLE')")
    public ResponseEntity<?> runPayroll(@RequestBody Map<String, Object> payrollData) {
        
        com.EMPMANAGE.Entity.PayrollSlip slip = new com.EMPMANAGE.Entity.PayrollSlip();
        
        slip.setUserOfficialEmail((String) payrollData.get("userOfficialEmail"));
        slip.setFirstName((String) payrollData.get("firstName"));
        slip.setLastName((String) payrollData.get("lastName"));
        slip.setDepartment((String) payrollData.get("department"));
        slip.setRole((String) payrollData.get("role"));
        
        Number baseSalaryNum = (Number) payrollData.get("baseSalary");
        double baseSalary = (baseSalaryNum != null) ? baseSalaryNum.doubleValue() : 0.0;
        
        // Calculations
        double allowances = baseSalary * 0.12;
        double taxDeductions = baseSalary * 0.10;
        double netSalary = baseSalary + allowances - taxDeductions;
        
        slip.setBaseSalary(baseSalary);
        slip.setAllowances(allowances);
        slip.setTaxDeductions(taxDeductions);
        slip.setNetSalary(netSalary);
        slip.setPaymentStatus("PROCESSED");

        // Saves cleanly now that the repository interface extends JpaRepository explicitly
        com.EMPMANAGE.Entity.PayrollSlip savedSlip = payrollSlipRepository.save(slip);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Payroll processed successfully.");
        response.put("generatedSlipId", savedSlip.getId());
        response.put("data", savedSlip);
        
        return ResponseEntity.status(201).body(response);
    }

    // 2. VIEW PAYROLL DETAILS
    // GET http://localhost:9000/api/payroll/view/{slipId}
    @GetMapping("/view/{slipId}")
    @PreAuthorize("hasAuthority('VIEW_PAYROLL')")
    public ResponseEntity<?> viewPayrollSlip(@PathVariable Long slipId) {
        
        Optional<com.EMPMANAGE.Entity.PayrollSlip> slipOpt = payrollSlipRepository.findById(slipId);
        
        if (slipOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Payroll record with Slip ID " + slipId + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("payrollRecord", slipOpt.get());
        
        return ResponseEntity.ok(response);
    }
}