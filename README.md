# NexusHR – AI-Enabled Enterprise HR & Workforce Intelligence Platform

> Production-Grade Java Full-Stack HR Management System with Real-Time Analytics & AI-Powered Workforce Insights

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![React](https://img.shields.io/badge/React-19-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 📌 Overview

NexusHR is an AI-enabled Enterprise Human Resource Management Platform designed to automate and streamline the complete employee lifecycle. The application integrates attendance management, payroll processing, employee performance tracking, workforce analytics, and AI-driven insights into a unified platform.

The goal of NexusHR is to reduce manual HR operations, improve employee engagement, and provide data-driven decision-making capabilities through modern web technologies and intelligent automation.

---

## 🎯 Project Objectives

* Automate HR processes and workflows
* Manage the employee lifecycle efficiently
* Simplify payroll and attendance management
* Track employee performance and productivity
* Generate workforce insights using AI
* Improve decision-making through analytics
* Provide a secure and scalable enterprise solution

---

## 🚀 Key Features

### 👥 Employee Management

* Employee onboarding and offboarding
* Employee profile management
* Department and role allocation
* Employee record maintenance

### ⏰ Attendance & Leave Management

* Daily attendance tracking
* Leave request management
* Leave approval workflow
* Attendance analytics

### 💰 Payroll Management

* Automated salary calculations
* Payroll processing
* Tax and deduction handling
* Payslip generation

### 📈 Performance Management

* Performance reviews
* Goal tracking
* Employee rating system
* Performance analytics

### 🤖 AI Workforce Intelligence

* Employee attrition prediction
* Workforce analytics
* Skill gap analysis
* Employee engagement scoring
* AI-powered recommendations

### 📊 Dashboard & Reporting

* Real-time HR analytics
* Employee statistics
* Department-wise insights
* Report generation

### 🔔 Notifications

* Approval notifications
* Attendance alerts
* Payroll updates
* Company announcements

---

## 🏗️ System Architecture

```text
Frontend (React + TypeScript)
            │
            ▼
Spring Boot REST APIs
            │
 ┌──────────┼──────────┐
 │          │          │
 ▼          ▼          ▼
Employee   Payroll   Performance
Module     Module     Module
            │
            ▼
        MySQL Database
            │
            ▼
      AI Analytics Engine
```

---

## 🛠️ Technology Stack

### Frontend

* React 19
* TypeScript
* Tailwind CSS
* Vite
* HTML5
* CSS3
* JavaScript

### Backend

* Java 21
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* JWT Authentication

### Database

* MySQL 8.0
* Hibernate ORM

### AI Integration

* Spring AI
* OpenAI APIs

### Tools & DevOps

* Git & GitHub
* Docker
* GitHub Actions

---

## 🔐 Security Features

* JWT Authentication
* Role-Based Access Control (RBAC)

  * ADMIN
  * HR
  * EMPLOYEE
* Password Encryption (BCrypt)
* Secure REST APIs
* Input Validation
* Session Management
* Audit Logging

---

## 📂 Project Modules

| Module                 | Description                          |
| ---------------------- | ------------------------------------ |
| Employee Management    | Manage employee records and profiles |
| Attendance Management  | Track attendance and leave requests  |
| Payroll Processing     | Automated payroll generation         |
| Performance Management | Employee evaluation and reviews      |
| AI Analytics           | Workforce intelligence and insights  |
| Dashboard              | Real-time HR analytics               |
| Notifications          | Alerts and system updates            |

---

## 📁 Project Structure

```bash
NexusHR_AI-Enabled-Enterprise-HR-Workforce-Intelligence-platform/
│
├── Backend/
│   ├── src/
│   │   └── main/java/com/EMPMANAGE/
│   │       ├── Controller/
│   │       ├── Service/
│   │       ├── Repository/
│   │       ├── DTO/
│   │       ├── Entity/
│   │       └── Security/
│   │
│   ├── pom.xml
│   └── mvnw
│
├── employee-management-ui/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── App.tsx
│   │   └── main.tsx
│   │
│   ├── package.json
│   └── vite.config.ts
│
└── README.md
```

---

## ⚙️ Installation & Setup

### 1. Clone Repository

```bash
git clone https://github.com/Vishal-133/NexusHR_AI-Enabled-Enterprise-HR-Workforce-Intelligence-platform.git

cd NexusHR_AI-Enabled-Enterprise-HR-Workforce-Intelligence-platform
```

---

### 2. Database Setup (MySQL)

Create a database:

```sql
CREATE DATABASE employee_management;
```

Update:

```properties
Backend/src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=root
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

### 3. Run Backend

```bash
cd Backend

./mvnw clean install

./mvnw spring-boot:run
```

Backend Server:

```text
http://localhost:8080
```

---

### 4. Run Frontend

```bash
cd employee-management-ui

npm install

npm run dev
```

Frontend Server:

```text
http://localhost:5173
```

---

## 📸 Screenshots

### Login Page

*Add Screenshot Here*

### Dashboard

*Add Screenshot Here*

### Employee Management

*Add Screenshot Here*

### Attendance Management

*Add Screenshot Here*

### Payroll Module

*Add Screenshot Here*

### AI Analytics Dashboard

*Add Screenshot Here*

---

## 🔮 Future Enhancements

* Cross-Platform Mobile Application
* AI Resume Screening System
* Employee Sentiment Analysis using NLP
* Biometric Attendance Integration
* Advanced Predictive Attrition Models
* Chatbot-based HR Assistant
* Multi-Tenant Enterprise Support

---

## 👨‍💻 Author

### Vishal

**Education:** M.Tech Software Engineering, VIT Vellore

**GitHub:** https://github.com/Vishal-133

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/new-feature
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push the branch

```bash
git push origin feature/new-feature
```

5. Open a Pull Request

---

## 📜 License

This project is licensed under the MIT License.

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

---

### "Transforming Human Resource Management Through AI-Powered Workforce Intelligence."
