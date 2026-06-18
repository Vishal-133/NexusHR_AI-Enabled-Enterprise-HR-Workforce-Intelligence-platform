package com.EMPMANAGE.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    // 1. GET SYSTEM ANALYTICS SUMMARY
    // GET http://localhost:9000/api/analytics/dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('VIEW_PAYROLL')") // Restricting dashboard view to Admin/HR tier permissions
    public ResponseEntity<?> getDashboardAnalytics() {
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Dashboard analytics summary metrics fetched successfully.");

        // 1. Attendance Metrics Summary
        Map<String, Object> attendanceStats = new HashMap<>();
        attendanceStats.put("totalPresentToday", 12);
        attendanceStats.put("activeCheckIns", 4);
        attendanceStats.put("lateArrivals", 1);
        response.put("attendanceAnalytics", attendanceStats);

        // 2. Leave System Metrics Summary
        Map<String, Object> leaveStats = new HashMap<>();
        leaveStats.put("pendingApprovals", 3);
        leaveStats.put("approvedThisMonth", 8);
        leaveStats.put("rejectedThisMonth", 1);
        response.put("leaveAnalytics", leaveStats);

        // 3. Financial & Payroll Metrics Summary
        Map<String, Object> payrollStats = new HashMap<>();
        payrollStats.put("totalPayrollProcessedThisMonth", 875000.00);
        payrollStats.put("pendingDisbursals", 0);
        payrollStats.put("averageEmployeeSalary", 72916.66);
        response.put("payrollAnalytics", payrollStats);

        // 4. Department Distribution Snapshot
        Map<String, Integer> departmentDistribution = new HashMap<>();
        departmentDistribution.put("IT", 5);
        departmentDistribution.put("HR", 2);
        departmentDistribution.put("Finance", 2);
        departmentDistribution.put("Operations", 4);
        response.put("departmentDistribution", departmentDistribution);

        return ResponseEntity.ok(response);
    }
}