package net.thumbtack.school.hiringWithMocks;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Server;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class TestServerWithSpy {

    private final Gson gson = new Gson();
    private final Server server = new Server();

    private final EmployeeService employeeService = new EmployeeService();
    private final EmployerService employerService = new EmployerService();
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private final EmployerDao employerDao = new EmployerDaoImpl();
    private EmployeeDao spyEmployeeDao;
    private EmployerDao spyEmployerDao;

    @Before
    public void startServer() {
        server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
        spyEmployeeDao = spy(employeeDao);
        spyEmployerDao = spy(employerDao);
        employeeService.setEmployeeDao(spyEmployeeDao);
        employerService.setEmployerDao(spyEmployerDao);
    }


    @Test
    public void testRegisterEmployeeWhenExceptionBUSY_LOGIN() throws ServerException {
        Employee employee = new Employee("Olga", "Chernikova", "olga@12", "katya2001", "956523");
        doThrow(new ServerException(ServerErrorCode.BUSY_LOGIN))
                .when(spyEmployeeDao).registerEmployee(employee);

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
    public void testRegisterEmployerWhenExceptionBUSY_LOGIN() throws ServerException {
        Employer employer = new Employer("School", "Krupskaya 6", "Roma", "Petrov",
                "Andreevich","mike@12", "mike1234", "58963445");

        when(spyEmployerDao.registerEmployer(employer))
                .thenThrow(new ServerException(ServerErrorCode.BUSY_LOGIN));

        RegisterEmployerDtoRequest requestCorrect = new RegisterEmployerDtoRequest(
                "Thumbtack", "Krupskaya 6", "Mike", "Voronov",
                "Andreevich", "mike@12", "mike1234", "847562196"
        );
        employerService.registerEmployer(gson.toJson(requestCorrect));

        RegisterEmployerDtoRequest registerSameLogin = new RegisterEmployerDtoRequest(
                "School", "Krupskaya 6", "Roma", "Petrov",
                "Andreevich", "mike@12", "mike1234", "58963445"
        );

        String serverResult = employerService.registerEmployer(gson.toJson(registerSameLogin));
        String result_error = gson.toJson(new ErrorDtoResponse(ServerErrorCode.BUSY_LOGIN));

        assertEquals(result_error, serverResult);
    }

    @Test
    public void testLoginWhenExceptionUSER_NOT_FOUND() throws ServerException {
        doThrow(new ServerException(ServerErrorCode.USER_NOT_FOUND))
                .when(spyEmployerDao).loginEmployer(anyString(), anyString());

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
}