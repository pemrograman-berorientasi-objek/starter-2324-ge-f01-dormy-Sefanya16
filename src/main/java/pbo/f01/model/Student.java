package pbo.f01.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name="id", nullable = false,length = 255)
    private String id;
    @Column(name="name", nullable = false,length = 255)
    private String name;
    @Column(name="EntranceYear", nullable = false,length = 255)
    private String EntranceYear;
    @Column(name="gender", nullable = false,length = 255)
    private String gender;

    @ManyToMany(targetEntity = Dorm.class, cascade = CascadeType.ALL)
    @JoinTable(
        name = "student_dorm",
        joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "dorm_name", referencedColumnName = "name")
    )
    private List<Dorm> dorms = new ArrayList<>();

    public Student(){

     }

    public Student(String id, String name, String EntranceYear,String gender){
        this.id=id;
        this.name=name;
        this.EntranceYear=EntranceYear;
        this.gender=gender;

    }

    public Student(String id, String name, String EntranceYear, String gender,List<Dorm> dorms){
        this.id=id;
        this.name=name;
        this.EntranceYear= EntranceYear;
        this.gender=gender;
        this.dorms=dorms;
    }
   
    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    public String getGender(){
        return this.gender;
    }

    public String getEntranceYear(){
        return this.EntranceYear;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setEntranceYear(String EntranceYear){
        this.EntranceYear= EntranceYear;
    }


    public List<Dorm> getDorms(){
        return this.dorms;
    }

    public void setDorms(List<Dorm> dorms){

        this.dorms= dorms;
    }

    @Override
    public String toString(){
        return id +"|"+ name + "|" + EntranceYear ;
    }    
}