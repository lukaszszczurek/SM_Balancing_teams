package org.balancingTeams.models;

public class Person {
    private String name;
    private int rate;

    public Person(String name, int rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }
}
