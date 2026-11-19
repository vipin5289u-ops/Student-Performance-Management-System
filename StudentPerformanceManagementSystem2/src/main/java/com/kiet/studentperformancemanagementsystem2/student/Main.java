package com.kiet.studentperformancemanagementsystem2.student;



import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\n==================================");
            System.out.println(" STUDENT PERFORMANCE MANAGEMENT");
            System.out.println("==================================");

            System.out.println("1. Add Student");
            System.out.println("2. Calculate Average & Performance");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Email");
            System.out.println("5. Search Student by Course");
            System.out.println("6. Generate Merit List");
            System.out.println("7. Find Students with Same Skills");
            System.out.println("8. Students Needing Programming Improvement");
            System.out.println("9. Add Technical Skill");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid choice!");
                continue;
            }

            switch (choice) {

                case 1:

                    try {
                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Programming Score: ");
                        double programming = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter Mathematics Score: ");
                        double mathematics = Double.parseDouble(sc.nextLine());

                        if (programming < 0 || programming > 100 ||
                                mathematics < 0 || mathematics > 100) {
                            System.out.println("Scores must be between 0 and 100!");
                            break;
                        }

                        Student s = new Student(
                                id, name, email, course,
                                programming, mathematics
                        );

                        dao.addStudent(s);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Enter numeric values.");
                    }

                    break;

                case 2:
                    dao.displayPerformance();
                    break;

                case 3:

                    System.out.print("Enter Student ID: ");

                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        dao.searchById(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID!");
                    }

                    break;

                case 4:

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    dao.searchByEmail(email);
                    break;

                case 5:

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    dao.searchByCourse(course);
                    break;

                case 6:
                    dao.meritList();
                    break;

                case 7:
                    dao.sameSkills();
                    break;

                case 8:
                    dao.improvementStudents();
                    break;

                case 9:

                    try {
                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Technical Skill: ");
                        String skill = sc.nextLine();

                        dao.addSkill(id, skill);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Student ID!");
                    }

                    break;

                case 0:

                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
