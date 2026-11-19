package com.kiet.studentperformancemanagementsystem2.student;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_performance_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Vipin@5289";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL, USER, PASSWORD
        );
    }
}
