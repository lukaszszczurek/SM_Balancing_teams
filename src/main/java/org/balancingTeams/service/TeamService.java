package org.balancingTeams.service;


import org.balancingTeams.models.Person;
import org.balancingTeams.models.Team;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TeamService {



    public List<List<Person>> getBalancedTeams(List<Person> people, int groupCount) {


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
        }

        for (int i = 0; i < validGroup; i++) {
           teams.get(i).addMember(people.get(i));

        }

        int index = groupCount;

        var ListOfConflictsTeams = new ArrayList<Team>();



        collectingTeamProcess(index, people, teams, groupCount, average, validGroup, ListOfConflictsTeams);


        List<List<Person>> result = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {

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
                               int groupCount, double average,
                               int validGroup, List<Team> ListOfConflictsTeams)

    {
        var DeclinedPersons = new ArrayList<Person>();
        var ListUsed = new ArrayList<Team>();


        while (index < people.size()) {

            for (int i = 0; i < groupCount; i++) {
                if (index >= people.size())
                    break;
                var candidateMember = people.get(index);

                var teamFirstOfAll = teams.stream().filter(t -> !ListUsed.contains(t)).min(Comparator.
                        comparing(t -> Math.abs((t.getCurrentSum() + candidateMember.getRate()) / (t.getMembers().size() + 1) - average))).get();

                var averageDifference = Math.abs((teamFirstOfAll.getCurrentSum() + candidateMember.getRate()) / (teamFirstOfAll.getMembers().size() + 1) - average);
                var team = teams.stream().filter(t -> !ListUsed.contains(t))
                        .filter(t -> Math.abs((t.getCurrentSum() + candidateMember.getRate()) / (t.getMembers().size() + 1) - average) == averageDifference).toList();
                if (team.size() > 1) {
                    for (Team t : team) {
                      {
                            if(!ListOfConflictsTeams.contains(t))
                            {
                                ListOfConflictsTeams.add(t);
                            }
                            if(!DeclinedPersons.contains(candidateMember))
                          {
                                DeclinedPersons.add(candidateMember);

                          }

                        }
                    }
                } else if (team.size() == 1) {
                    team.get(0).addMember(candidateMember);
                    ListUsed.add(team.get(0));
                    if(ListOfConflictsTeams.contains(team.get(0)))
                    {
                        ListOfConflictsTeams.remove(team.get(0));
                    }
                } else {
                    System.out.println("ww");
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
}
