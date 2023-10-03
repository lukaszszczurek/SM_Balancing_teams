package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Person {
    String name;
    int rate;

    public Person(String name, int rate) {
        this.name = name;
        this.rate = rate;
    }
}
class Team
{
    public List<Integer> members;
    public int currentSum;

    public Team()
    {
        members = new ArrayList<>();
        currentSum = 0;
    }

    public void addMember(int member)
    {
        members.add(member);
        currentSum += member;
    }

    public int getCurrentSum()
    {
        return currentSum;
    }

    public List<Integer> getMembers()
    {
        return members;
    }
}

public class Main {

    void GetBalancedTeams(List<Integer> people, int groups) {
        int average = people.stream().mapToInt(Integer::intValue).sum() / people.size();
        people.sort(Comparator.naturalOrder());

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < groups; i++)
        {
            teams.add(new Team());
        }

        // add first number to first team and so on until we have one member in each team
        for (int i = 0; i < groups; i++)
        {
            teams.get(i).addMember(people.get(i));
        }

       int index = groups;
        while (index < people.size())
        {
            var ListUsed = new ArrayList<Team>();
            for(int i=0; i< groups; i++)
            {

                var candidateMember = people.get(index);

                var team = teams.stream().filter(t -> !ListUsed.contains(t)).min(Comparator.
                        comparing(t -> Math.abs((double) Math.abs((t.getCurrentSum() + candidateMember)/ (t.getMembers().size() + 1)) - average))).get();

                team.addMember(candidateMember);
                ListUsed.add(team);

                index ++;
            }
        }

        for (int i = 0; i < groups; i++) {
            System.out.println("Team " + i + ": " + teams.get(i).getMembers());
        }
    }

    public static void main(String[] args) {
        List<Integer> people = new ArrayList<>(){
{
                add(3);
                add(5);
                add(5);
                add(6);
                add(8);
                add(9);
            }
        };


        List<Integer> test1 = new ArrayList<>(){
            {
                add(1);
                add(3);
                add(7);
                add(9);
            }
        };

        Main main = new Main();
        main.GetBalancedTeams(test1, 2);

    }

}
