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

The goal of NexusHR is to reduce manual HR operations, improve employee engagement, and provide data-driven decision-making capabilities for organizations through modern web technologies and intelligent automation.

---

## 🎯 Project Objectives

* Automate HR processes and workflows
* Manage employee lifecycle efficiently
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
Employee  Payroll  Performance
Module    Module   Module
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

* React.js
* TypeScript
* Tailwind CSS
* Vite
* HTML5
* CSS3
* JavaScript

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* REST APIs
* JWT Authentication

### Database

* MySQL 8.0
* Hibernate ORM
* Spring Data JPA

### AI Integration

* Spring AI
* OpenAI APIs

### Tools & DevOps

* Git
* GitHub
* Docker
* GitHub Actions

---

## 🔐 Security Features

* JWT Authentication
* Role-Based Access Control (RBAC)
* Password Encryption
* Secure API Endpoints
* Input Validation
* Session Management
* Audit Logging
* OWASP Security Best Practices

---

## 📂 Project Modules

| Module                 | Description                          |
| ---------------------- | ------------------------------------ |
| Employee Management    | Manage employee records and profiles |
| Attendance Management  | Track attendance and leaves          |
| Payroll Processing     | Automated payroll generation         |
| Performance Management | Employee evaluation system           |
| AI Analytics           | Workforce intelligence and insights  |
| Dashboard              | Real-time HR analytics               |
| Notifications          | System alerts and updates            |

---

## 📊 Business Benefits

* Reduces manual HR workload
* Improves workforce productivity
* Enhances employee engagement
* Centralized employee data management
* Faster payroll processing
* Better workforce planning
* Data-driven decision making

---

## 📁 Project Structure

```bash
NexusHR/
│
├── frontend/
│   ├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   └── assets/
│
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── entities/
│   ├── security/
│   └── configuration/
│
├── database/
│
├── documentation/
│
└── README.md
```

---

## ⚙️ Installation & Setup

### Clone Repository

```bash
git clone https://github.com/Vishal-133/NexusHR_AI-Enabled-Enterprise-HR-Workforce-Intelligence-platform.git
```

### Navigate to Project

```bash
cd NexusHR_AI-Enabled-Enterprise-HR-Workforce-Intelligence-platform
```

---

### Backend Setup

```bash
cd backend

mvn clean install

mvn spring-boot:run
```

---

### Frontend Setup

```bash
cd frontend

npm install

npm run dev
```

---

### MySQL Configuration

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nexushr
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 📸 Screenshots

Add your project screenshots here.

### Login Page

![Login](screenshots/login.png)

### Dashboard

![Dashboard](screenshots/dashboard.png)

### Employee Management

![Employee Management](screenshots/employee-management.png)

### Attendance Tracking

![Attendance](screenshots/attendance.png)

### Payroll Management

![Payroll](screenshots/payroll.png)

### AI Analytics Dashboard

![AI Dashboard](screenshots/analytics.png)

---

## 🔄 Workflow

1. Employee Registration
2. Attendance Tracking
3. Leave Management
4. Payroll Processing
5. Performance Evaluation
6. AI-Based Workforce Analysis
7. Reporting & Analytics

---

## 🔮 Future Enhancements

* Mobile Application
* Resume Screening System
* Recruitment Management
* Employee Sentiment Analysis
* Advanced AI Recommendations
* Multi-Tenant SaaS Support
* Biometric Attendance Integration
* Advanced Workforce Forecasting

---

## 🧪 Testing

```bash
mvn test
```

---

## 📈 Performance Goals

* High Availability
* Secure Authentication
* Real-Time Analytics
* Scalable Architecture
* Responsive User Interface
* Enterprise-Level Security

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Open a Pull Request

---

## 👨‍💻 Author

### Vishal

M.Tech Software Engineering
VIT Vellore

GitHub: https://github.com/Vishal-133

---

## 📜 License

This project is developed for educational, portfolio, internship, and learning purposes.

---

## ⭐ Support

If you like this project, please give it a ⭐ on GitHub and support the development.

---

### "Transforming Human Resource Management Through AI-Powered Workforce Intelligence."
