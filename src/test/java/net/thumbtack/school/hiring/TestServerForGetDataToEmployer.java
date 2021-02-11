package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.AddProfileDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.ContainsTokenAndNameVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.ContainsTokenDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.HiringEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.response.ads.GetProfilesDtoResponse;
import net.thumbtack.school.hiring.dto.response.ads.GetSetVacanciesDtoResponse;
import net.thumbtack.school.hiring.model.ads.Profile;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.ads.Vacancy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestServerForGetDataToEmployer {
    private final Gson gson = new Gson();
    private final Server server = new Server();

    private String tokenEmployee1, tokenEmployee2, tokenEmployee3, tokenEmployer;

    @Before
    public void startServer() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee1 = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "katya02", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee2 = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "katya020", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee3 = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "katya0200", "847562196"
        );

        tokenEmployee1 = server.registerEmployee(gson.toJson(requestRegisterEmployee1));
        tokenEmployee2 = server.registerEmployee(gson.toJson(requestRegisterEmployee2));
        tokenEmployee3 = server.registerEmployee(gson.toJson(requestRegisterEmployee3));

        tokenEmployer = server.registerEmployer(gson.toJson(requestRegisterEmployer));
    }

    @Test
    public void testChangeActivityVacancyAndGetVacancies() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        requirements1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("sql", 3, false));
        requirements2.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirements2);

        Set<Requirement> requirements3 = new HashSet<>();
        requirements3.add(new Requirement("c#", 2, false));
        requirements3.add(new Requirement("c++", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest3 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 40000, requirements3);

        server.addVacancy(gson.toJson(addVacancyDtoRequest1));
        server.addVacancy(gson.toJson(addVacancyDtoRequest2));
        server.addVacancy(gson.toJson(addVacancyDtoRequest3));

        server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));
        server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Tester")));

        server.markVacancyAsActive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));

        Set<Vacancy> vacanciesActive = new HashSet<>();
        vacanciesActive.add(new Vacancy("Manager", 40000, requirements3));
        vacanciesActive.add(new Vacancy("Programmer", 50000, requirements1));

        Set<Vacancy> vacanciesInactive = new HashSet<>();
        vacanciesInactive.add(new Vacancy("Tester", 30000, requirements2));

        Set<Vacancy> vacanciesAll = new HashSet<>();
        vacanciesAll.add(new Vacancy("Programmer", 50000, requirements1));
        vacanciesAll.add(new Vacancy("Tester", 30000, requirements2));
        vacanciesAll.add(new Vacancy("Manager", 40000, requirements3));

        String serverResultActive = server.getActiveVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
        String serverResultInactive = server.getInactiveVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
        String serverResultAll = server.getAllVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));

        Set<Vacancy> resultServerVacanciesActive = gson.fromJson(serverResultActive, GetSetVacanciesDtoResponse.class).getVacancies();
        Set<Vacancy> resultServerVacanciesInactive = gson.fromJson(serverResultInactive, GetSetVacanciesDtoResponse.class).getVacancies();
        Set<Vacancy> resultServerVacanciesAll = gson.fromJson(serverResultAll, GetSetVacanciesDtoResponse.class).getVacancies();

        assertAll(
                () -> assertEquals(resultServerVacanciesActive, vacanciesActive),
                () -> assertEquals(resultServerVacanciesInactive, vacanciesInactive),
                () -> assertEquals(resultServerVacanciesAll, vacanciesAll)
        );
    }

    @Test
    public void testGetEmployeesWhereAllRequirementAccountLevel() {
        Set<Requirement> requirementsEmployer1 = new HashSet<>();
        requirementsEmployer1.add(new Requirement("python", 3, true));
        requirementsEmployer1.add(new Requirement("java", 4, false));
        AddVacancyDtoRequest addVacancy1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsEmployer1);

        Set<Requirement> requirementsEmployer2 = new HashSet<>();
        requirementsEmployer2.add(new Requirement("c++", 3, true));
        requirementsEmployer2.add(new Requirement("c#", 4, false));
        AddVacancyDtoRequest addVacancy2 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsEmployer2);

        Set<Requirement> requirementsEmployee1 = new HashSet<>();
        requirementsEmployee1.add(new Requirement("sql", 3));
        requirementsEmployee1.add(new Requirement("java", 5));
        requirementsEmployee1.add(new Requirement("python", 4));
        requirementsEmployee1.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenEmployee1, requirementsEmployee1);

        Set<Requirement> requirementsEmployee2 = new HashSet<>();
        requirementsEmployee2.add(new Requirement("c#", 5));
        requirementsEmployee2.add(new Requirement("java", 2));
        requirementsEmployee2.add(new Requirement("python", 4));
        requirementsEmployee2.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(tokenEmployee2, requirementsEmployee2);

        Set<Requirement> requirementsEmployee3 = new HashSet<>();
        requirementsEmployee3.add(new Requirement("c#", 4));
        requirementsEmployee3.add(new Requirement("sql", 3));
        requirementsEmployee3.add(new Requirement("java", 5));
        requirementsEmployee3.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest3 = new AddProfileDtoRequest(tokenEmployee3, requirementsEmployee3);

        server.addProfile(gson.toJson(addProfileDtoRequest1));
        server.addProfile(gson.toJson(addProfileDtoRequest2));
        server.addProfile(gson.toJson(addProfileDtoRequest3));

        server.addVacancy(gson.toJson(addVacancy1));
        server.addVacancy(gson.toJson(addVacancy2));

        Set<Profile> expectProfiles1 = new HashSet<>();
        expectProfiles1.add(new Profile(requirementsEmployee1));

        Set<Profile> expectProfiles2 = new HashSet<>();
        expectProfiles2.add(new Profile(requirementsEmployee2));
        expectProfiles2.add(new Profile(requirementsEmployee3));

        String serverResult1 = server.getEmployeesWhereAllRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer"))
        );
        String serverResult2 = server.getEmployeesWhereAllRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles1 = gson.fromJson(serverResult1, GetProfilesDtoResponse.class).getProfiles();
        Set<Profile> resultProfiles2 = gson.fromJson(serverResult2, GetProfilesDtoResponse.class).getProfiles();

        assertEquals(resultProfiles1, expectProfiles1);
        assertEquals(resultProfiles2, expectProfiles2);

        server.markProfileAsInactive(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee3)));
        expectProfiles2.remove(new Profile(requirementsEmployee3));

        String serverResult3 = server.getEmployeesWhereAllRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles3 = gson.fromJson(serverResult3, GetProfilesDtoResponse.class).getProfiles();
        assertEquals(expectProfiles2, resultProfiles3);

        server.markProfileAsActive(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee3)));
        expectProfiles2.add(new Profile(requirementsEmployee3));

        String serverResult4 = server.getEmployeesWhereAllRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles4 = gson.fromJson(serverResult4, GetProfilesDtoResponse.class).getProfiles();
        assertEquals(expectProfiles2, resultProfiles4);
    }

    @Test
    public void testGetEmployeesWhereNecessaryRequirementAccountLevel() {
        Set<Requirement> requirementsEmployer1 = new HashSet<>();
        requirementsEmployer1.add(new Requirement("python", 3, true));
        requirementsEmployer1.add(new Requirement("java", 4, false));
        AddVacancyDtoRequest addVacancy1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsEmployer1);

        Set<Requirement> requirementsEmployer2 = new HashSet<>();
        requirementsEmployer2.add(new Requirement("c++", 3, true));
        requirementsEmployer2.add(new Requirement("c#", 4, false));
        AddVacancyDtoRequest addVacancy2 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsEmployer2);

        Set<Requirement> requirementsEmployee1 = new HashSet<>();
        requirementsEmployee1.add(new Requirement("sql", 3));
        requirementsEmployee1.add(new Requirement("java", 5));
        requirementsEmployee1.add(new Requirement("python", 4));
        requirementsEmployee1.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenEmployee1, requirementsEmployee1);

        Set<Requirement> requirementsEmployee2 = new HashSet<>();
        requirementsEmployee2.add(new Requirement("c#", 5));
        requirementsEmployee2.add(new Requirement("java", 2));
        requirementsEmployee2.add(new Requirement("python", 4));
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(tokenEmployee2, requirementsEmployee2);

        Set<Requirement> requirementsEmployee3 = new HashSet<>();
        requirementsEmployee3.add(new Requirement("c#", 2));
        requirementsEmployee3.add(new Requirement("sql", 3));
        requirementsEmployee3.add(new Requirement("java", 5));
        requirementsEmployee3.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest3 = new AddProfileDtoRequest(tokenEmployee3, requirementsEmployee3);

        server.addProfile(gson.toJson(addProfileDtoRequest1));
        server.addProfile(gson.toJson(addProfileDtoRequest2));
        server.addProfile(gson.toJson(addProfileDtoRequest3));

        server.addVacancy(gson.toJson(addVacancy1));
        server.addVacancy(gson.toJson(addVacancy2));

        Set<Profile> expectProfiles1 = new HashSet<>();
        expectProfiles1.add(new Profile(requirementsEmployee1));
        expectProfiles1.add(new Profile(requirementsEmployee2));

        Set<Profile> expectProfiles2 = new HashSet<>();
        expectProfiles2.add(new Profile(requirementsEmployee1));
        expectProfiles2.add(new Profile(requirementsEmployee3));

        String serverResult1 = server.getEmployeesWhereNecessaryRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer"))
        );
        String serverResult2 = server.getEmployeesWhereNecessaryRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles1 = gson.fromJson(serverResult1, GetProfilesDtoResponse.class).getProfiles();
        Set<Profile> resultProfiles2 = gson.fromJson(serverResult2, GetProfilesDtoResponse.class).getProfiles();

        assertEquals(resultProfiles1, expectProfiles1);
        assertEquals(resultProfiles2, expectProfiles2);

        server.removeProfile(gson.toJson(new ContainsTokenDtoRequest(tokenEmployee3)));
        expectProfiles2.remove(new Profile(requirementsEmployee3));

        String serverResult3 = server.getEmployeesWhereNecessaryRequirementAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles3 = gson.fromJson(serverResult3, GetProfilesDtoResponse.class).getProfiles();
        assertEquals(expectProfiles2, resultProfiles3);
    }

    @Test
    public void testGetEmployeesWhereAllRequirementNotAccountLevel() {
        Set<Requirement> requirementsEmployer1 = new HashSet<>();
        requirementsEmployer1.add(new Requirement("python", 3, true));
        requirementsEmployer1.add(new Requirement("java", 4, false));
        AddVacancyDtoRequest addVacancy1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsEmployer1);

        Set<Requirement> requirementsEmployer2 = new HashSet<>();
        requirementsEmployer2.add(new Requirement("c++", 3, true));
        requirementsEmployer2.add(new Requirement("c#", 4, false));
        AddVacancyDtoRequest addVacancy2 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsEmployer2);

        Set<Requirement> requirementsEmployee1 = new HashSet<>();
        requirementsEmployee1.add(new Requirement("sql", 3));
        requirementsEmployee1.add(new Requirement("java", 5));
        requirementsEmployee1.add(new Requirement("python", 4));
        requirementsEmployee1.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenEmployee1, requirementsEmployee1);

        Set<Requirement> requirementsEmployee2 = new HashSet<>();
        requirementsEmployee2.add(new Requirement("c#", 5));
        requirementsEmployee2.add(new Requirement("c++", 2));
        requirementsEmployee2.add(new Requirement("python", 4));
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(tokenEmployee2, requirementsEmployee2);

        Set<Requirement> requirementsEmployee3 = new HashSet<>();
        requirementsEmployee3.add(new Requirement("c#", 2));
        requirementsEmployee3.add(new Requirement("sql", 3));
        requirementsEmployee3.add(new Requirement("java", 5));
        requirementsEmployee3.add(new Requirement("c++", 4));
        AddProfileDtoRequest addProfileDtoRequest3 = new AddProfileDtoRequest(tokenEmployee3, requirementsEmployee3);

        server.addProfile(gson.toJson(addProfileDtoRequest1));
        server.addProfile(gson.toJson(addProfileDtoRequest2));
        server.addProfile(gson.toJson(addProfileDtoRequest3));

        server.addVacancy(gson.toJson(addVacancy1));
        server.addVacancy(gson.toJson(addVacancy2));

        Set<Profile> expectProfiles1 = new HashSet<>();
        expectProfiles1.add(new Profile(requirementsEmployee1));

        Set<Profile> expectProfiles2 = new HashSet<>();
        expectProfiles2.add(new Profile(requirementsEmployee2));
        expectProfiles2.add(new Profile(requirementsEmployee3));

        String serverResult1 = server.getEmployeesWhereAllRequirementNotAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer"))
        );
        String serverResult2 = server.getEmployeesWhereAllRequirementNotAccountLevel(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles1 = gson.fromJson(serverResult1, GetProfilesDtoResponse.class).getProfiles();
        Set<Profile> resultProfiles2 = gson.fromJson(serverResult2, GetProfilesDtoResponse.class).getProfiles();

        assertEquals(resultProfiles1, expectProfiles1);
        assertEquals(resultProfiles2, expectProfiles2);
    }

    @Test
    public void testGetEmployeesFitsLeastOneRequirement() {
        Set<Requirement> requirementsEmployer1 = new HashSet<>();
        requirementsEmployer1.add(new Requirement("python", 4, true));
        requirementsEmployer1.add(new Requirement("java", 4, false));
        AddVacancyDtoRequest addVacancy1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirementsEmployer1);

        Set<Requirement> requirementsEmployer2 = new HashSet<>();
        requirementsEmployer2.add(new Requirement("c++", 5, true));
        requirementsEmployer2.add(new Requirement("c#", 4, false));
        AddVacancyDtoRequest addVacancy2 = new AddVacancyDtoRequest(tokenEmployer, "Director", 70000, requirementsEmployer2);

        Set<Requirement> requirementsEmployee1 = new HashSet<>();
        requirementsEmployee1.add(new Requirement("sql", 3));
        requirementsEmployee1.add(new Requirement("java", 5));
        requirementsEmployee1.add(new Requirement("python", 3));
        requirementsEmployee1.add(new Requirement("c#", 4));
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenEmployee1, requirementsEmployee1);

        Set<Requirement> requirementsEmployee2 = new HashSet<>();
        requirementsEmployee2.add(new Requirement("sql", 5));
        requirementsEmployee2.add(new Requirement("c++", 2));
        requirementsEmployee2.add(new Requirement("python", 4));
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(tokenEmployee2, requirementsEmployee2);

        Set<Requirement> requirementsEmployee3 = new HashSet<>();
        requirementsEmployee3.add(new Requirement("c#", 5));
        requirementsEmployee3.add(new Requirement("python", 2));
        requirementsEmployee3.add(new Requirement("sql", 3));
        requirementsEmployee3.add(new Requirement("c++", 2));
        AddProfileDtoRequest addProfileDtoRequest3 = new AddProfileDtoRequest(tokenEmployee3, requirementsEmployee3);

        server.addProfile(gson.toJson(addProfileDtoRequest1));
        server.addProfile(gson.toJson(addProfileDtoRequest2));
        server.addProfile(gson.toJson(addProfileDtoRequest3));

        server.addVacancy(gson.toJson(addVacancy1));
        server.addVacancy(gson.toJson(addVacancy2));

        Set<Profile> expectProfiles1 = new HashSet<>();
        expectProfiles1.add(new Profile(requirementsEmployee1));
        expectProfiles1.add(new Profile(requirementsEmployee2));

        Set<Profile> expectProfiles2 = new HashSet<>();
        expectProfiles2.add(new Profile(requirementsEmployee1));
        expectProfiles2.add(new Profile(requirementsEmployee3));

        String serverResult1 = server.getEmployeesFitsLeastOneRequirement(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer"))
        );
        String serverResult2 = server.getEmployeesFitsLeastOneRequirement(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles1 = gson.fromJson(serverResult1, GetProfilesDtoResponse.class).getProfiles();
        Set<Profile> resultProfiles2 = gson.fromJson(serverResult2, GetProfilesDtoResponse.class).getProfiles();

        assertEquals(resultProfiles1, expectProfiles1);
        assertEquals(resultProfiles2, expectProfiles2);

        server.hiringEmployee(gson.toJson(new HiringEmployeeDtoRequest(tokenEmployer, "Programmer", "katya02")));

        expectProfiles2.remove(new Profile(requirementsEmployee1));

        String serverResult3 = server.getEmployeesFitsLeastOneRequirement(
                gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Director"))
        );
        Set<Profile> resultProfiles3 = gson.fromJson(serverResult3, GetProfilesDtoResponse.class).getProfiles();

        assertEquals(resultProfiles3, expectProfiles2);

        Set<Vacancy> vacanciesActive = new HashSet<>();
        vacanciesActive.add(new Vacancy("Director", 70000, requirementsEmployer2));
        String serverResultVacancies = server.getActiveVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
        Set<Vacancy> resultServerVacanciesActive = gson.fromJson(serverResultVacancies, GetSetVacanciesDtoResponse.class).getVacancies();

        assertEquals(resultServerVacanciesActive, vacanciesActive);
    }
}