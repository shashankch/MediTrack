
package com.health.meditrack.entity;

public abstract class Person extends MedicalEntity{

    private String name;
    private int age;

    public Person(String id,String name,int age){
        super(id);
        this.name=name;
        this.age=age;
    }

    public String getName(){return name;}
    public int getAge(){return age;}

    public void setName(String n){name=n;}
    public void setAge(int a){age=a;}
}
