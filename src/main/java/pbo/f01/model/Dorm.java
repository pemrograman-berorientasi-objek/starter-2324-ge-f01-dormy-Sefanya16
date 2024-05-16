package pbo.f01.model;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "dorms")
public class Dorm {
    @Id
    @Column(name="name", nullable = false,length = 255)
    private String name;
    @Column(name="capacity", nullable = false,length = 255)
    private short capacity;
    @Column(name="gender", nullable = false,length = 255)
    private String gender;
    @Column(name="studentsCount", nullable = false)
    private int studentsCount;

    @OneToMany(mappedBy = "dorms")
    private List<Student> students = new ArrayList<>();

    public Dorm(){

    }

    public Dorm(String name, short capacity, String gender){
        this.name=name;
        this.capacity=capacity;
        this.gender=gender;
        this.studentsCount = 0;
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

    public int getStudentsCount(){
        return this.studentsCount;
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

    public void setStudentsCount(int studentsCount){
        this.studentsCount = studentsCount;
    }

    public List<Student> getStudents(){
        return this.students;
    }

    public void setStudents(List<Student> students){
        this.students = students;
        this.studentsCount = this.students.size();
    }

    @Override
    public String toString(){
        return name +"|"+ gender + "|" + capacity + "|" + studentsCount;

    }

}