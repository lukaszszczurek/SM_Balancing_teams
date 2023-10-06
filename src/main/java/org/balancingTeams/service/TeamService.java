package org.balancingTeams.service;


import org.balancingTeams.exception.NoTeamAvailableException;
import org.balancingTeams.models.Person;
import org.balancingTeams.models.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.balancingTeams.exception.NoTeamAvailableException.DEFAULT_MESSAGE;

public class TeamService {


    public List<List<Person>> getBalancedTeams(List<Person> people, int groupCount) throws NoTeamAvailableException {


        double average = people.stream()
                .mapToDouble(Person::getRate)
                .sum() / people.size();
        people.sort(Comparator.comparing(Person::getRate));

        people.forEach(person -> {
            System.out.println(person.getName() + " " + person.getRate());
        });
        System.out.println("--------------------");

        // scenario if group is bigger than people count
        int validGroup = Math.min(groupCount, people.size());

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < validGroup; i++) {
            teams.add(new Team());
            teams.get(i).addMember(people.get(i));
        }

        var ListOfConflictsTeams = new ArrayList<Team>();

        collectingTeamProcess(groupCount, people, teams, groupCount, average, validGroup, ListOfConflictsTeams);


        List<List<Person>> result = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {

            if (i >= validGroup) {
                result.add(null);
            } else {
                result.add(teams.get(i).getMembers());
            }
        }

        for (List<Person> personList : result) {
            for (Person person : personList) {
                System.out.println(person.getName());
            }
            System.out.println("-----------");
        }
        return result;
    }

    public double GetAverageInTeam(List<Person> people) {
        return people.stream().mapToDouble(Person::getRate).sum() / people.size();
    }

    public double getStandardDeviation(List<List<Person>> people) {

        List<Double> averageInTeams = new ArrayList<>();

        for (List<Person> person : people) {
            averageInTeams.add(GetAverageInTeam(person));
        }

        double sumAllElements = 0;

        for (Double inTeam : averageInTeams) {
            sumAllElements += inTeam;
        }

        double average = sumAllElements / averageInTeams.size();
        double sumOfSquares = 0;

        for (Double averageInTeam : averageInTeams) {
            sumOfSquares += Math.pow(averageInTeam - average, 2);
        }
        double variance = sumOfSquares / averageInTeams.size();

        return Math.round(Math.sqrt(variance) * 100) / 100.0;
    }

    void collectingTeamProcess(int index, List<Person> people, List<Team> teams,
                               int groupCount, double average, int validGroup, List<Team> ListOfConflictsTeams) throws NoTeamAvailableException
    {
        var DeclinedPersons = new ArrayList<Person>();
        var ListUsed = new ArrayList<Team>();


        while (index < people.size()) {

            for (int i = 0; i < groupCount; i++) {
                if (index >= people.size())
                    break;
                var candidateMember = people.get(index);

                var teamFirstOfAll = getFirstValidTeam(teams, ListUsed, candidateMember, average);

                var team = getAllValidTeams(teams, ListUsed, candidateMember, teamFirstOfAll, average);

                if (team.size() > 1) {
                    for (Team t : team) {
                        {
                            if (!ListOfConflictsTeams.contains(t)) {
                                ListOfConflictsTeams.add(t);
                            }
                            if (!DeclinedPersons.contains(candidateMember)) {
                                DeclinedPersons.add(candidateMember);
                            }

                        }
                    }
                } else if (team.size() == 1) {
                    team.get(0).addMember(candidateMember);
                    ListUsed.add(team.get(0));
                    ListOfConflictsTeams.remove(team.get(0));
                } else {
                    throw new NoTeamAvailableException(DEFAULT_MESSAGE);
                }

                index++;
            }
        }

        if (!ListOfConflictsTeams.isEmpty()) {
            double expectedMemberOfTeam = Math.ceil((double) people.size() / validGroup);

            var availableTeams = teams.stream().filter(t -> t.getMembers().size() < expectedMemberOfTeam).toList();
            for (Person person : DeclinedPersons) {
                var chosenTeam = availableTeams.stream()
                        .min(Comparator.comparing((t -> Math.abs((t.getCurrentSum() + person.getRate()) / (teams.size() + 1)) - average)))
                        .get();
                teams.get(teams.indexOf(chosenTeam)).addMember(person);
            }
        }
    }


    private Team getFirstValidTeam(List<Team> teams, ArrayList<Team> listUsed, Person candidateMember, double average) {
        var optionalTeam = teams.stream().filter(t -> !listUsed.contains(t)).min(Comparator.
                comparing(t -> Math.abs((t.getCurrentSum() + candidateMember.getRate()) / (t.getMembers().size() + 1) - average)));
        if (optionalTeam.isEmpty()) {
            throw new NoTeamAvailableException(DEFAULT_MESSAGE);
        }
        return optionalTeam.get();
    }

    private List<Team> getAllValidTeams(List<Team> teams, List<Team> ListUsed, Person candidateMember, Team teamFirstOfAll, double average) {
        var averageDifference = Math.abs((teamFirstOfAll.getCurrentSum() + candidateMember.getRate()) / (teamFirstOfAll.getMembers().size() + 1) - average);
        return teams.stream().filter(t -> !ListUsed.contains(t))
                .filter(t -> Math.abs((t.getCurrentSum() + candidateMember.getRate()) / (t.getMembers().size() + 1) - average) == averageDifference).toList();
    }
}
