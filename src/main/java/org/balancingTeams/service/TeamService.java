package org.balancingTeams.service;

import org.balancingTeams.exception.NoTeamAvailableException;
import org.balancingTeams.models.Person;
import org.balancingTeams.models.Team;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TeamService {

    public List<Team> getBalancedTeams(List<Person> listOfPerson, int groupCount) throws NoTeamAvailableException {

        if(listOfPerson.size() < groupCount)
        {
            throw new NoTeamAvailableException("Not enough people to create teams");
        }

        listOfPerson.sort(Comparator.comparing(Person::getRate));
        var listOfAllPermutation = getAllPermutationsOfList(listOfPerson);
        var resultTeams = new ArrayList<Team>();

        double bestStandardDeviation = Double.MAX_VALUE;

        for( List<Person> people : listOfAllPermutation)
        {
            List<Team> teams = new ArrayList<>();

            for (int i = 0; i < groupCount; i++) {
                teams.add(new Team());
            }
               for (Person person : people)
               {
                   int modulo = people.indexOf(person) % groupCount;
                   teams.get(modulo).addMember(person);
               }

                var standardDeviation = getStandardDeviation(teams);

               if(standardDeviation < bestStandardDeviation)
               {
                     bestStandardDeviation = standardDeviation;
                     resultTeams.clear();
                     resultTeams.addAll(teams);
               }
        }

        return resultTeams;
    }

    public double GetAverageInTeam(Team team) {
        return team.getCurrentSum() / team.getMembers().size();
    }

    public double getStandardDeviation(List<Team> people) {

        List<Double> averageInTeams = new ArrayList<>();

        double sum = 0.0;

        for (Team team : people) {
            averageInTeams.add(GetAverageInTeam(team));
            sum += GetAverageInTeam(team);
        }

        double average = sum / people.size();
        double sumOfSquares = 0;

        for (Double averageInTeam : averageInTeams) {
            sumOfSquares += Math.pow(averageInTeam - average, 2);
        }
        double variance = sumOfSquares / averageInTeams.size();

        return Math.round(Math.sqrt(variance) * 100) / 100.0;
    }

    public static List<List<Person>> getAllPermutationsOfList(List<Person> people) throws NoTeamAvailableException {

        List<List<Person>> permutations = new ArrayList<>();
        generatePermutations(permutations, new ArrayList<>(), people);
        return permutations;
    }

    private static void generatePermutations(List<List<Person>> permutations, ArrayList<Person> currentPermutation, List<Person> inputList) {
        if (inputList.isEmpty()) {
            permutations.add(new ArrayList<>(currentPermutation));
        } else {
            for (int i = 0; i < inputList.size(); i++) {
                Person element = inputList.get(i);
                currentPermutation.add(element);
                List<Person> newRemaining = new ArrayList<>(inputList);
                newRemaining.remove(i);
                generatePermutations(permutations, currentPermutation, newRemaining);
                currentPermutation.remove(currentPermutation.size() - 1);
            }
        }
    }

    public void printResult(List<Person> people, int groupCount) {

        var teams = getBalancedTeams(people, groupCount);
        var standardDeviation = getStandardDeviation(teams);


        int i = 1;
        for (Team team : teams) {
            System.out.print("Team no " + i + " has " + team.getMembers().size() + " players ");
            System.out.print("(");
            i++;
            for (Person person : team.getMembers()) {

                if(team.getMembers().indexOf(person) == team.getMembers().size() - 1)
                    System.out.print(person.getName());
                else
                    System.out.print(person.getName() + ", ");
                 }

            System.out.print(").");
            System.out.println("Average: " + GetAverageInTeam(team));
        }
        System.out.println("Teams rate standard deviation: " + standardDeviation);
    }
}
