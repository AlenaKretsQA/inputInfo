package com.expeditors.exercise;

import lombok.*;
@Data
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public boolean ageEligible(int age) {
        return (this.age > age);
    }

    @Override
    public String toString() {
        return "FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", Age=" + age ;
    }
}