package org.balancingTeams.models;

public class Person {
    private final String name;
    private final Integer rate;

    public Person(String name, Integer rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public Integer getRate() {
        return rate;
    }
}
