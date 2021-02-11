package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.ads.Profile;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.ads.Vacancy;
import net.thumbtack.school.hiring.model.users.Employer;
import net.thumbtack.school.hiring.model.users.User;

import java.util.Set;

public interface EmployerDao {

    String registerEmployer(Employer employer) throws ServerException;

    String loginEmployer(String login, String password) throws ServerException;

    void logoutEmployer(String token) throws ServerException;

    void leaveEmployer(String token) throws ServerException;

    void changeRegistrationDataEmployer(User user, String companyName, String address, String firstName, String lastName,
                                        String patronymic, String email, String password);

    void addVacancy(Employer employer, Vacancy vacancy);

    void removeVacancy(Employer employer, String jobTitle) throws ServerException;

    void markVacancyAsInactive(Employer employer, String jobTitle) throws ServerException;

    void markVacancyAsActive(Employer employer, String jobTitle) throws ServerException;

    Set<Vacancy> getActiveVacancies(Employer employer);

    Set<Vacancy> getInactiveVacancies(Employer employer);

    Set<Vacancy> getAllVacancies(Employer employer);

    void addRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException;

    void removeRequirementInVacancy(Employer employer, String jobTitle, String titleRequirement) throws ServerException;

    void changeRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException;

    Set<String> getExistRequirements();

    Set<Profile> getEmployeesWhereAllReqAccountLevel(Employer employer, String jobTitle) throws ServerException;

    Set<Profile> getEmployeesWhereNecessaryReqAccountLevel(Employer employer, String jobTitle) throws ServerException;

    Set<Profile> getEmployeesWhereAllReqNotAccountLevel(Employer employer, String jobTitle) throws ServerException;

    Set<Profile> getEmployeesFitsLeastOneRequirement(Employer employer, String jobTitle) throws ServerException;

    void hiringEmployee(Employer employer, String jobTitle, String loginEmployee) throws ServerException;

    User getUserByToken(String token);
}
