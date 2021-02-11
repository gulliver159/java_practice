package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.service.*;

public class Server {
    Gson gson = new Gson();

    private EmployeeService employeeService = new EmployeeService();
    private EmployerService employerService = new EmployerService();

    public void startServer(String savedDataFileName) {
        DataBase.getDataBaseFromFile(gson.fromJson(savedDataFileName, StartAndStopDtoRequest.class));
    }

    public void stopServer(String saveDataFileName) {
        DataBase.saveDataBaseToFile(gson.fromJson(saveDataFileName, StartAndStopDtoRequest.class));
    }


    public String registerEmployee(String requestJsonString) {
        return employeeService.registerEmployee(requestJsonString);
    }

    public String registerEmployer(String requestJsonString) {
        return employerService.registerEmployer(requestJsonString);
    }


    public String loginEmployee(String requestJsonString) {
        return employeeService.loginEmployee(requestJsonString);
    }

    public String loginEmployer(String requestJsonString) {
        return employerService.loginEmployer(requestJsonString);
    }


    public String logoutEmployee(String requestJsonString) {
        return employeeService.logoutEmployee(requestJsonString);
    }

    public String logoutEmployer(String requestJsonString) {
        return employerService.logoutEmployer(requestJsonString);
    }


    public String leaveEmployee(String requestJsonString) {
        return employeeService.leaveEmployee(requestJsonString);
    }

    public String leaveEmployer(String requestJsonString) {
        return employerService.leaveEmployer(requestJsonString);
    }


    public String addVacancy(String requestJsonString) {
        return employerService.addVacancy(requestJsonString);
    }

    public String removeVacancy(String requestJsonString) {
        return employerService.removeVacancy(requestJsonString);
    }


    public String addProfile(String requestJsonString) {
        return employeeService.addProfile(requestJsonString);
    }

    public String removeProfile(String requestJsonString) {
        return employeeService.removeProfile(requestJsonString);
    }


    public String changeRegistrationDataEmployee(String requestJsonString) {
        return employeeService.changeRegistrationDataEmployee(requestJsonString);
    }

    public String changeRegistrationDataEmployer(String requestJsonString) {
        return employerService.changeRegistrationDataEmployer(requestJsonString);
    }

    public String markProfileAsActive(String requestJsonString) {
        return employeeService.markProfileAsActive(requestJsonString);
    }

    public String markProfileAsInactive(String requestJsonString) {
        return employeeService.markProfileAsInactive(requestJsonString);
    }


    public String markVacancyAsActive(String requestJsonString) {
        return employerService.markVacancyAsActive(requestJsonString);
    }

    public String markVacancyAsInactive(String requestJsonString) {
        return employerService.markVacancyAsInactive(requestJsonString);
    }

    public String getActiveVacancies(String requestJsonString) {
        return employerService.getActiveVacancies(requestJsonString);
    }

    public String getInactiveVacancies(String requestJsonString) {
        return employerService.getInactiveVacancies(requestJsonString);
    }

    public String getAllVacancies(String requestJsonString) {
        return employerService.getAllVacancies(requestJsonString);
    }


    public String addRequirementInProfile(String requestJsonString) {
        return employeeService.addRequirementInProfile(requestJsonString);
    }

    public String removeRequirementInProfile(String requestJsonString) {
        return employeeService.removeRequirementInProfile(requestJsonString);
    }

    public String changeRequirementInProfile(String requestJsonString) {
        return employeeService.changeRequirementInProfile(requestJsonString);
    }


    public String addRequirementInVacancy(String requestJsonString) {
        return employerService.addRequirementInVacancy(requestJsonString);
    }

    public String removeRequirementInVacancy(String requestJsonString) {
        return employerService.removeRequirementInVacancy(requestJsonString);
    }

    public String changeRequirementInVacancy(String requestJsonString) {
        return employerService.changeRequirementInVacancy(requestJsonString);
    }


    public String getExistSkills() {
        return employeeService.getExistSkills();
    }

    public String getExistRequirements() {
        return employerService.getExistRequirements();
    }


    public String getVacanciesWhereAllRequirementAccountLevel(String requestJsonString) {
        return employeeService.getVacanciesWhereAllReqAccountLevel(requestJsonString);
    }

    public String getVacanciesWhereNecessaryRequirementAccountLevel(String requestJsonString) {
        return employeeService.getVacanciesWhereNecessaryReqAccountLevel(requestJsonString);
    }

    public String getVacanciesWhereAllRequirementNotAccountLevel(String requestJsonString) {
        return employeeService.getVacanciesWhereAllReqNotAccountLevel(requestJsonString);
    }

    public String getVacanciesFitsLeastOneRequirement(String requestJsonString) {
        return employeeService.getVacanciesFitsLeastOneRequirement(requestJsonString);
    }


    public String getEmployeesWhereAllRequirementAccountLevel(String requestJsonString) {
        return employerService.getEmployeesWhereAllReqAccountLevel(requestJsonString);
    }

    public String getEmployeesWhereNecessaryRequirementAccountLevel(String requestJsonString) {
        return employerService.getEmployeesWhereNecessaryReqAccountLevel(requestJsonString);
    }

    public String getEmployeesWhereAllRequirementNotAccountLevel(String requestJsonString) {
        return employerService.getEmployeesWhereAllReqNotAccountLevel(requestJsonString);
    }

    public String getEmployeesFitsLeastOneRequirement(String requestJsonString) {
        return employerService.getEmployeesFitsLeastOneRequirement(requestJsonString);
    }

    public String hiringEmployee(String requestJsonString) {
        return employerService.hiringEmployee(requestJsonString);
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setEmployerService(EmployerService employerService) {
        this.employerService = employerService;
    }
}
