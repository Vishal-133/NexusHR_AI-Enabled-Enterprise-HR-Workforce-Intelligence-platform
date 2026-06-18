package com.EMPMANAGE.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payroll_slips")
public class PayrollSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userOfficialEmail;
    private String firstName;
    private String lastName;
    private String department;
    private String role;
    private Double baseSalary;
    private Double allowances;
    private Double taxDeductions;
    private Double netSalary;
    private String paymentStatus; // PROCESSED, CREDITED

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserOfficialEmail() { return userOfficialEmail; }
    public void setUserOfficialEmail(String userOfficialEmail) { this.userOfficialEmail = userOfficialEmail; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(Double baseSalary) { this.baseSalary = baseSalary; }

    public Double getAllowances() { return allowances; }
    public void setAllowances(Double allowances) { this.allowances = allowances; }

    public Double getTaxDeductions() { return taxDeductions; }
    public void setTaxDeductions(Double taxDeductions) { this.taxDeductions = taxDeductions; }

    public Double getNetSalary() { return netSalary; }
    public void setNetSalary(Double netSalary) { this.netSalary = netSalary; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}