package teamServiceTest;

import org.balancingTeams.exception.NoTeamAvailableException;
import org.balancingTeams.models.Person;
import org.balancingTeams.service.TeamService;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class TeamServiceTest {

    @Test
    public void testGetBalancedTeamThrowsNoTeamAvailableExceptionException() {
        List<Person> testDataOne = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 1),
                        new Person("Robbie", 3),
                        new Person("Juliet", 9),
                        new Person("Scarlet", 7)
                ));

        List<Person> testDataTwo = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 1),
                        new Person("Robbie", 3),
                        new Person("Waclaw", 9),
                        new Person("Scarlet", 7),
                        new Person("Juliet", 2)
                ));

        TeamService teamService = new TeamService();
        Assert.assertThrows(NoTeamAvailableException.class, () -> teamService.getBalancedTeams(testDataOne,5));
        Assert.assertThrows(NoTeamAvailableException.class, () -> teamService.getBalancedTeams(testDataTwo,6));
    }
    @Test
    public void testGetBalancedTeamsGivenTest() {
        List<Person> people = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 8),
                        new Person("Robbie", 5),
                        new Person("Juliet", 3),
                        new Person("Scarlet", 5),
                        new Person("Jude",9),
                        new Person("Deborah",6)
                ));

        TeamService teamService = new TeamService();
        var result = teamService.getBalancedTeams(people,3);
        var standardDeviation = teamService.getStandardDeviation(result);
        double expected = 0.41;



        assertEquals(expected, standardDeviation, 0.01);
        System.setOut(System.out);


    }

    @Test
    public void getBalancedTeamsEqualNumberOfMembers () {
        List<Person> testOneData = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 1),
                        new Person("Robbie", 3),
                        new Person("Juliet", 9),
                        new Person("Scarlet", 7)
                ));

        List<Person> testTwoData = new ArrayList<>(
                Arrays.asList(
                        new Person("Mariusz", 1),
                        new Person("Arjen", 5),
                        new Person("Robin", 9),
                        new Person("Robert", 7),
                        new Person("Arek", 3),
                        new Person("Kamil", 2)
                ));

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
    public void getBalancedTeamsDifferNumberOfMembers()
    {
        List<Person> testOneData = new ArrayList<>(
                Arrays.asList(
                        new Person("Johnny", 1),
                        new Person("Robbie", 3),
                        new Person("Juliet", 9)
                ));

        List<Person> testTwoData = new ArrayList<>(
                Arrays.asList(
                        new Person("Mariusz", 1),
                        new Person("Arjen", 5),
                        new Person("Robin", 9)
                ));

        List<Person> people = new ArrayList<>(
                Arrays.asList(
                        new Person("Ruben",1),
                        new Person("James",3),
                        new Person("Kevin",5),
                        new Person("Rui",7),
                        new Person("Marco",7),
                        new Person("Ricardo",9),
                        new Person("Robert",9)
                ));

        var teamService = new TeamService();

        var resultOne = teamService.getBalancedTeams(testOneData,2);
        var resultTwo = teamService.getBalancedTeams(testTwoData,2);
        var resultThree = teamService.getBalancedTeams(people,3);

        var standardDeviationOne = teamService.getStandardDeviation(resultOne);
        var standardDeviationTwo = teamService.getStandardDeviation(resultTwo);
        var standardDeviationThree = teamService.getStandardDeviation(resultThree);

        assertEquals(1.0, standardDeviationOne, 0.01);
        assertEquals(0.0, standardDeviationTwo, 0.01);
        assertEquals(0.16, standardDeviationThree, 0.01);
    }
}