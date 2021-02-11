package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.ads.*;
import net.thumbtack.school.hiring.model.users.Employee;
import net.thumbtack.school.hiring.model.users.User;

import java.util.List;
import java.util.Set;

public class EmployeeDaoImpl implements EmployeeDao {

    private DataBase database = DataBase.getInstance();

    public String registerEmployee(Employee employee) throws ServerException {
        database = DataBase.getInstance();
        return database.registerUser(employee);
    }

    public String loginEmployee(String login, String password) throws ServerException {
        return database.loginUser(login, password);
    }

    public void logoutEmployee(String token) throws ServerException {
        database.logoutUser(token);
    }

    public void leaveEmployee(String token) throws ServerException {
        database.leaveUser(token);
    }

    public void changeRegistrationDataEmployee(User user, String companyName, String address, String firstName, String lastName,
                                               String patronymic, String email, String password) {
        database.changeRegistrationDataUser(user, companyName, address, firstName, lastName, patronymic, email, password);
    }

    public void addProfile(Employee employee, Profile profile) {
        database.addProfile(employee, profile);
    }

    public void removeProfile(Employee employee){
        database.removeProfile(employee);
    }

    public void markProfileAsInactive(Employee employee){
        database.markProfileAsInactive(employee);
    }

    public void markProfileAsActive(Employee employee) {
        database.markProfileAsActive(employee);
    }

    public void addRequirementInProfile(Employee employee, Requirement requirement) {
        database.addRequirementInProfile(employee, requirement);
    }

    public void removeRequirementInProfile(Employee employee, String titleRequirement) throws ServerException {
        database.removeRequirementInProfile(employee, titleRequirement);
    }

    public void changeRequirementInProfile(Employee employee, Requirement requirement) throws ServerException {
        database.changeRequirementInProfile(employee, requirement);
    }

    public Set<String> getExistSkills() {
        return database.getExistRequirements();
    }

    public Set<Vacancy> getVacanciesWhereAllReqAccountLevel(Employee employee) {
        return database.getVacanciesWhereAllReqAccountLevel(employee);
    }

    public Set<Vacancy> getVacanciesWhereNecessaryReqAccountLevel(Employee employee) {
        return database.getVacanciesWhereNecessaryReqAccountLevel(employee);
    }

    public Set<Vacancy> getVacanciesWhereAllReqNotAccountLevel(Employee employee) {
        return database.getVacanciesWhereAllReqNotAccountLevel(employee);
    }

    public List<Vacancy> getVacanciesFitsLeastOneRequirement(Employee employee) throws ServerException {
        return database.getVacanciesFitsLeastOneRequirement(employee);
    }

    public User getUserByToken(String token) {
        return database.getUserByToken(token);
    }
}
