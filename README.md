# 🏢 SimpleERP System - COMP603 Project

**Authors:** Yiming Gao & Soyeon Im

## 🚀 Quick Start

A modern **Enterprise Resource Planning (ERP)** system built with Java Swing and Apache Derby database.

### 📋 Prerequisites
- Java 21+ 
- NetBeans IDE 23 (recommended)

### 🔐 Test Accounts
| Username | Password | Role   |
|----------|----------|--------|
| admin    | admin123 | Admin  |

## ⭐ Key Features

### 🔑 Authentication
- User login/registration with secure validation
- Session management and logout functionality

### 👥 Employee Management  
- CRUD operations for employee records
- Department organization and search functionality
- Data persistence with Apache Derby

### 📦 Product Inventory
- Product catalog with stock tracking
- Price management and inventory control
- Smart delete functionality (single/batch)

### 📊 Reporting & UI
- Employee and inventory reports
- Modern tabbed interface with search integration
- Responsive design with visual feedback

## 🛠️ Technical Stack

- **Language:** Java 21
- **Database:** Apache Derby (Embedded)
- **GUI:** Java Swing
- **Patterns:** MVC, DAO, Singleton
- **Testing:** JUnit

## 📁 Project Structure
```
simpleerp/
├── src/gui/           # UI components  
├── src/dao/           # Data access
├── src/model/         # Data models
├── src/db/            # Database management
├── test/              # Unit tests
└── simpleerpdb/       # Derby database
```

