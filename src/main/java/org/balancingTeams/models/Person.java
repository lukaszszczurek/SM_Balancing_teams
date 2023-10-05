package org.balancingTeams.models;

public class Person {
    private String name;
    private Integer rate;

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
