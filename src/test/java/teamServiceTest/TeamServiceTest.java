package teamServiceTest;

import org.balancingTeams.models.Person;
import org.balancingTeams.service.TeamService;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.balancingTeams.service.TeamService.getAllPermutationsOfList;
import static org.junit.Assert.assertEquals;

public class TeamServiceTest {

    @Test
    public void testgetBalancedTeamsGivenTest() throws NoSuchFieldException {
        List<Person> poeple = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 8),
                        new Person("Robbie", 5),
                        new Person("Juliet", 3),
                        new Person("Scarlet", 5),
                        new Person("Jude",9),
                        new Person("Deborah",6)
                )
        );


        TeamService teamService = new TeamService();
        var result = teamService.getBalancedTeams(poeple,3);
        System.out.println(result);

        var standardDeviation = teamService.getStandardDeviation(result);
        double expected = 0.41;

        assertEquals(expected, standardDeviation, 0.01);

    }

    @Test
    public void getBalancedTeamsEqualNumberOfMembers () throws NoSuchFieldException {
        List<Person> testOneData = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 1),
                        new Person("Robbie", 3),
                        new Person("Juliet", 9),
                        new Person("Scarlet", 7)
                )
        );

        List<Person> testTwoData = new ArrayList<>(
                Arrays.asList(
                        new Person("Mariusz", 1),
                        new Person("Arjen", 5),
                        new Person("Robin", 9),
                        new Person("Robert", 7),
                        new Person("Arek", 3),
                        new Person("Kamil", 2)
                )
        );

        System.out.println("LIS: " + testOneData);

        TeamService teamService = new TeamService();
        var resultFirstCase = teamService.getBalancedTeams(testOneData,2);
        var resultSecondCase = teamService.getBalancedTeams(testTwoData,3);


        var standardDeviationForFirstData = teamService.getStandardDeviation(resultFirstCase);
        var standardDeviationForSecondData = teamService.getStandardDeviation(resultSecondCase);
        double expectedForFirstData = 0.0;
        double expectedForSecondData = 0.41;

        assertEquals(expectedForFirstData, standardDeviationForFirstData, 0.01);
        assertEquals(expectedForSecondData, standardDeviationForSecondData, 0.01);
    }

    @Test
    public void permutation()
    {
        List<Person> p = new ArrayList<>(
                Arrays.asList(
                        new Person("Jon",4),
                        new Person("Joggn",3),
                        new Person("AAA",1),
                        new Person("Alfred",3)
                )
        );

        var r = getAllPermutationsOfList(p);
        var inn = r.size();

        for(int i =0; i< inn; i++)
        {
            for(Person prr : r.get(i))
            {
                System.out.print(prr.getName() + " : " + prr.getRate() + ",");
            }
            System.out.println(" <=> ");

        }



        assertEquals(2,2);
    }
}