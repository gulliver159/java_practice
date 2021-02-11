package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.ads.*;
import net.thumbtack.school.hiring.model.users.Employee;
import net.thumbtack.school.hiring.model.users.User;

import java.util.List;
import java.util.Set;

public interface EmployeeDao {

    String registerEmployee(Employee employee) throws ServerException;

    String loginEmployee(String login, String password) throws ServerException;

    void logoutEmployee(String token) throws ServerException;

    void leaveEmployee(String token) throws ServerException;

    void changeRegistrationDataEmployee(User user, String companyName, String address, String firstName, String lastName,
                                        String patronymic, String email, String password);

    void addProfile(Employee employee, Profile profile);

    void removeProfile(Employee employee);

    void markProfileAsInactive(Employee employee);

    void markProfileAsActive(Employee employee);

    void addRequirementInProfile(Employee employee, Requirement requirement);

    void removeRequirementInProfile(Employee employee, String titleRequirement) throws ServerException;

    void changeRequirementInProfile(Employee employee, Requirement requirement) throws ServerException;

    Set<String> getExistSkills();

    Set<Vacancy> getVacanciesWhereAllReqAccountLevel(Employee employee);

    Set<Vacancy> getVacanciesWhereNecessaryReqAccountLevel(Employee employee);

    Set<Vacancy> getVacanciesWhereAllReqNotAccountLevel(Employee employee);

    List<Vacancy> getVacanciesFitsLeastOneRequirement(Employee employee) throws ServerException;

    User getUserByToken(String token);
}
