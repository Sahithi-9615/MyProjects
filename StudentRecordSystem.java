
import java.sql.*;
import java.util.Scanner;

public class StudentRecordSystem {
    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "sahithi";
    static final String PASS = "93saHI@mysql"; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Connecting to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            while (true) {
                // Simple menu for user
                System.out.println("\n=== Student Record Management System ===");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine(); 

                if (choice == 1) {
                    addStudent(conn, sc);
                } else if (choice == 2) {
                    viewStudents(conn);
                } else if (choice == 3) {
                    updateStudent(conn, sc);
                } else if (choice == 4) {
                    deleteStudent(conn, sc);
                } else if (choice == 5) {
                    conn.close();
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        sc.close();
    }

    // Method to add a student
    static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter CGPA: ");
        float cgpa = sc.nextFloat();

        String query = "INSERT INTO students VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, roll);
        stmt.setString(2, name);
        stmt.setString(3, email);
        stmt.setString(4, dept);
        stmt.setFloat(5, cgpa);

        int rows = stmt.executeUpdate();
        System.out.println(rows + " student(s) added successfully.");
    }

    // Method to display all students
    static void viewStudents(Connection conn) throws SQLException {
        String query = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("\n--- Student List ---");
        while (rs.next()) {
            int roll = rs.getInt("roll_no");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String dept = rs.getString("department");
            float cgpa = rs.getFloat("cgpa");

            System.out.println("Roll No: " + roll + ", Name: " + name + ", Email: " + email + ", Dept: " + dept + ", CGPA: " + cgpa);
        }
    }

    // Method to update student data
    static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Email: ");
        String email = sc.nextLine();

        System.out.print("Enter New CGPA: ");
        float cgpa = sc.nextFloat();

        String query = "UPDATE students SET email=?, cgpa=? WHERE roll_no=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setFloat(2, cgpa);
        stmt.setInt(3, roll);

        int rows = stmt.executeUpdate();
        System.out.println(rows + " student(s) updated.");
    }

    // Method to delete a student
    static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Roll No to delete: ");
        int roll = sc.nextInt();

        String query = "DELETE FROM students WHERE roll_no=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, roll);

        int rows = stmt.executeUpdate();
        System.out.println(rows + " student(s) deleted.");
    }
}

/*
 * --- MySQL Setup ---
 * 
 * CREATE DATABASE student_db;
 * USE student_db;
 * 
 * CREATE TABLE students (
 *     roll_no INT PRIMARY KEY,
 *     name VARCHAR(50),
 *     email VARCHAR(50),
 *     department VARCHAR(30),
 *     cgpa FLOAT
 * );
 * 
 */



