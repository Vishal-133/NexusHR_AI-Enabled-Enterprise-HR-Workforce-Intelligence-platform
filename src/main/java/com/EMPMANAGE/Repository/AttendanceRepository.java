package com.EMPMANAGE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.EMPMANAGE.Entity.AttendanceRecord;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    // Custom finder method to check for check-ins that haven't checked out yet
    List<AttendanceRecord> findByUserOfficialEmailAndCheckOutTimeIsNull(String userOfficialEmail);
}