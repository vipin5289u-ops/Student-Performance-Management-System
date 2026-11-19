package com.kiet.studentperformancemanagementsystem2.student;




public class Student {

    private int studentId;
    private String name;
    private String email;
    private String course;
    private double programmingScore;
    private double mathematicsScore;

    public Student(int studentId, String name, String email,
                   String course, double programmingScore,
                   double mathematicsScore) {

        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.course = course;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }
}
