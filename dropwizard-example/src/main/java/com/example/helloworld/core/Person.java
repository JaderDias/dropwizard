package com.example.helloworld.core;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    private long id;

    private String fullName;

    private String jobTitle;

    @Min(value = 0)
    @Max(value = 9999)
    private int yearBorn;

    public Person() {
    }

    public Person(long id, String fullName, String jobTitle, int yearBorn) {
        this.id = id;
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.yearBorn = yearBorn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Person)) {
            return false;
        }

        Person person = (Person) o;

        return id == person.id &&
                yearBorn == person.yearBorn &&
                Objects.equals(fullName, person.fullName) &&
                Objects.equals(jobTitle, person.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, jobTitle, yearBorn);
    }
}
