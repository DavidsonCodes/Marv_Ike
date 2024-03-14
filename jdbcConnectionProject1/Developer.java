package org.example.jdbcConnectionProject1;

public class Developer {
    private int id;
    private String name;
    private int age;
    private String location;
    private String skill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Developer(int id, String name, int age, String location, String skill) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
        this.skill = skill;
    }
}
