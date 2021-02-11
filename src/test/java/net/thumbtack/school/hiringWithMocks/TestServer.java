package net.thumbtack.school.hiringWithMocks;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Server;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.LoginDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.users.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exception.ServerErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.users.Employee;
import net.thumbtack.school.hiring.model.users.Employer;
import net.thumbtack.school.hiring.service.EmployeeService;
import net.thumbtack.school.hiring.service.EmployerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestServer {

    private final Gson gson = new Gson();
    private final EmployeeService employeeService = new EmployeeService();
    private final EmployeeDao mockEmployeeDao = mock(EmployeeDao.class);
    private final EmployerService employerService = new EmployerService();
    private final EmployerDao mockEmployerDao = mock(EmployerDao.class);

    @Before
    public void startServer() {
        employeeService.setEmployeeDao(mockEmployeeDao);
        employerService.setEmployerDao(mockEmployerDao);
    }

    @Test
    public void testRegisterEmployeeWhenExceptionINVALID_DATA() throws ServerException {
        RegisterEmployeeDtoRequest request1 = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "0220"
        );
        RegisterEmployeeDtoRequest request2 = new RegisterEmployeeDtoRequest(
                "", "Rogozhina", "Andreevna", "katya@12", "kat", "02200220"
        );

        employeeService.registerEmployee(gson.toJson(request1));
        employeeService.registerEmployee(gson.toJson(request2));

        verify(mockEmployeeDao, never()).registerEmployee(any(Employee.class));
    }

    @Test
    public void testRegisterEmployeeWhenExceptionBUSY_LOGIN() throws ServerException {
        doReturn("token")
                .doThrow(new ServerException(ServerErrorCode.BUSY_LOGIN))
                .when(mockEmployeeDao).registerEmployee(any(Employee.class));

        RegisterEmployeeDtoRequest requestCorrect = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );
        employeeService.registerEmployee(gson.toJson(requestCorrect));

        RegisterEmployeeDtoRequest registerSameLogin = new RegisterEmployeeDtoRequest(
                "Olga", "Chernikova", "olga@12", "katya2001", "956523"
        );

        String serverResult = employeeService.registerEmployee(gson.toJson(registerSameLogin));
        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        assertEquals(result_error, serverResult);
    }

    @Test
    public void testRegisterEmployerWhenExceptionINVALID_DATA() throws ServerException {
        RegisterEmployerDtoRequest request1 = new RegisterEmployerDtoRequest(
                "Thumbtack", "", "", "Voronov",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployerDtoRequest request2 = new RegisterEmployerDtoRequest(
                "Thumbtack", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike", "847"
        );

        employerService.registerEmployer(gson.toJson(request1));
        employerService.registerEmployer(gson.toJson(request2));

        verify(mockEmployerDao, never()).registerEmployer(any(Employer.class));
    }

    @Test
    public void testRegisterEmployerWhenExceptionBUSY_LOGIN() throws ServerException {
        when(mockEmployerDao.registerEmployer(any(Employer.class)))
                .thenReturn("token")
                .thenThrow(new ServerException(ServerErrorCode.BUSY_LOGIN));

        RegisterEmployerDtoRequest requestCorrect = new RegisterEmployerDtoRequest(
                "Thumbtack", "Krupskaya 6", "Mike", "Voronov",
                "mike@12", "mike1234", "847562196"
        );
        employerService.registerEmployer(gson.toJson(requestCorrect));

        RegisterEmployerDtoRequest registerSameLogin = new RegisterEmployerDtoRequest(
                "School", "Krupskaya 6", "Roma", "Petrov",
                "mike@12", "mike1234", "58963445"
        );

        String serverResult = employerService.registerEmployer(gson.toJson(registerSameLogin));
        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        assertEquals(result_error, serverResult);
    }

    @Test
    public void testLoginAndLogout() throws ServerException {
        when(mockEmployerDao.registerEmployer(any(Employer.class)))
                .thenReturn("token");
        when(mockEmployeeDao.registerEmployee(any(Employee.class)))
                .thenReturn("token");

        doReturn("token")
                .when(mockEmployerDao).loginEmployer(anyString(), anyString());
        doReturn("token")
                .when(mockEmployeeDao).loginEmployee(anyString(), anyString());


        String resultError = gson.toJson(new ErrorDtoResponse(ServerErrorCode.USER_NOT_FOUND));

        RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                "Thumbtack", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                "mike@12", "mike2001", "847562196"
        );
        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );

        String tokenEmployerJson = employerService.registerEmployer(gson.toJson(requestRegisterEmployer));
        String tokenEmployeeJson = employeeService.registerEmployee(gson.toJson(requestRegisterEmployee));

        employerService.logoutEmployer(tokenEmployerJson);
        employeeService.logoutEmployee(tokenEmployeeJson);

        String serverResult1 = employerService.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "847562196")));
        String serverResult2 = employeeService.loginEmployee(gson.toJson(new LoginDtoRequest("katya2001", "02200220")));

        assertAll(
                () -> assertNotEquals(resultError, serverResult1),
                () -> assertNotEquals(resultError, serverResult2)
        );
    }

    @Test
    public void testLoginWhenExceptionUSER_NOT_FOUND() throws ServerException {
        when(mockEmployeeDao.registerEmployee(any(Employee.class)))
                .thenReturn("token");

        when(mockEmployerDao.loginEmployer(anyString(), anyString()))
                .thenThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND));

        RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );

        String tokenAfterRegisterJson = employeeService.registerEmployee(gson.toJson(requestRegisterEmployee));
        employeeService.logoutEmployee(tokenAfterRegisterJson);

        String tokenAfterLoginJson1 = employerService.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "947562196")));
        String tokenAfterLoginJson2 = employerService.loginEmployer(gson.toJson(new LoginDtoRequest("mike", "847562196")));
        String tokenAfterLoginJson3 = employerService.loginEmployer(gson.toJson(new LoginDtoRequest("mike2001", "")));

        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.USER_NOT_FOUND));

        assertAll(
                () -> assertEquals(result_error, tokenAfterLoginJson1),
                () -> assertEquals(result_error, tokenAfterLoginJson2),
                () -> assertEquals(result_error, tokenAfterLoginJson3)
        );
    }

    @Test
    public void testRegisterEmployee() throws ServerException {
        Employee employee = new Employee("Katya", "Rogozhina", "katya@12", "katya2001", "02200220");
        RegisterEmployeeDtoRequest request1 = new RegisterEmployeeDtoRequest(
                "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
        );
        when(mockEmployeeDao.registerEmployee(any(Employee.class))).thenReturn("token");

        employeeService.registerEmployee(gson.toJson(request1));

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(mockEmployeeDao).registerEmployee(employeeCaptor.capture());

        assertEquals(employee, employeeCaptor.getValue());
    }
}