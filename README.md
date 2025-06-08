# 🏢 COMP603 Project - SimpleERP System

**Authors:** Yiming Gao & Soyeon Im 

---

## 🚀 Running the Project

This project is developed using **NetBeans IDE** and managed with **Apache Ant**. The application is a modern GUI-based Enterprise Resource Planning (ERP) system built with Java Swing and Apache Derby database.

### 📋 Prerequisites
- Java Development Kit (JDK) 21 or higher
- NetBeans IDE 23 (recommended)
- Apache Derby Database (included)


## 🔐 Test Accounts

👤 Test accounts are provided below, or you can register a new account on the registration page:

| Role     | Username | Password |
|----------|----------|----------|
| Admin    | admin    | admin123 |
| Student  | test     | test123  |

---

## ⭐ Project Features

Our team has developed a **comprehensive Enterprise Resource Planning (ERP) System** with a modern graphical user interface. The system includes the following key features:

### 🔑 1. Authentication System
- **User Login:** Secure login functionality with username and password validation
- **User Registration:** New users can create accounts through an intuitive registration interface
- **Session Management:** Proper session handling and logout functionality

### 👥 2. Employee Management
- **Employee Records:** Add, view, edit, and delete employee information
- **Department Organization:** Organize employees by departments
- **Search & Filter:** Quick search functionality for finding specific employees
- **Data Persistence:** All employee data is stored in Apache Derby database

### 📦 3. Product Inventory Management
- **Product Catalog:** Comprehensive product information management
- **Stock Tracking:** Real-time inventory quantity monitoring
- **Price Management:** Product pricing and cost tracking
- **Inventory Control:** Add, update, and remove products from inventory

### 📊 4. Reporting System
- **Employee Reports:** Generate comprehensive employee statistics
- **Inventory Reports:** View product inventory status and summaries
- **Data Visualization:** Clean, organized data presentation
- **Export Functionality:** Generate reports for business analysis

### 🎨 5. Modern User Interface
- **Responsive Design:** Clean, modern GUI with professional appearance
- **Tabbed Navigation:** Easy switching between different modules
- **Search Integration:** Built-in search functionality across all modules
- **Visual Feedback:** Interactive buttons with hover effects and icons

### 🗄️ 6. Database Integration
- **Apache Derby:** Embedded database for reliable data storage
- **Automatic Setup:** Database tables created automatically on first run
- **Backup System:** Automatic database backup functionality
- **Transaction Management:** Proper database transaction handling

### 🔧 7. System Architecture
- **MVC Pattern:** Clear separation of Model, View, and Controller components
- **DAO Pattern:** Data Access Objects for database operations
- **Singleton Pattern:** Database connection management
- **Resource Management:** Centralized resource and configuration management

---

## 🛠️ Technical Specifications

- **Language:** Java 21
- **GUI Framework:** Java Swing
- **Database:** Apache Derby (Embedded)
- **Build Tool:** Apache Ant
- **IDE:** NetBeans 23
- **Testing:** JUnit Framework
- **Design Patterns:** Singleton, DAO, MVC

---

## 📁 Project Structure

```
simpleerp/
├── src/
│   ├── gui/           # User interface components
│   ├── dao/           # Data access objects
│   ├── model/         # Data models
│   ├── db/            # Database management
│   ├── utils/         # Utility classes
│   └── resources/     # Icons and images
├── test/              # Unit tests
├── simpleerpdb/       # Derby database files
└── build.xml          # Ant build configuration
```

---

