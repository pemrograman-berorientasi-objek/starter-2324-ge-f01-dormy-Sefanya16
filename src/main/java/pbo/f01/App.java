package pbo.f01;

import java.util.*;
import javax.persistence.*;

import pbo.f01.model.Dorm;
import pbo.f01.model.Student;

public class App {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    static final String DELIMITER = "#";
    static final String STOP = "---";

    public static void main(String[] _args) {
        factory = Persistence.createEntityManagerFactory("dormy_pu");
        entityManager = factory.createEntityManager();

        // Clean up tables if they contain buffer
        cleanUpTables();

        Scanner scan = new Scanner(System.in);
        String stdin;
        String[] buffer;

        while (true) {
            stdin = scan.nextLine();
            if (stdin.equals(STOP))
                break;
            buffer = stdin.split(DELIMITER);
            String order = buffer[0];
            buffer = Arrays.copyOfRange(buffer, 1, buffer.length);

            switch (order) {
                case "student-add":
                    addStudent(buffer);
                    break;
                case "dorm-add":
                    addDorm(buffer);
                    break;
                case "assign":
                    assignStudentToDorm(stdin);
                    break;
                case "display-all":
                    displayAllDorms();
                    break;
            }
        }

        entityManager.close();
        scan.close();
    }

    public static void cleanUpTables() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Student").executeUpdate();
        entityManager.createQuery("DELETE FROM Dorm").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public static void addDorm(String[] buffer) {
        entityManager.getTransaction().begin();
        Dorm dorm = new Dorm(buffer[0], Short.parseShort(buffer[1]), buffer[2]);
        entityManager.persist(dorm);
        entityManager.getTransaction().commit();
    }

    public static void addStudent(String[] buffer) {

        String studentId = buffer[0];

        entityManager.getTransaction().begin();
        // Check if student with the same ID already exists
        if (entityManager.find(Student.class, studentId) == null) {
            String studentName = buffer[1];
            String entranceYear = buffer[2];
            String gender = buffer[3];
            Student student = new Student(studentId, studentName, entranceYear, gender);
            entityManager.persist(student);
            entityManager.getTransaction().commit();
        } else {
            // System.out.println("Student with ID " + studentId + " already exists.");
        }

    }

    // public static void assignStudentToDorm(String[] buffer) {
    // if (buffer.length == 4) { // Fixed the length check from 'parts' to 'buffer'
    // String studentId = buffer[1];
    // String dormName = buffer[2];
    // Student student = entityManager.find(Student.class, studentId); // Changed
    // 'em' to 'entityManager'
    // Dorm dorm = entityManager.find(Dorm.class, dormName);

    // System.out.println(student);
    // System.out.println(dorm);
    // if (student != null && dorm != null &&
    // student.getGender().equals(dorm.getGender()) && student.getDorms().isEmpty())
    // {
    // if (dorm.getStudentsCount() < dorm.getCapacity()) {
    // student.getDorms().add(dorm);
    // dorm.getStudents().add(student);
    // dorm.setStudentsCount(dorm.getStudentsCount() + 1);
    // entityManager.getTransaction().begin();
    // entityManager.merge(student);
    // entityManager.merge(dorm);
    // entityManager.getTransaction().commit();
    // System.out.println("fdsfsd");
    // } else {
    // // System.out.println("Dorm " + dormName + " is full.");
    // }
    // } else {
    // // System.out.println("Student " + studentId + " cannot be assigned to dorm "
    // + dormName + ".");
    // }
    // }
    // }

    public static void assignStudentToDorm(String param) {
        String[] buffer = param.split("#");
        String studentId = buffer[1];
        String dormName = buffer[2];

        Student student = entityManager.find(Student.class, studentId);
        Dorm dorm = entityManager.find(Dorm.class, dormName);

        String stdGender = student.getGender();
        String drmGender = dorm.getGender();

        if (student != null && dorm != null ) {
            if (dorm.getStudentsCount() < dorm.getCapacity()) {
                if (stdGender.equals(drmGender)) {
                    student.getDorms().add(dorm);
                    dorm.getStudents().add(student);
                    dorm.setStudentsCount(dorm.getStudentsCount() + 1);
                    entityManager.getTransaction().begin();
                    entityManager.merge(student);
                    entityManager.merge(dorm);
                    entityManager.getTransaction().commit();
                }
            }
        }
    }

    public static void displayAllDorms() {
        String dormSql = "SELECT d FROM Dorm d ORDER BY d.name";
        List<Dorm> dorms = entityManager.createQuery(dormSql, Dorm.class).getResultList();
        for (Dorm dorm : dorms) {
            System.out.println(dorm);
            List<Student> students = dorm.getStudents();
            students.sort(Comparator.comparing(Student::getName));
            for (Student student : students) {
                System.out.println(student.toString());
            }
        }
    }

}
