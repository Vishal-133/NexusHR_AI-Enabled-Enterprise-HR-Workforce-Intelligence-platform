package com.EMPMANAGE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
// Crucial: It must explicitly extend JpaRepository with the full path to your Entity type
public interface PayrollSlip extends JpaRepository<com.EMPMANAGE.Entity.PayrollSlip, Long> {
}