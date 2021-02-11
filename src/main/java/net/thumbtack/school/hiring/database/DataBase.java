package net.thumbtack.school.hiring.database;

import com.google.common.collect.*;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
import net.thumbtack.school.hiring.exception.*;
import net.thumbtack.school.hiring.model.ads.*;
import net.thumbtack.school.hiring.model.users.*;

import java.io.*;
import java.util.*;

public class DataBase implements Serializable {
    static Gson gson = new Gson();

    private static DataBase instance;


    private final Map<String, User> tokensUsers = new HashMap<>();

    private final Map<String, User> loginToUser = new HashMap<>();

    private final Set<String> existTitleRequirements = new HashSet<>();

    // Для выдачи списков вакансий работнику
    private Multimap<Requirement, Vacancy> activeVacancies = MultimapBuilder.treeKeys((Comparator<Requirement>) (o1, o2) -> {
        int compTitle = o1.getTitle().compareTo(o2.getTitle());
        if (compTitle == 0)
            return o1.getProficiencyLevel() - o2.getProficiencyLevel();
        else
            return compTitle;
    }).linkedListValues().build();

    // Для выдачи списков профилей работадателю
    private Multimap<Requirement, Profile> activeProfiles = MultimapBuilder.treeKeys((Comparator<Requirement>) (o1, o2) -> {
        int compTitle = o1.getTitle().compareTo(o2.getTitle());
        if (compTitle == 0)
            return o1.getProficiencyLevel() - o2.getProficiencyLevel();
        else
            return compTitle;
    }).linkedListValues().build();

    private DataBase() {
    }

    public static DataBase getInstance() {
        return instance;
    }

    // Work with users
    public static void getDataBaseFromFile(StartAndStopDtoRequest startAndStopDtoRequest) {
        if (startAndStopDtoRequest.getFileName() == null) {
            instance = new DataBase();
        } else {
            try (BufferedReader f = new BufferedReader(new FileReader(new File(startAndStopDtoRequest.getFileName())))) {
                instance = gson.fromJson(f, DataBase.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveDataBaseToFile(StartAndStopDtoRequest startAndStopDtoRequest) {
        if (startAndStopDtoRequest.getFileName() != null) {
            try (BufferedWriter f = new BufferedWriter(new FileWriter(new File(startAndStopDtoRequest.getFileName())))) {
                gson.toJson(instance, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String registerUser(User user) throws ServerException {
        if (loginToUser.putIfAbsent(user.getLogin(), user) != null)
            throw new ServerException(ServerErrorCode.BUSY_LOGIN);

        String token = UUID.randomUUID().toString();
        tokensUsers.put(token, user);
        return token;
    }


    public String loginUser(String login, String password) throws ServerException {
        String token = UUID.randomUUID().toString();

        User user = loginToUser.get(login);
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }

        if (user.getPassword().equals(password)) {
            tokensUsers.put(token, user);
            return token;
        }
        throw new ServerException(ServerErrorCode.INCORRECT_PASSWORD);
    }

    public void logoutUser(String token) throws ServerException {
        if (tokensUsers.remove(token) == null)
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
    }


    public void leaveUser(String token) throws ServerException {
        User user = tokensUsers.remove(token);
        if (user == null)
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        loginToUser.remove(user.getLogin());
    }


    public void changeRegistrationDataUser(User user, String companyName, String address, String firstName, String lastName,
                                           String patronymic, String email, String password) {
        user.changeRegistrationData(companyName, address, firstName, lastName, patronymic, email, password);
    }


    // Work with ads
    private Vacancy findVacancy(Employer employer, String jobTitle) throws ServerException {
        Vacancy vacancy = Iterables.find(employer.getVacancies(), (Vacancy v) -> v.getJobTitle().equals(jobTitle), null);
        if (vacancy != null) {
            return vacancy;
        }
        throw new ServerException(ServerErrorCode.VACANCY_NOT_FOUND);
    }

    private void distributionBySkill(Profile profile) {
        for (Requirement requirement : profile.getRequirements()) {
            activeProfiles.put(requirement, profile);
        }
    }

    private void distributionBySkill(Vacancy vacancy) {
        for (Requirement requirement : vacancy.getRequirements()) {
            activeVacancies.put(requirement, vacancy);
        }
    }


    public void addVacancy(Employer employer, Vacancy vacancy) {
        employer.addVacancy(vacancy);
        distributionBySkill(vacancy);
        for (Requirement requirement : vacancy.getRequirements()) {
            existTitleRequirements.add(requirement.getTitle());
        }
    }

    public void removeVacancy(Employer employer, String jobTitle) throws ServerException {
        Vacancy vacancy = findVacancy(employer, jobTitle);
        employer.removeVacancy(vacancy);
        for (Requirement requirement : vacancy.getRequirements()) {
            activeVacancies.remove(requirement, vacancy);
        }
    }

    public void addProfile(Employee employee, Profile profile) {
        employee.setProfile(profile);
        distributionBySkill(profile);

        for (Requirement requirement : profile.getRequirements()) {
            existTitleRequirements.add(requirement.getTitle());
        }
    }

    public void removeProfile(Employee employee) {
        for (Requirement requirement : employee.getProfile().getRequirements()) {
            activeProfiles.remove(requirement, employee.getProfile());
        }
        employee.setProfile(null);
    }


    public void markProfileAsInactive(Employee employee) {
        for (Requirement requirement : employee.getProfile().getRequirements()) {
            activeProfiles.remove(requirement, employee.getProfile());
        }
    }

    public void markProfileAsActive(Employee employee) {
        distributionBySkill(employee.getProfile());
    }


    public void markVacancyAsInactive(Employer employer, String jobTitle) throws ServerException {
        Vacancy vacancy = findVacancy(employer, jobTitle);
        vacancy.setActive(false);
        for (Requirement requirement : vacancy.getRequirements()) {
            activeVacancies.remove(requirement, vacancy);
        }
    }

    public void markVacancyAsActive(Employer employer, String jobTitle) throws ServerException {
        Vacancy vacancy = findVacancy(employer, jobTitle);
        vacancy.setActive(true);
        distributionBySkill(vacancy);
    }


    public void addRequirementInProfile(Employee employee, Requirement requirement) {
        employee.getProfile().addRequirement(requirement);
        existTitleRequirements.add(requirement.getTitle());
        activeProfiles.put(requirement, employee.getProfile());
    }

    public void removeRequirementInProfile(Employee employee, String titleRequirement) throws ServerException {
        Requirement requirement = Iterables.find(employee.getProfile().getRequirements(),
                (Requirement r) -> r.getTitle().equals(titleRequirement), null);
        if (requirement == null)
            throw new ServerException(ServerErrorCode.SKILL_NOT_FOUND);
        employee.getProfile().getRequirements().remove(requirement);
        activeProfiles.remove(requirement, employee.getProfile());
    }

    public void changeRequirementInProfile(Employee employee, Requirement requirement) throws ServerException {
        removeRequirementInProfile(employee, requirement.getTitle());
        addRequirementInProfile(employee, requirement);
    }


    public void addRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException {
        Vacancy vacancy = findVacancy(employer, jobTitle);
        vacancy.addRequirement(requirement);
        existTitleRequirements.add(requirement.getTitle());
        activeVacancies.put(requirement, vacancy);
    }

    public void removeRequirementInVacancy(Employer employer, String jobTitle, String titleRequirement) throws ServerException {
        Vacancy vacancy = findVacancy(employer, jobTitle);
        Requirement requirement = Iterables.find(vacancy.getRequirements(),
                (Requirement r) -> r.getTitle().equals(titleRequirement), null);
        if (requirement == null)
            throw new ServerException(ServerErrorCode.REQUIREMENT_NOT_FOUND);
        activeVacancies.remove(requirement, vacancy);
    }

    public void changeRequirementInVacancy(Employer employer, String jobTitle, Requirement requirement) throws ServerException {
        removeRequirementInVacancy(employer, jobTitle, requirement.getTitle());
        addRequirementInVacancy(employer, jobTitle, requirement);
    }


    // Геттеры
    public Set<Vacancy> getActiveVacancies(Employer employer) {
        Set<Vacancy> vacancies = new HashSet<>();
        for (Vacancy vacancy : employer.getVacancies()) {
            if (vacancy.isActive())
                vacancies.add(vacancy);
        }
        return vacancies;
    }

    public Set<Vacancy> getInactiveVacancies(Employer employer) {
        Set<Vacancy> vacancies = new HashSet<>();
        for (Vacancy vacancy : employer.getVacancies()) {
            if (!vacancy.isActive())
                vacancies.add(vacancy);
        }
        return vacancies;
    }

    public Set<Vacancy> getAllVacancies(Employer employer) {
        return employer.getVacancies();
    }


    public Set<String> getExistRequirements() {
        return existTitleRequirements;
    }




    public Set<Vacancy> getVacanciesWhereAllReqAccountLevel(Employee employee) {
        return getVacancies(employee, true, false, false);
    }

    public Set<Vacancy> getVacanciesWhereNecessaryReqAccountLevel(Employee employee) {
        return getVacancies(employee, true, true, false);
    }

    public Set<Vacancy> getVacanciesWhereAllReqNotAccountLevel(Employee employee) {
        return getVacancies(employee, false, false, false);
    }

    public List<Vacancy> getVacanciesFitsLeastOneRequirement(Employee employee) throws ServerException {
        List<Vacancy> readyVacancies = new ArrayList<>(getVacancies(employee, true, false, true));
        Set<Requirement> skills = employee.getProfile().getRequirements();
        readyVacancies.sort((v1, v2) -> getNumberSuitableSkills(skills, v2.getRequirements()) - getNumberSuitableSkills(skills, v1.getRequirements()));
        return readyVacancies;
    }

    private Set<Vacancy> getVacancies(Employee employee, boolean isCheckLevel, boolean isFilter, boolean isLeast) {
        Set<Vacancy> unsortedVacancies = new HashSet<>();
        SortedMap<Requirement, Collection<Vacancy>> treeMap = (SortedMap<Requirement, Collection<Vacancy>>) activeVacancies.asMap();
        Set<Requirement> skills = employee.getProfile().getRequirements();

        for (Requirement skill : skills) {
            Set<Vacancy> setVacanciesThatMeetSkill = returnSetVacanciesThatMeetSkill(treeMap, skill, isCheckLevel);
            unsortedVacancies.addAll(setVacanciesThatMeetSkill);
        }

        Set<Vacancy> readyVacancies = new HashSet<>(unsortedVacancies);
        if (!isLeast)
            readyVacancies = checkingUnnecessaryRequirementsInVacancies(unsortedVacancies, skills, isCheckLevel, isFilter);

        return readyVacancies;
    }

    private Set<Vacancy> checkingUnnecessaryRequirementsInVacancies(Set<Vacancy> unsortedVacancies, Set<Requirement> skills, boolean isCheckLevel, boolean isFilter) {
        Set<Vacancy> readyVacancies = new HashSet<>();
        for (Vacancy vacancy : unsortedVacancies) {
            Set<Requirement> requirements = vacancy.getRequirements();
            if (isFilter) {
                requirements = filterRequirementsForNecessary(requirements);
            }
            if (skills.containsAll(requirements)) {
                if (isCheckLevel) {
                    if (checkLevel(skills, requirements)) {
                        readyVacancies.add(vacancy);
                    }
                } else {
                    readyVacancies.add(vacancy);
                }
            }
        }
        return readyVacancies;
    }

    private boolean checkLevel(Set<Requirement> skills, Set<Requirement> requirements) {
        for (Requirement requirement : requirements) {
            Requirement skill = Iterables.find(skills, (Requirement s) -> s.getTitle().equals(requirement.getTitle()));
            if (skill.getProficiencyLevel() < requirement.getProficiencyLevel()) {
                return false;
            }
        }
        return true;
    }

    private Set<Vacancy> returnSetVacanciesThatMeetSkill(SortedMap<Requirement, Collection<Vacancy>> treeMap, Requirement skill, boolean isCheckLevel) {
        SortedMap<Requirement, Collection<Vacancy>> subMap;
        if (isCheckLevel)
            subMap = treeMap.subMap(new Requirement(skill.getTitle(), 1),
                    new Requirement(skill.getTitle(), skill.getProficiencyLevel() + 1));
        else
            subMap = treeMap.subMap(new Requirement(skill.getTitle(), 1), new Requirement(skill.getTitle(), 6));
        Set<Vacancy> unionSet = new HashSet<>();
        for (Collection<Vacancy> collection : subMap.values()) {
            unionSet.addAll(collection);
        }
        return unionSet;
    }

    private int getNumberSuitableSkills(Set<Requirement> skills, Set<Requirement> requirements) {
        int numberSuitableSkills = 0;
        for (Requirement requirement : requirements) {
            Requirement skill = Iterables.find(skills, (Requirement s) -> s.getTitle().equals(requirement.getTitle()));
            if (skill.getProficiencyLevel() >= requirement.getProficiencyLevel()) {
                numberSuitableSkills++;
            }
        }
        return numberSuitableSkills;
    }

    private Set<Requirement> filterRequirementsForNecessary(Set<Requirement> requirements) {
        Set<Requirement> necessaryRequirements = new HashSet<>();
        for (Requirement requirement : requirements) {
            if (requirement.isObligatory()) {
                necessaryRequirements.add(requirement);
            }
        }
        return necessaryRequirements;
    }



    public Set<Profile> getProfilesWhereAllReqAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return getProfiles(employer, jobTitle, true, false, false);
    }

    public Set<Profile> getProfilesWhereNecessaryReqAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return getProfiles(employer, jobTitle, true, true, false);
    }

    public Set<Profile> getProfilesWhereAllReqNotAccountLevel(Employer employer, String jobTitle) throws ServerException {
        return getProfiles(employer, jobTitle, false, false, false);
    }

    public Set<Profile> getProfilesFitsLeastOneRequirement(Employer employer, String jobTitle) throws ServerException {
        return getProfiles(employer, jobTitle, true, false, true);
    }

    private Set<Profile> getProfiles(Employer employer, String jobTitle, boolean isCheckLevel, boolean isFilter, boolean isLeast) throws ServerException {
        Set<Profile> readyProfiles = new HashSet<>();
        SortedMap<Requirement, Collection<Profile>> treeMap = (SortedMap<Requirement, Collection<Profile>>) activeProfiles.asMap();
        Vacancy vacancy = findVacancy(employer, jobTitle);

        Set<Requirement> requirements;
        if (isFilter)
            requirements = filterRequirementsForNecessary(vacancy.getRequirements());
        else
            requirements = vacancy.getRequirements();

        for (Requirement requirement : requirements) {
            // Получение set всех профилей, подходящих под данное требование
            Set<Profile> setProfilesThatMeetRequirement = returnSetProfilesThatMeetRequirement(treeMap, requirement, isCheckLevel);
            // Объединение ии пересечение множеств, в зависимости от того, все требования или хотя бы одно
            if (isLeast) {
                readyProfiles.addAll(setProfilesThatMeetRequirement);
            } else {
                if (readyProfiles.size() == 0)
                    readyProfiles.addAll(setProfilesThatMeetRequirement);
                else
                    readyProfiles.retainAll(setProfilesThatMeetRequirement);
            }
        }
        return readyProfiles;
    }

    private Set<Profile> returnSetProfilesThatMeetRequirement(SortedMap<Requirement, Collection<Profile>> treeMap, Requirement requirement, boolean isCheckLevel) {
        SortedMap<Requirement, Collection<Profile>> subMap;
        if (isCheckLevel)
            subMap = treeMap.subMap(requirement, new Requirement(requirement.getTitle(), 6));
        else
            subMap = treeMap.subMap(new Requirement(requirement.getTitle(), 1), new Requirement(requirement.getTitle(), 6));
        Set<Profile> unionSet = new HashSet<>();
        for (Collection<Profile> collection : subMap.values()) {
            unionSet.addAll(collection);
        }
        return unionSet;
    }


    public void hiringEmployee(Employer employer, String jobTitle, String loginEmployee) throws ServerException {
        markVacancyAsInactive(employer, jobTitle);

        Employee employee = (Employee) loginToUser.get(loginEmployee);
        if (employee == null) {
            throw new ServerException(ServerErrorCode.EMPLOYEE_NOT_FOUND);
        }
        removeProfile(employee);
    }

    public User getUserByToken(String token) {
        return tokensUsers.get(token);
    }
}
