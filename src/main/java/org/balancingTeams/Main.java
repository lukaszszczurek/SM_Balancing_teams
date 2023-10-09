package org.balancingTeams;

import org.balancingTeams.models.Person;
import org.balancingTeams.service.TeamService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
   static List<Person> people = new ArrayList<>(
            Arrays.asList(
                    new Person("Johnny", 8),
                    new Person("Robbie", 5),
                    new Person("Juliet", 3),
                    new Person("Scarlet", 5),
                    new Person("Jude",9),
                    new Person("Deborah",6)
            ));

    public static void main(String[] args) {
        TeamService teamService = new TeamService();
        teamService.printResult(people, 3);


    }
}
