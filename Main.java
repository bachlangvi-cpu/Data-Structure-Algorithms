import java.util.Scanner;

class Student {
    String id;
    String name;
    double marks;
    String rank;

    Student next;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = getRank(marks);
        this.next = null;
    }

    public static String getRank(double marks) {
        if (marks < 5.0) return "Fail";
        else if (marks < 6.5) return "Medium";
        else if (marks < 7.5) return "Good";
        else if (marks < 9.0) return "Very Good";
        else return "Excellent";
    }
}

class StudentLinkedList {
    private Student head;

    // Add student
    public boolean addStudent(String id, String name, double marks) {
        if (searchById(id) != null) {
            return false; // Duplicate ID
        }

        Student newStudent = new Student(id, name, marks);

        if (head == null) {
            head = newStudent;
        } else {
            Student temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newStudent;
        }
        return true;
    }

    // Display all students
    public void displayStudents() {
        if (head == null) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n===== STUDENT LIST =====");
        System.out.printf("%-10s %-25s %-10s %-15s\n", "ID", "Name", "Marks", "Rank");
        System.out.println("--------------------------------------------------------------");

        Student temp = head;
        while (temp != null) {
            System.out.printf("%-10s %-25s %-10.2f %-15s\n",
                    temp.id, temp.name, temp.marks, temp.rank);
            temp = temp.next;
        }
    }

    // Search student by ID
    public Student searchById(String id) {
        Student temp = head;
        while (temp != null) {
            if (temp.id.equalsIgnoreCase(id)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    // Edit student
    public boolean editStudent(String id, String newName, double newMarks) {
        Student student = searchById(id);
        if (student != null) {
            student.name = newName;
            student.marks = newMarks;
            student.rank = Student.getRank(newMarks);
            return true;
        }
        return false;
    }

    // Delete student
    public boolean deleteStudent(String id) {
        if (head == null) return false;

        if (head.id.equalsIgnoreCase(id)) {
            head = head.next;
            return true;
        }

        Student temp = head;
        while (temp.next != null && !temp.next.id.equalsIgnoreCase(id)) {
            temp = temp.next;
        }

        if (temp.next == null) {
            return false;
        }

        temp.next = temp.next.next;
        return true;
    }

    // Sort students by marks descending using Bubble Sort
    public void sortByMarksDescending() {
        if (head == null || head.next == null) {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            Student current = head;

            while (current.next != null) {
                if (current.marks < current.next.marks) {
                    // Swap data
                    String tempId = current.id;
                    String tempName = current.name;
                    double tempMarks = current.marks;
                    String tempRank = current.rank;

                    current.id = current.next.id;
                    current.name = current.next.name;
                    current.marks = current.next.marks;
                    current.rank = current.next.rank;

                    current.next.id = tempId;
                    current.next.name = tempName;
                    current.next.marks = tempMarks;
                    current.next.rank = tempRank;

                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);

        System.out.println("Students sorted by marks in descending order successfully.");
    }

    // Count students
    public int countStudents() {
        int count = 0;
        Student temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}

public class Main{
    static Scanner scanner = new Scanner(System.in);
    static StudentLinkedList studentList = new StudentLinkedList();

    public static void main(String[] args) {
        int choice;

        do {
            showMenu();
            choice = getValidInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    editStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    sortStudents();
                    break;
                case 7:
                    System.out.println("Exiting program... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please choose from 1 to 7.");
            }
        } while (choice != 7);
    }

    public static void showMenu() {
        System.out.println("\n========== STUDENT MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Edit Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Sort Students by Marks (Descending)");
        System.out.println("7. Exit");
        System.out.println("===============================================");
    }

    public static void addStudent() {
        System.out.println("\n--- Add Student ---");

        String id = getNonEmptyString("Enter Student ID: ");
        String name = getNonEmptyString("Enter Student Name: ");
        double marks = getValidMarks("Enter Student Marks (0 - 10): ");

        boolean added = studentList.addStudent(id, name, marks);

        if (added) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Error: Student ID already exists.");
        }
    }

    public static void displayStudents() {
        studentList.displayStudents();
        System.out.println("Total students: " + studentList.countStudents());
    }

    public static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        String id = getNonEmptyString("Enter Student ID to search: ");

        Student student = studentList.searchById(id);

        if (student != null) {
            System.out.println("\nStudent found:");
            System.out.printf("%-10s %-25s %-10s %-15s\n", "ID", "Name", "Marks", "Rank");
            System.out.println("--------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-10.2f %-15s\n",
                    student.id, student.name, student.marks, student.rank);
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void editStudent() {
        System.out.println("\n--- Edit Student ---");
        String id = getNonEmptyString("Enter Student ID to edit: ");

        Student student = studentList.searchById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String newName = getNonEmptyString("Enter new name: ");
        double newMarks = getValidMarks("Enter new marks (0 - 10): ");

        boolean updated = studentList.editStudent(id, newName, newMarks);

        if (updated) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Error updating student.");
        }
    }

    public static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        String id = getNonEmptyString("Enter Student ID to delete: ");

        boolean deleted = studentList.deleteStudent(id);

        if (deleted) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void sortStudents() {
        System.out.println("\n--- Sort Students by Marks ---");
        studentList.sortByMarksDescending();
    }

    // Input validation methods
    public static String getNonEmptyString(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static double getValidMarks(String message) {
        double marks;
        while (true) {
            try {
                System.out.print(message);
                marks = Double.parseDouble(scanner.nextLine());

                if (marks < 0 || marks > 10) {
                    System.out.println("Error: Marks must be between 0 and 10.");
                } else {
                    return marks;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    public static int getValidInt(String message) {
        int number;
        while (true) {
            try {
                System.out.print(message);
                number = Integer.parseInt(scanner.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }
}