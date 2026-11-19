package com.kiet.studentperformancemanagementsystem2.student;




import java.sql.*;
import java.util.Scanner;

public class StudentDAO {

    // 1. Add Student
    public void addStudent(Student s) {

        String sql = "INSERT INTO students " +
                "(student_id, name, email, course, programming_score, mathematics_score) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        if (!validScore(s.getProgrammingScore()) ||
                !validScore(s.getMathematicsScore())) {
            System.out.println("Scores must be between 0 and 100!");
            return;
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getCourse());
            ps.setDouble(5, s.getProgrammingScore());
            ps.setDouble(6, s.getMathematicsScore());

            ps.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Student ID or Email!");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // Score Validation
    private boolean validScore(double score) {
        return score >= 0 && score <= 100;
    }

    // 2. Calculate Average and Performance Category
    public void displayPerformance() {

        String sql = "SELECT student_id, name, " +
                "(programming_score + mathematics_score)/2 AS average_score, " +
                "CASE " +
                "WHEN (programming_score + mathematics_score)/2 >= 85 THEN 'Excellent' " +
                "WHEN (programming_score + mathematics_score)/2 >= 70 THEN 'Good' " +
                "WHEN (programming_score + mathematics_score)/2 >= 50 THEN 'Satisfactory' " +
                "ELSE 'Needs Improvement' END AS category, " +
                "CASE " +
                "WHEN programming_score > mathematics_score THEN 'Programming' " +
                "WHEN mathematics_score > programming_score THEN 'Mathematics' " +
                "ELSE 'Both Equal' END AS stronger_subject " +
                "FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                System.out.println("\nID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Average: " + rs.getDouble("average_score"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Stronger Subject: " +
                        rs.getString("stronger_subject"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3. Search Student by ID
    public void searchById(int id) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    printStudent(rs);
                } else {
                    System.out.println("Student not found!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4. Search Student by Email
    public void searchByEmail(String email) {

        String sql = "SELECT * FROM students WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    printStudent(rs);
                } else {
                    System.out.println("Student not found!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 5. Search Student by Course
    public void searchByCourse(String course) {

        String sql = "SELECT * FROM students WHERE course = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    printStudent(rs);
                    found = true;
                }

                if (!found) {
                    System.out.println("No students found!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 6. Generate Merit List
    public void meritList() {

        String sql = "SELECT student_id, name, course, " +
                "(programming_score + mathematics_score)/2 AS average_score " +
                "FROM students ORDER BY average_score DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== MERIT LIST ==========");

            int rank = 1;

            while (rs.next()) {
                System.out.println(
                        rank++ + ". " +
                                rs.getString("name") +
                                " | ID: " + rs.getInt("student_id") +
                                " | Course: " + rs.getString("course") +
                                " | Average: " + rs.getDouble("average_score")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 7. Students Having Same Technical Skills
    public void sameSkills() {

        String sql = "SELECT skill, " +
                "GROUP_CONCAT(student_id ORDER BY student_id) AS student_ids " +
                "FROM student_skills " +
                "GROUP BY skill " +
                "HAVING COUNT(DISTINCT student_id) > 1";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== COMMON SKILLS ==========");

            boolean found = false;

            while (rs.next()) {
                System.out.println(
                        "Skill: " + rs.getString("skill") +
                                " | Student IDs: " + rs.getString("student_ids")
                );
                found = true;
            }

            if (!found) {
                System.out.println("No common skills found!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add Technical Skill
    public void addSkill(int studentId, String skill) {

        String sql = "INSERT INTO student_skills(student_id, skill) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setString(2, skill);

            ps.executeUpdate();

            System.out.println("Skill added successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Invalid student ID or duplicate skill!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 8. Programming Improvement + Mathematics >= 75
    public void improvementStudents() {

        String sql = "SELECT * FROM students " +
                "WHERE programming_score < 50 " +
                "AND mathematics_score >= 75";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== STUDENTS NEEDING IMPROVEMENT ==========");

            boolean found = false;

            while (rs.next()) {
                printStudent(rs);
                found = true;
            }

            if (!found) {
                System.out.println("No matching students found!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Display Student Details
    private void printStudent(ResultSet rs) throws SQLException {

        System.out.println("\nStudent ID: " + rs.getInt("student_id"));
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Email: " + rs.getString("email"));
        System.out.println("Course: " + rs.getString("course"));
        System.out.println("Programming: " + rs.getDouble("programming_score"));
        System.out.println("Mathematics: " + rs.getDouble("mathematics_score"));
    }
}
