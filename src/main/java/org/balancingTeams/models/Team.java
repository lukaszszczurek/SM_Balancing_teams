package org.balancingTeams.models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final List<Person> members;
    private Double currentSum;

    public Team() {
        members = new ArrayList<>();
        currentSum = 0.0;
    }

    public void addMember(Person member) {
        members.add(member);
        currentSum += member.getRate();
    }

    public double getCurrentSum() {
        return currentSum;
    }

    public List<Person> getMembers() {
        return members;
    }
}
