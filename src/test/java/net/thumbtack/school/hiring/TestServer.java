package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.*;
import net.thumbtack.school.hiring.dto.request.users.*;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.ads.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestServer {

    private final Gson gson = new Gson();
    private final Server server = new Server();

    @Before
    public void startServer() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
    }

    @Test
    public void testRegisterEmployeeWhenExceptionINVALID_DATA() {
        RegisterEmployeeDtoRequest request1 = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "0220"
        );
        RegisterEmployeeDtoRequest request2 = new RegisterEmployeeDtoRequest(
                "", "Rogozhina", "Andreevna", "katya@12", "kat", "02200220"
        );

        String serverResult1 = server.registerEmployee(gson.toJson(request1));
        String serverResult2 = server.registerEmployee(gson.toJson(request2));

        String errorPassword = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PASSWORD));
        String errorFirstName = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_FIRST_NAME));

        assertAll(
                () -> assertEquals(errorPassword, serverResult1),
                () -> assertEquals(errorFirstName, serverResult2)
        );

    }

    @Test
    public void testRegisterEmployeeWhenExceptionBUSY_LOGIN() {
        RegisterEmployeeDtoRequest requestCorrect = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );
        server.registerEmployee(gson.toJson(requestCorrect));

        RegisterEmployeeDtoRequest registerSameLogin = new RegisterEmployeeDtoRequest(
                "Olga", "Chernikova", "olga@12", "katya2001", "956523"
        );

        String serverResult = server.registerEmployee(gson.toJson(registerSameLogin));
        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        assertEquals(result_error, serverResult);
    }

    @Test
    public void testRegisterEmployerWhenExceptionINVALID_DATA() {
        RegisterEmployerDtoRequest request1 = new RegisterEmployerDtoRequest(
                "Tamtek", "", "", "Voronov",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployerDtoRequest request2 = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike", "847"
        );

        String serverResult1 = server.registerEmployer(gson.toJson(request1));
        String serverResult2 = server.registerEmployer(gson.toJson(request2));

        String errorAddress = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_ADDRESS));
        String errorLogin = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_LOGIN));

        assertAll(
                () -> assertEquals(errorAddress, serverResult1),
                () -> assertEquals(errorLogin, serverResult2)
        );
    }

    @Test
    public void testRegisterEmployerWhenExceptionBUSY_LOGIN() {
        RegisterEmployerDtoRequest requestCorrect = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov",
                "mike@12", "mike1234", "847562196"
        );
        server.registerEmployee(gson.toJson(requestCorrect));

        RegisterEmployerDtoRequest registerSameLogin = new RegisterEmployerDtoRequest(
                "School", "Krupskaya 6", "Roma", "Petrov",
                "mike@12", "mike1234", "58963445"
        );

        String serverResult = server.registerEmployee(gson.toJson(registerSameLogin));
        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        assertEquals(result_error, serverResult);
    }

//    @Ignore
//    @Test
//    public void testStartAndStopServer() {
//        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
//                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
//                "mike@12", "mike2001", "847562196"
//        );
//        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
//                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
//        );
//
//        server.registerEmployer(gson.toJson(requestRegisterEmployer));
//        server.registerEmployee(gson.toJson(requestRegisterEmployee));
//
//        DataBase dataBaseBeforeSave = DataBase.getInstance();
//        StartAndStopDtoRequest startAndStopDtoRequest = new StartAndStopDtoRequest("SavedDataBaseTest1");
//
//        server.stopServer(gson.toJson(startAndStopDtoRequest));
//        server.startServer(gson.toJson(startAndStopDtoRequest));
//
//        DataBase dataBaseAfterSave = DataBase.getInstance();
//        assertEquals(dataBaseBeforeSave, dataBaseAfterSave);
//    }

    @Test
    public void testLoginAndLogout() {
        String resultError = gson.toJson(new ErrorDtoResponse(ServerErrorCode.USER_NOT_FOUND));

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );

        String tokenEmloyerJson = server.registerEmployer(gson.toJson(requestRegisterEmployer));
        String tokenEmloyeeJson = server.registerEmployee(gson.toJson(requestRegisterEmployee));

        server.logoutEmployer(tokenEmloyerJson);
        server.logoutEmployee(tokenEmloyeeJson);

        String serverResult1 = server.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "847562196")));
        String serverResult2 = server.loginEmployee(gson.toJson(new LoginDtoRequest("katya2001", "02200220")));

        assertAll(
                () -> assertNotEquals(resultError, serverResult1),
                () -> assertNotEquals(resultError, serverResult2)
        );
    }

    @Test
    public void testLoginWhenExceptionUSER_NOT_FOUND() {
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );

        String tokenAfterRegisterJson = server.registerEmployee(gson.toJson(requestRegisterEmployee));
        server.logoutEmployee(tokenAfterRegisterJson);

        String tokenAfterLoginJson1 = server.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "947562196")));
        String tokenAfterLoginJson2 = server.loginEmployer(gson.toJson(new LoginDtoRequest("mike", "847562196")));
        String tokenAfterLoginJson3 = server.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "")));

        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.USER_NOT_FOUND));

        assertAll(
                () -> assertEquals(result_error, tokenAfterLoginJson1),
                () -> assertEquals(result_error, tokenAfterLoginJson2),
                () -> assertEquals(result_error, tokenAfterLoginJson3)
        );
    }


    @Test
    public void testLeave() {
        String resultError = gson.toJson(new ErrorDtoResponse(ServerErrorCode.USER_NOT_FOUND));

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );

        String tokenEmloyerJson = server.registerEmployer(gson.toJson(requestRegisterEmployer));
        String tokenEmloyeeJson = server.registerEmployee(gson.toJson(requestRegisterEmployee));

        server.leaveEmployer(tokenEmloyerJson);
        server.leaveEmployee(tokenEmloyeeJson);

        String serverResult1 = server.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "847562196")));
        String serverResult2 = server.loginEmployer(gson.toJson(new LoginDtoRequest("katya2001", "02200220")));

        assertAll(
                () -> assertEquals(resultError, serverResult1),
                () -> assertEquals(resultError, serverResult2)
        );
    }

    @Test
    public void testAddVacancyWhenException() {
        String errorJobTitle = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_JOB_TITLE));
        String errorSalary = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_SALARY));
        String errorTitleRequirement = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_TITLE_REQUIREMENT));
        String errorLevel = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PROFICIENCY_LEVEL));

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );

        String tokenJson = server.registerEmployer(gson.toJson(requestRegisterEmployer));

        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenJson, "", 50000, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 3, false));
        AddVacancyDtoRequest addVacancyDtoRequest2 = new AddVacancyDtoRequest(tokenJson, "Programmer", -50, requirements2);

        Set<Requirement> requirements3 = new HashSet<>();
        requirements3.add(new Requirement("", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest3 = new AddVacancyDtoRequest(tokenJson, "Programmer", 50000, requirements3);

        Set<Requirement> requirements4 = new HashSet<>();
        requirements4.add(new Requirement("python", 7, false));
        AddVacancyDtoRequest addVacancyDtoRequest4 = new AddVacancyDtoRequest(tokenJson, "Programmer", 50000, requirements4);

        String serverResult1 = server.addVacancy(gson.toJson(addVacancyDtoRequest1));
        String serverResult2 = server.addVacancy(gson.toJson(addVacancyDtoRequest2));
        String serverResult3 = server.addVacancy(gson.toJson(addVacancyDtoRequest3));
        String serverResult4 = server.addVacancy(gson.toJson(addVacancyDtoRequest4));

        assertAll(
                () -> assertEquals(serverResult1, errorJobTitle),
                () -> assertEquals(serverResult2, errorSalary),
                () -> assertEquals(serverResult3, errorTitleRequirement),
                () -> assertEquals(serverResult4, errorLevel)
        );
    }

    @Test
    public void testAddProfileWhenException() {
        String errorTitleRequirement = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_TITLE_REQUIREMENT));
        String errorLevel = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PROFICIENCY_LEVEL));

        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Mike", "Voronov", "Andreevich", "mike@12", "mike2001", "847562196"
        );

        String tokenJson = server.registerEmployee(gson.toJson(requestRegisterEmployee));

        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("", 2));
        requirements1.add(new Requirement("java", 4));
        AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenJson, requirements1);

        Set<Requirement> requirements2 = new HashSet<>();
        requirements2.add(new Requirement("python", 3));
        requirements2.add(new Requirement("java", -6));
        AddProfileDtoRequest addProfileDtoRequest2 = new AddProfileDtoRequest(tokenJson, requirements2);

        String serverResult1 = server.addProfile(gson.toJson(addProfileDtoRequest1));
        String serverResult2 = server.addProfile(gson.toJson(addProfileDtoRequest2));

        assertAll(
                () -> assertEquals(serverResult1, errorTitleRequirement),
                () -> assertEquals(serverResult2, errorLevel)
        );
    }
}