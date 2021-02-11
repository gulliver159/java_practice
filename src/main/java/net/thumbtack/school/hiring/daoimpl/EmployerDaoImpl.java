package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.ads.Profile;
import net.thumbtack.school.hiring.model.ads.Requirement;
import net.thumbtack.school.hiring.model.ads.Vacancy;
import net.thumbtack.school.hiring.model.users.Employer;
import net.thumbtack.school.hiring.model.users.User;

import java.util.Set;

public class EmployerDaoImpl implements EmployerDao {

    private DataBase database = DataBase.getInstance();

    public String registerEmployer(Employer employer) throws ServerException {
        database = DataBase.getInstance();
        return database.registerUser(employer);
    }

    public String loginEmployer(String login, String password) throws ServerException {
        return database.loginUser(login, password);
    }

    public void logoutEmployer(String token) throws ServerException {
        database.logoutUser(token);
    }

    public void leaveEmployer(String token) throws ServerException {
        database.leaveUser(token);
    }

    public void changeRegistrationDataEmployer(User user, String companyName, String address, String firstName, String lastName,
                                               String patronymic, String email, String password) {
        database.changeRegistrationDataUser(user, companyName, address, firstName, lastName, patronymic, email, password);
    }

    public void addVacancy(Employer employer, Vacancy vacancy) {
        database.addVacancy(employer, vacancy);
    }

    public void removeVacancy(Employer employer, String jobTitle) throws ServerException {
        database.removeVacancy(employer, jobTitle);
    }

    public void markVacancyAsInactive(Employer employer, String jobTitle) throws ServerException {
        database.markVacancyAsInactive(employer, jobTitle);
    }

    public void markVacancyAsActive(Employer employer, String jobTitle) throws ServerException {
        database.markVacancyAsActive(employer, jobTitle);
    }

    public Set<Vacancy> getActiveVacancies(Employer employer) {
        return database.getActiveVacancies(employer);
    }

    public Set<Vacancy> getInactiveVacancies(Employer employer) {
        return database.getInactiveVacancies(employer);
    }

    public Set<Vacancy> getAllVacancies(Employer employer) {
        return database.getAllVacancies(employer);
    }

    public void addRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException {
        database.addRequirementInVacancy(employer, jobTitle, requirement);
    }

    public void removeRequirementInVacancy(Employer employer, String jobTitle, String titleRequirement) throws ServerException {
        database.removeRequirementInVacancy(employer, jobTitle, titleRequirement);
    }

    public void changeRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException {
        database.changeRequirementInVacancy(employer, jobTitle, requirement);
    }

    public Set<String> getExistRequirements() {
        return database.getExistRequirements();
    }

    public Set<Profile> getEmployeesWhereAllReqAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return database.getProfilesWhereAllReqAccountLevel(employer, jobTitle);
    }

    public Set<Profile> getEmployeesWhereNecessaryReqAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return database.getProfilesWhereNecessaryReqAccountLevel(employer, jobTitle);
    }

    public Set<Profile> getEmployeesWhereAllReqNotAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return database.getProfilesWhereAllReqNotAccountLevel(employer, jobTitle);
    }

    public Set<Profile> getEmployeesFitsLeastOneRequirement(Employer employer, String jobTitle) throws ServerException {
        return database.getProfilesFitsLeastOneRequirement(employer, jobTitle);
    }


    public void hiringEmployee(Employer employer, String jobTitle, String loginEmployee) throws ServerException {
        database.hiringEmployee(employer, jobTitle, loginEmployee);
    }

    public User getUserByToken(String token) {
        return database.getUserByToken(token);
    }
}
