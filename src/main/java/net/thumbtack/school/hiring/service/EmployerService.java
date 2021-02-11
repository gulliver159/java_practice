package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.ads.*;
import net.thumbtack.school.hiring.dto.request.users.*;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.dto.response.ads.*;
import net.thumbtack.school.hiring.dto.response.users.*;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.ads.Vacancy;
import net.thumbtack.school.hiring.model.users.Employer;
import net.thumbtack.school.hiring.model.users.User;

public class EmployerService {
    private final Gson gson = new Gson();
    EmployerDao employerDao = new EmployerDaoImpl();

    public String registerEmployer(String requestJsonString) {
        String token;
        RegisterEmployerDtoRequest request = gson.fromJson(requestJsonString, RegisterEmployerDtoRequest.class);

        try {
            validateEmployer(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        Employer employer = createEmployer(request);
        try {
            token = employerDao.registerEmployer(employer);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        return gson.toJson(new LoginOrRegisterDtoResponse(token));
    }

    public String loginEmployer(String requestJsonString) {
        String token;
        LoginDtoRequest request = gson.fromJson(requestJsonString, LoginDtoRequest.class);

        try {
            token = employerDao.loginEmployer(request.getLogin(), request.getPassword());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        return gson.toJson(new LoginOrRegisterDtoResponse(token));
    }

    public String logoutEmployer(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);
        try {
            employerDao.logoutEmployer(request.getToken());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String leaveEmployer(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);
        try {
            employerDao.leaveEmployer(request.getToken());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String addVacancy(String requestJsonString) {
        AddVacancyDtoRequest request = gson.fromJson(requestJsonString, AddVacancyDtoRequest.class);
        try {
            validateVacancy(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        Vacancy vacancy = createVacancy(request);

        employerDao.addVacancy(employer, vacancy);
        return gson.toJson("");
    }

    public String markVacancyAsInactive(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request =
                gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.markVacancyAsInactive(employer, request.getJobTitle());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        return gson.toJson("");
    }

    public String markVacancyAsActive(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.markVacancyAsActive(employer, request.getJobTitle());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String changeRegistrationDataEmployer(String requestJsonString) {
        ChangeRegistrationDataDtoRequest request =
                gson.fromJson(requestJsonString, ChangeRegistrationDataDtoRequest.class);

        try {
            validateEmployer(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        User user = employerDao.getUserByToken(request.getToken());
        if(user == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        employerDao.changeRegistrationDataEmployer(user, request.getCompanyName(), request.getAddress(),
                request.getFirstName(), request.getLastName(), request.getPatronymic(),
                request.getEmail(), request.getPassword());

        return gson.toJson("");
    }

    public String getActiveVacancies(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employerDao.getActiveVacancies(employer)));
    }

    public String getInactiveVacancies(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employerDao.getInactiveVacancies(employer)));
    }

    public String getAllVacancies(String requestJsonString) {
        ContainsTokenDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        return gson.toJson(new GetSetVacanciesDtoResponse(employerDao.getAllVacancies(employer)));
    }

    public String removeVacancy(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.removeVacancy(employer, request.getJobTitle());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String addRequirementInVacancy(String requestJsonString) {
        AddOrChangeRequirementDtoRequest request =
                gson.fromJson(requestJsonString, AddOrChangeRequirementDtoRequest.class);
        try {
            validateRequirement(request);
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.addRequirementInVacancy(employer, request.getJobTitle(), request.getRequirement());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String removeRequirementInVacancy(String requestJsonString) {
        RemoveRequirementDtoRequest request =
                gson.fromJson(requestJsonString, RemoveRequirementDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.removeRequirementInVacancy(employer, request.getJobTitle(), request.getTitleRequirement());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    public String changeRequirementInVacancy(String requestJsonString) {
        AddOrChangeRequirementDtoRequest request
                = gson.fromJson(requestJsonString, AddOrChangeRequirementDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            validateRequirement(request);
            employerDao.changeRequirementInVacancy(employer, request.getJobTitle(), request.getRequirement());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }

    private static void validateRequirement(AddOrChangeRequirementDtoRequest data) throws ServerException {
        if (data.getRequirement().getTitle().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_TITLE_REQUIREMENT);
        }
        if (data.getRequirement().getProficiencyLevel() < 1 || data.getRequirement().getProficiencyLevel() > 5) {
            throw new ServerException(ServerErrorCode.INVALID_PROFICIENCY_LEVEL);
        }
    }

    public String getExistRequirements() {
        return gson.toJson(new GetRequirementsDtoResponse(employerDao.getExistRequirements()));
    }

    public String getEmployeesWhereAllReqAccountLevel(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            return gson.toJson(new GetProfilesDtoResponse(employerDao.getEmployeesWhereAllReqAccountLevel(employer, request.getJobTitle())));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getEmployeesWhereNecessaryReqAccountLevel(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            return gson.toJson(new GetProfilesDtoResponse(employerDao.getEmployeesWhereNecessaryReqAccountLevel(employer, request.getJobTitle())));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getEmployeesWhereAllReqNotAccountLevel(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            return gson.toJson(new GetProfilesDtoResponse(employerDao.getEmployeesWhereAllReqNotAccountLevel(employer, request.getJobTitle())));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getEmployeesFitsLeastOneRequirement(String requestJsonString) {
        ContainsTokenAndNameVacancyDtoRequest request = gson.fromJson(requestJsonString, ContainsTokenAndNameVacancyDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            return gson.toJson(new GetProfilesDtoResponse(employerDao.getEmployeesFitsLeastOneRequirement(employer, request.getJobTitle())));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String hiringEmployee(String requestJsonString) {
        HiringEmployeeDtoRequest request = gson.fromJson(requestJsonString, HiringEmployeeDtoRequest.class);

        Employer employer = (Employer) employerDao.getUserByToken(request.getToken());
        if(employer == null)
            return gson.toJson(new ErrorDtoResponse(ServerErrorCode.TOKEN_NOT_FOUND));

        try {
            employerDao.hiringEmployee(employer, request.getJobTitle(), request.getLoginEmployee());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
        return gson.toJson("");
    }


    private static void validateEmployer(RegisterEmployerDtoRequest data) throws ServerException {
        // Проверка валидности полученных данных
        if (data.getCompanyName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_NAME_COMPANY);
        }
        if (data.getAddress().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_ADDRESS);
        }
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

    private static void validateEmployer(ChangeRegistrationDataDtoRequest data) throws ServerException {
        // Проверка валидности полученных данных
        if (data.getCompanyName().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_NAME_COMPANY);
        }
        if (data.getAddress().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_ADDRESS);
        }
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

    private static Employer createEmployer(RegisterEmployerDtoRequest data) {
        return new Employer(data.getCompanyName(), data.getAddress(), data.getFirstName(), data.getLastName(),
                data.getPatronymic(), data.getEmail(), data.getLogin(), data.getPassword());
    }

    private static void validateVacancy(AddVacancyDtoRequest addVacancyDtoRequest) throws ServerException {
        for (Requirement requirement : addVacancyDtoRequest.getRequirements()) {
            if (requirement.getTitle().equals("")) {
                throw new ServerException(ServerErrorCode.INVALID_TITLE_REQUIREMENT);
            }
            if (requirement.getProficiencyLevel() < 1 || requirement.getProficiencyLevel() > 5) {
                throw new ServerException(ServerErrorCode.INVALID_PROFICIENCY_LEVEL);
            }
        }
        if (addVacancyDtoRequest.getJobTitle().equals("")) {
            throw new ServerException(ServerErrorCode.INVALID_JOB_TITLE);
        }
        if (addVacancyDtoRequest.getSalary() < 0) {
            throw new ServerException(ServerErrorCode.INVALID_SALARY);
        }
    }

    private static Vacancy createVacancy(AddVacancyDtoRequest addVacancyDtoRequest) {
        return new Vacancy(addVacancyDtoRequest.getJobTitle(), addVacancyDtoRequest.getSalary(),
                addVacancyDtoRequest.getRequirements());
    }

    public void setEmployerDao(EmployerDao employerDao) {
        this.employerDao = employerDao;
    }
}
