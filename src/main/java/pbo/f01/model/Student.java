package pbo.f01.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Students")
public class Student {
 
    @Id
    @Column(name="Id", nullable = false,length = 255)
    private String Id;
    @Column(name="name", nullable = false,length = 255)
    private String name;
    @Column(name="EntranceYear", nullable = false,length = 255)
    private String EntranceYear;
    @Column(name="gender", nullable = false,length = 255)
    private String gender;

    @ManyToOne
    private Dorm dorm;

    public Student(){
        this.Id="";
        this.name="";
        this.EntranceYear="";
        this.gender="";

    }

    public Student(String Id, String name, String EntranceYear, String gender){
        this.Id=Id;
        this.name=name;
        this.EntranceYear= EntranceYear;
        this.gender=gender;
    }
   
    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.Id;
    }

    public String getGender(){
        return this.gender;
    }

    public String getEntranceYear(){
        return this.EntranceYear;
    }

    public void setId(String Id){
        this.Id = Id;
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


    public Dorm getDorm(){
        return this.dorm;
    }

    public void setDorm(Dorm dorm){

        this.dorm= dorm;
    }

    @Override
    public String toString(){
        return Id +"|"+ name + "|" + EntranceYear;
    }






    
}

    
