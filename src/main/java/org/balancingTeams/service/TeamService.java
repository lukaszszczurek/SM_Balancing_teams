package org.balancingTeams.service;

import org.balancingTeams.models.Person;
import org.balancingTeams.models.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TeamService {

    public List<List<Person>> GetBalancedTeams(List<Person> people, int groups) {
        double average = people.stream().mapToDouble(Person::getRate).sum() / people.size();
        System.out.println("AVENGERS : " + average);
        people.sort(Comparator.comparing(Person::getRate));

        for (int i = 0; i < people.size(); i++) {
            System.out.println(people.get(i).getName() + " " + people.get(i).getRate());
        }
        System.out.println("--------------------");


        // scenario if group is bigger than people
        int validGroup = Math.min(groups, people.size());

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < validGroup; i++) {
            teams.add(new Team());
        }

        for (int i = 0; i < validGroup; i++) {
            teams.get(i).addMember(people.get(i));
        }

        int index = groups;
        while (index < people.size()) {
            var ListUsed = new ArrayList<Team>();
            for (int i = 0; i < groups; i++) {
                if (index >= people.size())
                    break;
                var candidateMember = people.get(index);

                var team = teams.stream().filter(t -> !ListUsed.contains(t)).min(Comparator.
                        comparing(t -> Math.abs(( (double) t.getCurrentSum() + candidateMember.getRate()) / (t.getMembers().size() + 1) - average))).get();

                team.addMember(candidateMember);
                ListUsed.add(team);

                index++;
            }
        }

        List<List<Person>> result = new ArrayList<>();

        for (int i = 0; i < groups; i++) {

            if (i >= validGroup) {
                result.add(null);
            } else {
                result.add(teams.get(i).getMembers());
            }
        }

        for (int i = 0; i < result.size(); i++) {
           for (int j = 0; j < result.get(i).size(); j++) {
               System.out.println(result.get(i).get(j).getName());
           }
            System.out.println("-----------");
        }
        return result;
    }

    public double GetAverageInTeam(List<Person> people) {
        return people.stream().mapToDouble(Person::getRate).sum() / people.size();
    }
    public double GetStandartDeviation(List<List<Person>> people) {

        List<Double> averageInTeams = new ArrayList<>();

        for (List<Person> person : people) {
            averageInTeams.add(GetAverageInTeam(person));
        }

        double sumAllElements = 0;

        for (Double inTeam : averageInTeams) {
            sumAllElements += inTeam;
        }
        System.out.println("SUM: " + sumAllElements);

        double average = sumAllElements / averageInTeams.size();
        System.out.println("AVERAGE: " + average);
        double sumOfSquares = 0;

        for (Double averageInTeam : averageInTeams) {
            System.out.println("AVERAGE IN TEAM: " + averageInTeam);
            sumOfSquares += Math.pow(averageInTeam - average, 2);
            System.out.println("SQUARE" + averageInTeams.indexOf(averageInTeam) + " : "   + sumOfSquares);
        }

        System.out.println("SUM OF SQUARES: " + sumOfSquares);

        System.out.println("AVERAGE IN TEAMS SIZE: " + averageInTeams.size());
        double variance = sumOfSquares / averageInTeams.size();

        System.out.println("VARIANCE: " + variance);

        return  Math.round(Math.sqrt(variance) * 100) / 100.0;
    }
}
