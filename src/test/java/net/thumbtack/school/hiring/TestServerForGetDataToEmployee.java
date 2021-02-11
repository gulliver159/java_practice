package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.*;
import net.thumbtack.school.hiring.dto.request.users.*;
import net.thumbtack.school.hiring.dto.response.ads.*;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.ads.Vacancy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestServerForGetDataToEmployee {

    private final Gson gson = new Gson();
    private final Server server = new Server();
    private String tokenEmployee, tokenEmployer;

    @Before
    public void startServer() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "katya0200117", "847562196"
        );

        tokenEmployee = server.registerEmployee(gson.toJson(requestRegisterEmployee));
        tokenEmployer = server.registerEmployer(gson.toJson(requestRegisterEmployer));
    }

    @Test
    public void testGetExistRequirements() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        requirements1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("sql", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirements2);

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addVacancy(gson.toJson(addVacancyDtoRequest));

        Set<String> expectTitlesRequirements = new HashSet<>();
        expectTitlesRequirements.add("python");
        expectTitlesRequirements.add("java");
        expectTitlesRequirements.add("sql");

        Set<String> resultServerBeforeAddReq1 = gson.fromJson(server.getExistRequirements(), GetRequirementsDtoResponse.class).getRequirementsTitles();
        Set<String> resultServerBeforeAddReq2 = gson.fromJson(server.getExistSkills(), GetRequirementsDtoResponse.class).getRequirementsTitles();

        assertAll(
                () -> assertEquals(resultServerBeforeAddReq1, expectTitlesRequirements),
                () -> assertEquals(resultServerBeforeAddReq2, expectTitlesRequirements)
        );

        server.addRequirementInVacancy(gson.toJson(new AddOrChangeRequirementDtoRequest(
                tokenEmployer, "Programmer", new Requirement("c++", 3, false)
        )));
        server.addRequirementInProfile(gson.toJson(new AddOrChangeSkillDtoRequest(
                tokenEmployee, new Requirement("c#", 5)
        )));

        expectTitlesRequirements.add("c++");
        expectTitlesRequirements.add("c#");

        Set<String> resultServerAfterAddReq1 = gson.fromJson(server.getExistRequirements(), GetRequirementsDtoResponse.class).getRequirementsTitles();
        Set<String> resultServerAfterAddReq2 = gson.fromJson(server.getExistSkills(), GetRequirementsDtoResponse.class).getRequirementsTitles();

        assertAll(
                () -> assertEquals(resultServerAfterAddReq1, expectTitlesRequirements),
                () -> assertEquals(resultServerAfterAddReq2, expectTitlesRequirements)
        );
    }


    @Test
    public void testGetVacanciesWhereAllRequirementAccountLevel() {
        Set<Requirement> requirementsPass1 = new HashSet<>();
        requirementsPass1.add(new Requirement("python", 3, false));
        requirementsPass1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsPass1);

        Set<Requirement> requirementsNotPass1 = new HashSet<>();
        requirementsNotPass1.add(new Requirement("c++", 3, true));
        requirementsNotPass1.add(new Requirement("c#", 4, false));
        AddVacancyDtoRequest addVacancyNotPass1 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsNotPass1);

        Set<Requirement> requirementsEmployee = new HashSet<>();
        requirementsEmployee.add(new Requirement("sql", 3));
        requirementsEmployee.add(new Requirement("java", 5));
        requirementsEmployee.add(new Requirement("python", 4));
        requirementsEmployee.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirementsEmployee);

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addVacancy(gson.toJson(addVacancyPass1));
        server.addVacancy(gson.toJson(addVacancyNotPass1));

        Set<Vacancy> expectVacancies = new HashSet<>();
        expectVacancies.add(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult1 = server.getVacanciesWhereAllRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies1 = gson.fromJson(serverResult1, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies1);

        Set<Requirement> requirementsPass2 = new HashSet<>();
        requirementsPass2.add(new Requirement("sql", 3, false));
        requirementsPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirementsPass2);

        Set<Requirement> requirementsNotPass2 = new HashSet<>();
        requirementsNotPass2.add(new Requirement("sql", 5, true));
        requirementsNotPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyNotPass2 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 60000, requirementsNotPass2);

        server.addVacancy(gson.toJson(addVacancyPass2));
        server.addVacancy(gson.toJson(addVacancyNotPass2));

        expectVacancies.add(new Vacancy("Tester", 30000, requirementsPass2));

        String serverResult2 = server.getVacanciesWhereAllRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies2 = gson.fromJson(serverResult2, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies2);

        server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));
        expectVacancies.remove(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult3 = server.getVacanciesWhereAllRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies3 = gson.fromJson(serverResult3, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies3);
    }

    @Test
    public void testGetVacanciesWhereNecessaryRequirementAccountLevel() {
        Set<Requirement> requirementsPass1 = new HashSet<>();
        requirementsPass1.add(new Requirement("python", 5, false));
        requirementsPass1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsPass1);

        Set<Requirement> requirementsNotPass1 = new HashSet<>();
        requirementsNotPass1.add(new Requirement("c++", 3, true));
        requirementsNotPass1.add(new Requirement("c#", 4, true));
        AddVacancyDtoRequest addVacancyNotPass1 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsNotPass1);

        Set<Requirement> requirementsEmployee = new HashSet<>();
        requirementsEmployee.add(new Requirement("sql", 3));
        requirementsEmployee.add(new Requirement("java", 5));
        requirementsEmployee.add(new Requirement("python", 4));
        requirementsEmployee.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirementsEmployee);

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addVacancy(gson.toJson(addVacancyPass1));
        server.addVacancy(gson.toJson(addVacancyNotPass1));

        Set<Vacancy> expectVacancies = new HashSet<>();
        expectVacancies.add(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult1 = server.getVacanciesWhereNecessaryRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies1 = gson.fromJson(serverResult1, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies1);

        Set<Requirement> requirementsPass2 = new HashSet<>();
        requirementsPass2.add(new Requirement("c#", 3, false));
        requirementsPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirementsPass2);

        Set<Requirement> requirementsNotPass2 = new HashSet<>();
        requirementsNotPass2.add(new Requirement("sql", 5, true));
        requirementsNotPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyNotPass2 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 60000, requirementsNotPass2);

        server.addVacancy(gson.toJson(addVacancyPass2));
        server.addVacancy(gson.toJson(addVacancyNotPass2));

        expectVacancies.add(new Vacancy("Tester", 30000, requirementsPass2));

        String serverResult2 = server.getVacanciesWhereNecessaryRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies2 = gson.fromJson(serverResult2, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies2);

        server.removeVacancy(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));
        expectVacancies.remove(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult3 = server.getVacanciesWhereNecessaryRequirementAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies3 = gson.fromJson(serverResult3, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies3);
    }


    @Test
    public void testGetVacanciesWhereAllReqNotAccountLevel() {
        Set<Requirement> requirementsPass1 = new HashSet<>();
        requirementsPass1.add(new Requirement("python", 5, false));
        requirementsPass1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsPass1);

        Set<Requirement> requirementsNotPass1 = new HashSet<>();
        requirementsNotPass1.add(new Requirement("c++", 3, true));
        requirementsNotPass1.add(new Requirement("c#", 4, true));
        AddVacancyDtoRequest addVacancyNotPass1 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsNotPass1);

        Set<Requirement> requirementsEmloyee = new HashSet<>();
        requirementsEmloyee.add(new Requirement("sql", 3));
        requirementsEmloyee.add(new Requirement("java", 5));
        requirementsEmloyee.add(new Requirement("python", 4));
        requirementsEmloyee.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirementsEmloyee);

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addVacancy(gson.toJson(addVacancyPass1));
        server.addVacancy(gson.toJson(addVacancyNotPass1));

        Set<Vacancy> expectVacancies = new HashSet<>();
        expectVacancies.add(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult1 = server.getVacanciesWhereAllRequirementNotAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies1 = gson.fromJson(serverResult1, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies1);

        Set<Requirement> requirementsPass2 = new HashSet<>();
        requirementsPass2.add(new Requirement("sql", 5, false));
        requirementsPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirementsPass2);

        Set<Requirement> requirementsNotPass2 = new HashSet<>();
        requirementsNotPass2.add(new Requirement("go", 5, true));
        requirementsNotPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyNotPass2 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 60000, requirementsNotPass2);

        server.addVacancy(gson.toJson(addVacancyPass2));
        server.addVacancy(gson.toJson(addVacancyNotPass2));

        expectVacancies.add(new Vacancy("Tester", 30000, requirementsPass2));

        String serverResult2 = server.getVacanciesWhereAllRequirementNotAccountLevel(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        Set<Vacancy> resultVacancies2 = gson.fromJson(serverResult2, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies2);
    }

    @Test
    public void testGetVacanciesFitsLeastOneRequirement() {
        Set<Requirement> requirementsPass1 = new HashSet<>();
        requirementsPass1.add(new Requirement("python", 5, false));
        requirementsPass1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsPass1);

        Set<Requirement> requirementsNotPass1 = new HashSet<>();
        requirementsNotPass1.add(new Requirement("go", 3, true));
        requirementsNotPass1.add(new Requirement("c#", 4, true));
        AddVacancyDtoRequest addVacancyNotPass1 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsNotPass1);

        Set<Requirement> requirementsEmloyee = new HashSet<>();
        requirementsEmloyee.add(new Requirement("sql", 3));
        requirementsEmloyee.add(new Requirement("java", 5));
        requirementsEmloyee.add(new Requirement("python", 4));
        requirementsEmloyee.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirementsEmloyee);

        server.addProfile(gson.toJson(addProfileDtoRequest));
        server.addVacancy(gson.toJson(addVacancyPass1));
        server.addVacancy(gson.toJson(addVacancyNotPass1));

        List<Vacancy> expectVacancies = new ArrayList<>();
        expectVacancies.add(new Vacancy("Programmer", 50000, requirementsPass1));

        String serverResult1 = server.getVacanciesFitsLeastOneRequirement(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        List<Vacancy> resultVacancies1 = gson.fromJson(serverResult1, GetListVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies1);

        Set<Requirement> requirementsPass2 = new HashSet<>();
        requirementsPass2.add(new Requirement("sql", 3, false));
        requirementsPass2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirementsPass2);

        Set<Requirement> requirementsPass3 = new HashSet<>();
        requirementsPass3.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyPass3 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 60000, requirementsPass3);

        Set<Requirement> requirementsPass4 = new HashSet<>();
        requirementsPass4.add(new Requirement("sql", 3, false));
        requirementsPass4.add(new Requirement("java", 3, true));
        requirementsPass4.add(new Requirement("python", 2, true));
        AddVacancyDtoRequest addVacancyPass4 = new AddVacancyDtoRequest(tokenEmployer, "Designer", 30000, requirementsPass4);

        server.addVacancy(gson.toJson(addVacancyPass2));
        server.addVacancy(gson.toJson(addVacancyPass3));
        server.addVacancy(gson.toJson(addVacancyPass4));

        expectVacancies.add(new Vacancy("Designer", 30000, requirementsPass4));
        expectVacancies.add(new Vacancy("Tester", 30000, requirementsPass2));
        expectVacancies.add(new Vacancy("Programmer", 50000, requirementsPass1));
        expectVacancies.add(new Vacancy("Manager", 60000, requirementsPass3));
        expectVacancies.remove(0);

        String serverResult2 = server.getVacanciesFitsLeastOneRequirement(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee)));
        List<Vacancy> resultVacancies2 = gson.fromJson(serverResult2, GetListVacanciesDtoResponse.class).getVacancies();

        assertEquals(expectVacancies, resultVacancies2);
    }
}