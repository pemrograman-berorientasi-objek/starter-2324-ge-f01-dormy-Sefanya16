package pbo.f01.model;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "Dorm")
public class Dorm {
    @Id
    @Column(name="name", nullable = false,length = 255)
    private String name;
    @Column(name="capasity", nullable = false,length = 255)
    private short capacity;
    @Column(name="gender", nullable = false,length = 255)
    private String gender;

    @OneToMany(mappedBy = "dorm")
    private List<Student> students = new ArrayList<>();

    public Dorm(){

    }

    public Dorm(String name, short capacity, String gender){
        this.name=name;
        this.capacity=capacity;
        this.gender=gender;
    }
   
    public String getName(){
        return this.name;
    }

    public short getCapacity(){
        return this.capacity;
    }

    public String getGender(){
        return this.gender;
    }

    public void setCapacity(short capacity){
        this.capacity = capacity;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public List<Student> getStudents(){
        return this.students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    @Override
    public String toString(){
        return name +"|"+ gender + "|" + capacity + "|" + students.size();
    }






    
}
