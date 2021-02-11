package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.ads.*;
import net.thumbtack.school.hiring.dto.request.users.*;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.dto.response.ads.*;
import net.thumbtack.school.hiring.dto.response.users.LoginOrRegisterDtoResponse;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.ads.Profile;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.users.Employee;

import net.thumbtack.school.hiring.model.users.User;

public class EmployeeService {

    private final Gson gson = new Gson();
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    public String registerEmployee(String requestJsonString) {
        String token;
        RegisterEmployeeDtoRequest request = gson.fromJson(requestJsonString, RegisterEmployeeDtoRequest.class);

        try {
            validateEmployee(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        Employee employee = createEmployee(request);

        try {
            token = employeeDao.registerEmployee(employee);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        return gson.toJson(new LoginOrRegisterDtoResponse(token));
    }

    public String loginEmployee(String requestJsonString) {
        String token;
        LoginDtoRequest request = gson.fromJson(requestJsonString, LoginDtoRequest.class);
        try {
            token = employeeDao.loginEmployee(request.getLogin(), request.getPassword());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson(new LoginOrRegisterDtoResponse(token));
    }

    public String logoutEmployee(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);
        try {
            employeeDao.logoutEmployee(request.getToken());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String leaveEmployee(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);
        try {
            employeeDao.leaveEmployee(request.getToken());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String addProfile(String requestJsonString) {
        AddProfileDtoRequest request = gson.fromJson(requestJsonString, AddProfileDtoRequest.class);

        try {
            validateProfile(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        Profile profile = createProfile(request);
        employeeDao.addProfile(employee, profile);
        return gson.toJson("");
    }

    public String removeProfile(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employeeDao.removeProfile(employee);
        return gson.toJson("");
    }

    public String addRequirementInProfile(String requestJsonString) {
        AddOrChangeSkillDtoRequest request = gson.fromJson(requestJsonString, AddOrChangeSkillDtoRequest.class);
        try {
            validateRequirement(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employeeDao.addRequirementInProfile(employee, request.getRequirement());

        return gson.toJson("");
    }

    public String removeRequirementInProfile(String requestJsonString) {
        RemoveSkillDtoRequest request = gson.fromJson(requestJsonString, RemoveSkillDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employeeDao.removeRequirementInProfile(employee, request.getTitleRequirement());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String changeRequirementInProfile(String requestJsonString) {
        AddOrChangeSkillDtoRequest request = gson.fromJson(requestJsonString, AddOrChangeSkillDtoRequest.class);
        try {
            validateRequirement(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employeeDao.changeRequirementInProfile(employee, request.getRequirement());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }


    public String markProfileAsInactive(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employeeDao.markProfileAsInactive(employee);
        return gson.toJson("");
    }

    public String markProfileAsActive(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employeeDao.markProfileAsActive(employee);
        return gson.toJson("");
    }

    public String changeRegistrationDataEmployee(String requestJsonString) {
        ChangeRegistrationDataDtoRequest request =
                gson.fromJson(requestJsonString, ChangeRegistrationDataDtoRequest.class);

        try {
            validateEmployee(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        User user = employeeDao.getUserByToken(request.getToken());
        if(user == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employeeDao.changeRegistrationDataEmployee(user, request.getCompanyName(), request.getAddress(),
                request.getFirstName(), request.getLastName(), request.getPatronymic(),
                request.getEmail(), request.getPassword());
        return gson.toJson("");
    }

    public String getExistSkills() {
        return gson.toJson(new GetRequirementsDtoResponse(employeeDao.getExistSkills()));
    }


    public String getVacanciesWhereAllReqAccountLevel(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employeeDao.getVacanciesWhereAllReqAccountLevel(employee)));
    }

    public String getVacanciesWhereNecessaryReqAccountLevel(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employeeDao.getVacanciesWhereNecessaryReqAccountLevel(employee)));
    }

    public String getVacanciesWhereAllReqNotAccountLevel(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employeeDao.getVacanciesWhereAllReqNotAccountLevel(employee)));
    }

    public String getVacanciesFitsLeastOneRequirement(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employee employee = (Employee) employeeDao.getUserByToken(request.getToken());
        if(employee == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            return gson.toJson(new GetListVacanciesDtoResponse(employeeDao.getVacanciesFitsLeastOneRequirement(employee)));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }


    private static void validateEmployee(RegisterEmployeeDtoRequest data) throws ServerException {
        if (data.getFirstName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_FIRST_NAME);
        }
        if (data.getLastName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_LAST_NAME);
        }
        if (data.getLogin().length() < 6) {
            throw new ServerException(ServerErrorCode.INVALID_LOGIN);
        }
        if (data.getPassword().length() < 6) {
            throw new ServerException(ServerErrorCode.INVALID_PASSWORD);
        }
        if (data.getEmail().length() < 6) {
            throw new ServerException(ServerErrorCode.INVALID_EMAIL);
        }
    }

    private static void validateEmployee(ChangeRegistrationDataDtoRequest data) throws ServerException {
        if (data.getFirstName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_FIRST_NAME);
        }
        if (data.getLastName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_LAST_NAME);
        }
        if (data.getPassword().length() < 6) {
            throw new ServerException(ServerErrorCode.INVALID_PASSWORD);
        }
        if (data.getEmail().length() < 6) {
            throw new ServerException(ServerErrorCode.INVALID_EMAIL);
        }
    }

    private static Employee createEmployee(RegisterEmployeeDtoRequest data) {
        return new Employee(data.getFirstName(), data.getLastName(), data.getPatronymic(),
                data.getEmail(), data.getLogin(), data.getPassword());
    }

    private static void validateProfile(AddProfileDtoRequest data) throws ServerException {
        for (Requirement requirement : data.getRequirements()) {
            if (requirement.getTitle().equals("")) {
                throw new ServerException(ServerErrorCode.INVALID_TITLE_REQUIREMENT);
            }
            if (requirement.getProficiencyLevel() < 1 || requirement.getProficiencyLevel() > 5) {
                throw new ServerException(ServerErrorCode.INVALID_PROFICIENCY_LEVEL);
            }
        }
    }

    private static Profile createProfile(AddProfileDtoRequest addProfileDtoRequest) {
        return new Profile(addProfileDtoRequest.getRequirements());
    }

    private static void validateRequirement(AddOrChangeSkillDtoRequest data) throws ServerException {
        if (data.getRequirement().getTitle().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_TITLE_REQUIREMENT);
        }
        if (data.getRequirement().getProficiencyLevel() < 1 || data.getRequirement().getProficiencyLevel() > 5) {
            throw new ServerException(ServerErrorCode.INVALID_PROFICIENCY_LEVEL);
        }
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
