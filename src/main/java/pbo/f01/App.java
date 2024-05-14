package pbo.f01;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pbo.f01.model.Dorm;
import pbo.f01.model.Student;

public class App {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    static final String DELIMINATOR = "#";
    static final String STOP = "---";

    public static void main(String[] _args) {
        factory = Persistence.createEntityManagerFactory("dormy_pu");
        entityManager = factory.createEntityManager();
        
        Scanner scan = new Scanner(System.in);
        String stdin;
        String[] buffer;

        while (true) {
            stdin = scan.nextLine();
            if (stdin.equals(STOP))
                break;
            buffer = stdin.split(DELIMINATOR);
            String order = buffer[0];
            buffer = java.util.Arrays.copyOfRange(buffer, 1, buffer.length);

            switch (order) {
                case "student-add":
                    Student student = new Student(buffer[0], buffer[1], buffer[2], buffer[3]);
                    entityManager.getTransaction().begin();
                    entityManager.persist(student);
                    entityManager.getTransaction().commit();
                    break;

                case "dorm-add":
                    Dorm dorm = new Dorm(buffer[0], Short.parseShort(buffer[1]), buffer[2]);
                    entityManager.getTransaction().begin();
                    entityManager.persist(dorm);
                    entityManager.getTransaction().commit();
                    break;
                
                case "assign":
                    assignStudentToDorm(buffer[0], buffer[1]);
                    break;

                case "display-all":
                    displayAll();
                    break;
            }
        }

        entityManager.close();
        scan.close();
    }

    private static void assignStudentToDorm(String studentId, String dormName) {
        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class, studentId);
        Dorm dorm = entityManager.find(Dorm.class, dormName);

        if (student != null && dorm != null && dorm.getCapacity() > dorm.getStudents().size() && student.getGender().equals(dorm.getGender())) {
            student.setDorm(dorm);
            dorm.addStudent(student);
            entityManager.persist(student);
            entityManager.persist(dorm);
        }

        entityManager.getTransaction().commit();
    }

    private static void displayAll() {
        List<Dorm> dorms = entityManager.createQuery("SELECT d FROM Dorm d ORDER BY d.name", Dorm.class).getResultList();
        
        for (Dorm dorm : dorms) {
            System.out.println(dorm);
            List<Student> students = dorm.getStudents();
            students.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}    

