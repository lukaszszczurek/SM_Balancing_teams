package org.balancingTeams;
import org.balancingTeams.models.Person;
import org.balancingTeams.service.TeamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>(){
            {
                add(new Person("Johnny", 8));
                add(new Person("Robbie", 5));
                add(new Person("Juliet", 3));
                add(new Person("Scarlet", 5));
                add(new Person("Jude",9));
                add(new Person("Deborah",6));
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
        List<Integer> test2 = new ArrayList<>(){
            {
                add(1);
                add(3);
                add(7);
                add(9);
            }
        };

        List<Person> test3 = new ArrayList<>(){
            {
                add(new Person("Johnny", 8));
                add(new Person("Robbie", 5));
                add(new Person("Juliet", 3));
                add(new Person("Scarlet", 5));
                add(new Person("Jude",9));
                add(new Person("Deborah",6));
            }
        };

        TeamService teamService = new TeamService();
       var a = teamService.GetBalancedTeams(test3,3);
       var averageValueInTeam = new ArrayList<Double>();

       for (int i = 1; i <= a.size(); i++) {

           var persons = a.get(i-1);

           List<String> names = new ArrayList<>();

            // create forEach loop for persons
           for (Person person : persons) {
               names.add(person.getName());

           }
            System.out.println("Team " + i + ": " + names + " " + teamService.GetAverageInTeam(persons));


       }
        var standartDaviation =  teamService.GetStandartDeviation(a);
        System.out.println(standartDaviation);



    }
}
