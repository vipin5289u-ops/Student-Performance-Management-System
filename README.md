# Student Performance Management System

## Project Overview
The Student Performance Management System is a Java-based application developed using JDBC and MySQL. It manages student academic records, calculates average scores, assigns performance categories, identifies stronger subjects, and generates merit lists.

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- Maven
- IntelliJ IDEA

## Database
**Database Name:** `student_performance_db`

### Tables

**1. students**
- student_id (Primary Key)
- name
- email
- course
- programming_score
- mathematics_score

**2. student_skills**
- id (Primary Key)
- student_id (Foreign Key)
- skill

## Features

1. Add student records.
2. Calculate each student's average score.
3. Assign performance categories:
   - 85 or above: Excellent
   - 70–84.99: Good
   - 50–69.99: Satisfactory
   - Below 50: Needs Improvement
4. Identify the stronger subject.
5. Search students by ID, email, or course.
6. Generate a merit list in descending order of average score.
7. Identify students sharing the same technical skills.
8. Find students who need programming improvement but have mathematics scores of at least 75.
9. Prevent duplicate student IDs.
10. Validate scores between 0 and 100.

## Project Structure

```text
StudentPerformanceManagementSystem2/
│
├── src/
│   └── main/
│       └── java/
│           └── com/kiet/studentperformancemanagementsystem2/
│               ├── StudentPerformanceManagementSystem2Application.java
│               ├── DBConnection.java
│               ├── Student.java
│               ├── StudentDAO.java
│               └── Main.java
│
├── pom.xml
└── README.md
```

## Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE student_performance_db;
```

Create the required tables and configure the MySQL connection in `DBConnection.java`.

## Configuration

Update the following database credentials in `DBConnection.java`:

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/student_performance_db";

private static final String USER = "root";

private static final String PASSWORD = "your_password";
```

Replace `your_password` with your MySQL password.

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Configure the MySQL database.
4. Add the MySQL Connector/J Maven dependency.
5. Run the main Java application.
6. Follow the console menu to perform student management operations.

## Learning Outcomes

- Understanding JDBC connectivity.
- Performing CRUD operations using Java.
- Using PreparedStatement and ResultSet.
- Writing SQL queries with JOIN, GROUP BY, HAVING and ORDER BY.
- Implementing database constraints and score validation.
- Integrating Java applications with MySQL.

## Author

**Vipin**

B.Tech – Computer Science and Engineering (AI & ML)
