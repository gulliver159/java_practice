package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.*;
import net.thumbtack.school.hiring.dto.request.users.*;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.ads.Requirement;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestServerWhenExceptionTokenNotFound {
    private final Gson gson = new Gson();
    private final Server server = new Server();

    private final String errorResult = gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));
    private final String invalidToken = gson.toJson(new ContainsTokenDtoRequest(123456789));

    private String tokenEmployee, tokenEmployer;

    @Before
    public void startServer() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "mike2001", "847562196"
        );
        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2324", "847562196"
        );
        tokenEmployee = server.registerEmployee(gson.toJson(requestRegisterEmployee));
        tokenEmployer = server.registerEmployer(gson.toJson(requestRegisterEmployer));
    }

    @Test
    public void testLogoutAndLeave() {
        String resultServerEmployee1 = server.logoutEmployee(invalidToken);
        String resultServerEmployee2 = server.leaveEmployee(invalidToken);

        String resultServerEmployer1 = server.logoutEmployer(invalidToken);
        String resultServerEmployer2 = server.leaveEmployer(invalidToken);

        assertAll(
                () -> assertEquals(resultServerEmployee1, errorResult),
                () -> assertEquals(resultServerEmployee2, errorResult),
                () -> assertEquals(resultServerEmployer1, errorResult),
                () -> assertEquals(resultServerEmployer2, errorResult)
        );
    }

    @Test
    public void testAddAndRemoveVacancyAndProfile() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(invalidToken, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(invalidToken, requirements2);

        String resultServerEmployee1 = server.addVacancy(gson.toJson(addVacancyDtoRequest));
        String resultServerEmployer1 = server.addProfile(gson.toJson(addProfileDtoRequest));

        String resultServerEmployee2 = server.removeProfile(gson.toJson(new ContainsTokenDtoRequest(invalidToken)));
        String resultServerEmployer2 = server.removeVacancy(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(invalidToken, "Programmer")));

        assertAll(
                () -> assertEquals(resultServerEmployee1, errorResult),
                () -> assertEquals(resultServerEmployee2, errorResult),
                () -> assertEquals(resultServerEmployer1, errorResult),
                () -> assertEquals(resultServerEmployer2, errorResult)
        );
    }

    @Test
    public void testChangeProfileAndVacancyActivity() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirements2);

        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        server.addProfile(gson.toJson(addProfileDtoRequest));

        String resultServerEmployee1 = server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(invalidToken, "Programmer")));
        String resultServerEmployer1 = server.markVacancyAsActive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(invalidToken, "Programmer")));

        String resultServerEmployee2 = server.markProfileAsInactive(gson.toJson(new ContainsTokenDtoRequest(invalidToken)));
        String resultServerEmployer2 = server.markProfileAsActive(gson.toJson(new ContainsTokenDtoRequest(invalidToken)));

        assertAll(
                () -> assertEquals(resultServerEmployee1, errorResult),
                () -> assertEquals(resultServerEmployee2, errorResult),
                () -> assertEquals(resultServerEmployer1, errorResult),
                () -> assertEquals(resultServerEmployer2, errorResult)
        );
    }

    @Test
    public void testGetVacanciesToEmployer() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddVacancyDtoRequest addVacancyDtoRequest2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 50000, requirements2);

        server.addVacancy(gson.toJson(addVacancyDtoRequest1));
        server.addVacancy(gson.toJson(addVacancyDtoRequest2));

        String request = gson.toJson(new ContainsTokenDtoRequest(invalidToken));

        String resultServer1 = server.getActiveVacancies(request);
        String resultServer2 = server.getInactiveVacancies(request);
        String resultServer3 = server.getAllVacancies(request);

        assertAll(
                () -> assertEquals(resultServer1, errorResult),
                () -> assertEquals(resultServer2, errorResult),
                () -> assertEquals(resultServer3, errorResult)
        );
    }

    @Test
    public void testGetVacanciesToEmployee() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirements2);

        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        server.addProfile(gson.toJson(addProfileDtoRequest));

        String request = gson.toJson(new ContainsTokenDtoRequest(invalidToken));

        String resultServer1 = server.getVacanciesWhereAllRequirementAccountLevel(request);
        String resultServer2 = server.getVacanciesWhereNecessaryRequirementAccountLevel(request);
        String resultServer3 = server.getVacanciesWhereAllRequirementNotAccountLevel(request);
        String resultServer4 = server.getVacanciesFitsLeastOneRequirement(request);

        assertAll(
                () -> assertEquals(resultServer1, errorResult),
                () -> assertEquals(resultServer2, errorResult),
                () -> assertEquals(resultServer3, errorResult),
                () -> assertEquals(resultServer4, errorResult)
        );
    }

    @Test
    public void testGetProfilesToEmployer() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirements2);

        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        server.addProfile(gson.toJson(addProfileDtoRequest));

        String request = gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(invalidToken, "Programmer"));

        String resultServer1 = server.getVacanciesWhereAllRequirementAccountLevel(request);
        String resultServer2 = server.getVacanciesWhereNecessaryRequirementAccountLevel(request);
        String resultServer3 = server.getVacanciesWhereAllRequirementNotAccountLevel(request);
        String resultServer4 = server.getVacanciesFitsLeastOneRequirement(request);

        assertAll(
                () -> assertEquals(resultServer1, errorResult),
                () -> assertEquals(resultServer2, errorResult),
                () -> assertEquals(resultServer3, errorResult),
                () -> assertEquals(resultServer4, errorResult)
        );
    }

    @Test
    public void testHiringEmployee() {
        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 2));
        requirements2.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest = new AddProfileDtoRequest(tokenEmployee, requirements2);

        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        server.addProfile(gson.toJson(addProfileDtoRequest));

        String request = gson.toJson(new HiringEmployeeDtoRequest(invalidToken, "Programmer", "mike2001"));
        String resultServer = server.hiringEmployee(request);

        assertEquals(resultServer, errorResult);
    }
}