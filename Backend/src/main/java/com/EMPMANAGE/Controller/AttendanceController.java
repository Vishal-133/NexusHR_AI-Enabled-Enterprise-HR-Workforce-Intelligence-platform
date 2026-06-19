package com.EMPMANAGE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.EMPMANAGE.Entity.AttendanceRecord;
import com.EMPMANAGE.Repository.AttendanceRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository; // Injects your MySQL database gateway
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 1. MARK CHECK-IN (Saves directly to MySQL)
    // POST http://localhost:9000/api/attendance/check-in
    @PostMapping("/check-in")
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public ResponseEntity<?> checkIn(@RequestBody Map<String, Object> attendanceData) {
        
        String userOfficialEmail = (String) attendanceData.get("userOfficialEmail");
        String firstName = (String) attendanceData.get("firstName");
        String lastName = (String) attendanceData.get("lastName");
        String department = (String) attendanceData.get("department");
        String role = (String) attendanceData.get("role");

        // Uses a custom look-up method in the repository to check for ongoing shifts
        List<AttendanceRecord> activeRecords = attendanceRepository.findByUserOfficialEmailAndCheckOutTimeIsNull(userOfficialEmail);
        if (!activeRecords.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "User is already checked in for today.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Map data directly into your Entity Object
        AttendanceRecord record = new AttendanceRecord();
        record.setUserOfficialEmail(userOfficialEmail);
        record.setFirstName(firstName);
        record.setLastName(lastName);
        record.setDepartment(department);
        record.setRole(role);
        record.setCheckInTime(LocalDateTime.now().format(formatter));
        record.setCheckOutTime(null);
        record.setWorkHours("0.0 hrs");
        record.setStatus("PRESENT");

        // SAVE directly to the 'attendance_records' table in MySQL
        AttendanceRecord savedRecord = attendanceRepository.save(record);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Checked in successfully.");
        response.put("attendanceId", savedRecord.getId()); // Uses the auto-increment database row ID
        response.put("data", savedRecord);

        return ResponseEntity.status(201).body(response);
    }

    // 2. MARK CHECK-OUT (Finds existing row inside MySQL and updates it)
    // POST http://localhost:9000/api/attendance/check-out
    @PostMapping("/check-out")
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public ResponseEntity<?> checkOut(@RequestBody Map<String, Object> attendanceData) {
        
        String userOfficialEmail = (String) attendanceData.get("userOfficialEmail");

        // Find the active row inside your MySQL table that has a null check_out_time
        List<AttendanceRecord> activeRecords = attendanceRepository.findByUserOfficialEmailAndCheckOutTimeIsNull(userOfficialEmail);
        if (activeRecords.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "No active check-in record found for this user. Please check-in first.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        AttendanceRecord record = activeRecords.get(0);
        LocalDateTime checkOutTime = LocalDateTime.now();
        LocalDateTime checkInTime = LocalDateTime.parse(record.getCheckInTime(), formatter);

        // Calculate total time spent working dynamically
        Duration duration = Duration.between(checkInTime, checkOutTime);
        long minutesWorked = duration.toMinutes();
        double hoursWorked = minutesWorked / 60.0;

        record.setCheckOutTime(checkOutTime.format(formatter));
        record.setWorkHours(String.format("%.2f hrs", hoursWorked));
        record.setStatus("COMPLETED");

        // Commit updates to the database row
        AttendanceRecord updatedRecord = attendanceRepository.save(record);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Checked out successfully.");
        response.put("data", updatedRecord);

        return ResponseEntity.ok(response);
    }

    // 3. VIEW ATTENDANCE BY RECORD ID (Pulls directly out of MySQL rows)
    // GET http://localhost:9000/api/attendance/view/{recordId}
    @GetMapping("/view/{recordId}")
    @PreAuthorize("hasAuthority('VIEW_EMP')")
    public ResponseEntity<?> viewAttendanceRecord(@PathVariable Long recordId) {
        
        Optional<AttendanceRecord> recordOpt = attendanceRepository.findById(recordId);
        
        if (recordOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Attendance record with ID " + recordId + " not found.");
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("attendanceRecord", recordOpt.get());
        
        return ResponseEntity.ok(response);
    }
}