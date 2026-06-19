package com.EMPMANAGE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.EMPMANAGE.Entity.LeaveRequest;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    long countByStatus(String status); // Automatically translates to: SELECT COUNT(*) FROM leave_requests WHERE status = ?
}