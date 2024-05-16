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
                //cek student add 
                    addStudent(buffer);
                    break;
                case "dorm-add":
                    addDorm(buffer);
                    break;
                case "assign":
                    assignStudentToDorm(buffer);
                    break;
                case "display-all":
                    displayAllDorms();
                    break;
            }
        }

        entityManager.close();
        factory.close();
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
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

   
    public static void addStudent(String[] buffer) {
        entityManager.getTransaction().begin();
        Student students = new Student(buffer[0], buffer[1], buffer[2], buffer[3]);
        entityManager.persist(students);
        entityManager.flush();
        entityManager.getTransaction().commit();
        }
    
    public static void assignStudentToDorm(String[] buffer) {
        entityManager.getTransaction().begin();
        Student tempStudent;
        if((tempStudent = entityManager.find(Student.class, buffer[0])) == null){
            Student student = new Student(buffer[0], buffer[1], buffer[2], buffer[3]);
            entityManager.persist(student);
        }else{
            if(!tempStudent.getId().equals(buffer[0])){
                Student student = new Student(buffer[0], buffer[1], buffer[2], buffer[3]);
                entityManager.persist(student);
            }
        }
        entityManager.getTransaction().commit();
       
    }

    public static void displayAllDorms() {
        String dormSql = "SELECT d FROM Dorm d ORDER BY d.name";

        List<Dorm> dorms = entityManager.createQuery(dormSql, Dorm.class).getResultList();
        for (Dorm dorm : dorms) {
            System.out.println(dorm);
            
            List<Student> students = dorm.getStudents();
            students.sort(Comparator.comparing(Student::getName));

            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}
