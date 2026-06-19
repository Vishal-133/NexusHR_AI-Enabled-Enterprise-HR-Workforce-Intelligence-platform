package com.EMPMANAGE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.EMPMANAGE.Entity.LeaveRequest;
import com.EMPMANAGE.Repository.LeaveRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepository; // Injects your MySQL database interface

    // 1. APPLY LEAVE
    // POST http://localhost:9000/api/leave/apply
    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public ResponseEntity<?> applyLeave(@RequestBody Map<String, Object> leaveData) {
        
        // Create a new Instance of your Entity Class instead of a temporary HashMap record
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserOfficialEmail((String) leaveData.get("userOfficialEmail"));
        leaveRequest.setFirstName((String) leaveData.get("firstName"));
        leaveRequest.setLastName((String) leaveData.get("lastName"));
        leaveRequest.setDepartment((String) leaveData.get("department"));
        leaveRequest.setRole((String) leaveData.get("role"));
        leaveRequest.setLeaveType((String) leaveData.get("leaveType"));
        leaveRequest.setStartDate((String) leaveData.get("startDate"));
        leaveRequest.setEndDate((String) leaveData.get("endDate"));
        leaveRequest.setReason((String) leaveData.get("reason"));
        leaveRequest.setStatus("PENDING"); // Default initial state

        // SAVE directly to the 'leave_requests' table in MySQL
        LeaveRequest savedRequest = leaveRepository.save(leaveRequest);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Leave request filed successfully.");
        response.put("generatedLeaveId", savedRequest.getId()); // Uses auto-incremented database ID
        response.put("data", savedRequest);

        return ResponseEntity.status(201).body(response);
    }

    // 2. APPROVE LEAVE
    // POST http://localhost:9000/api/leave/approve/{id}
    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('APPROVE_LEAVE')")
    public ResponseEntity<?> approveLeave(@PathVariable Long id) {
        
        // Find the record dynamically inside MySQL by its Primary Key ID
        Optional<LeaveRequest> leaveOpt = leaveRepository.findById(id);
        
        if (leaveOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Leave application with ID " + id + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        LeaveRequest leaveRequest = leaveOpt.get();
        
        // Dynamically update the status field to APPROVED
        leaveRequest.setStatus("APPROVED");
        
        // Save the updated object back to commit changes to the database
        LeaveRequest updatedRequest = leaveRepository.save(leaveRequest);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Leave application approved successfully.");
        response.put("data", updatedRequest);

        return ResponseEntity.ok(response);
    }
}