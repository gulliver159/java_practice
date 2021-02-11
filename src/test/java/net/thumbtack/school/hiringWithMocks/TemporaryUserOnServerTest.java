package net.thumbtack.school.hiringWithMocks;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Server;
import net.thumbtack.school.hiring.TemporaryUserOnServer;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.ads.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.dto.response.ads.GetRequirementsDtoResponse;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.users.Employee;
import net.thumbtack.school.hiring.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemporaryUserOnServerTest {
    private final TemporaryUserOnServer temporaryUserOnServer = new TemporaryUserOnServer();
    private final Server server = new Server();
    private final EmployeeService mockEmployeeService = mock(EmployeeService.class);

    private final Gson gson = new Gson();

    @BeforeEach
    public void startServer() {
        server.setEmployeeService(mockEmployeeService);
        temporaryUserOnServer.setServer(server);
    }

    @Test
    void testActWhenNotExistSkills() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));

        Set<String> setTitlesRequirements = new HashSet<>();
        String returnedSet = gson.toJson(new GetRequirementsDtoResponse(setTitlesRequirements));

        when(mockEmployeeService.registerEmployee(anyString())).thenReturn("token");
        when(mockEmployeeService.getExistSkills()).thenReturn(returnedSet);
        when(mockEmployeeService.leaveEmployee("token")).thenReturn("");

        Employee employee = new Employee("Katya", "Rogozhina", "katya@12",
                "katya2001", "02200220");
        String result = temporaryUserOnServer.act(employee);

        assertEquals("0", result);
    }

    @Test
    void testActWhenInvalidPassword() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));

        String errorPassword = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PASSWORD));

        when(mockEmployeeService.registerEmployee(anyString())).thenReturn(errorPassword);

        Employee employee = new Employee("Katya", "Rogozhina", "katya@12",
                "katya2001", "0220");
        String result = temporaryUserOnServer.act(employee);

        assertEquals(errorPassword, result);
        verify(mockEmployeeService, never()).getExistSkills();
        verify(mockEmployeeService, never()).leaveEmployee(anyString());
    }

    @Test
    void testActWhenInvalidFirstName() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));

        String errorFirstName = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_FIRST_NAME));

        when(mockEmployeeService.registerEmployee(anyString())).thenReturn(errorFirstName);

        Employee employee = new Employee("", "Rogozhina", "katya@12",
                "katya2001", "02200220");
        String result = temporaryUserOnServer.act(employee);

        assertEquals(errorFirstName, result);
        verify(mockEmployeeService, never()).getExistSkills();
        verify(mockEmployeeService, never()).leaveEmployee(anyString());
    }

    @Test
    void testActWhenBusyLogin() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));

        String errorBusyLogin = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        doReturn(errorBusyLogin).when(mockEmployeeService).registerEmployee(anyString());

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "katya2001", "847562196"
        );
        server.registerEmployer(gson.toJson(requestRegisterEmployer));

        Employee employee = new Employee("Katya", "Rogozhina", "katya@12",
                "katya2001", "02200220");
        String result = temporaryUserOnServer.act(employee);

        assertEquals(errorBusyLogin, result);
        verify(mockEmployeeService, never()).getExistSkills();
        verify(mockEmployeeService, never()).leaveEmployee(anyString());
    }

    @Test
    void testActWhenExistSkills() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));

        Set<String> setTitlesRequirements = new HashSet<>();
        setTitlesRequirements.add("python");
        setTitlesRequirements.add("java");
        String returnedSet = gson.toJson(new GetRequirementsDtoResponse(setTitlesRequirements));

        when(mockEmployeeService.registerEmployee(anyString())).thenReturn("token");
        when(mockEmployeeService.getExistSkills()).thenReturn(returnedSet);
        when(mockEmployeeService.leaveEmployee("token")).thenReturn("");

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        String tokenEmployer = server.registerEmployer(gson.toJson(requestRegisterEmployer));

        Set<Requirement> requirements1 = new HashSet<>();
        requirements1.add(new Requirement("python", 3, false));
        requirements1.add(new Requirement("java", 4, true));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

        server.addVacancy(gson.toJson(addVacancyDtoRequest));

        Employee employee = new Employee("Katya", "Rogozhina", "katya@12",
                "katya2001", "02200220");
        String result = temporaryUserOnServer.act(employee);

        assertEquals("2", result);
    }
}