    package net.thumbtack.school.hiring;

    import com.google.gson.Gson;
    import net.thumbtack.school.hiring.dto.request.StartAndStopDtoRequest;
    import net.thumbtack.school.hiring.dto.request.ads.*;
    import net.thumbtack.school.hiring.dto.request.users.*;
    import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
    import net.thumbtack.school.hiring.dto.response.ads.*;
    import net.thumbtack.school.hiring.exception.*;
    import net.thumbtack.school.hiring.model.ads.*;
    import org.junit.Before;
    import org.junit.Test;

    import java.util.HashSet;
    import java.util.Set;

    import static org.junit.Assert.*;
    import static org.junit.jupiter.api.Assertions.assertAll;

    public class TestServerForDataChanges {

        private final Gson gson = new Gson();
        private final Server server = new Server();
        private String tokenEmployer, tokenEmployee;

        @Before
        public void startServer() {
            server.startServer(gson.toJson(new StartAndStopDtoRequest(null)));
            RegisterEmployerDtoRequest requestRegisterEmployer = new RegisterEmployerDtoRequest(
                    "Tamtek", "Krupskaya 6", "Mike", "Voronov", "Andreevich",
                    "mike@12", "mike2001", "847562196"
            );
            RegisterEmployeeDtoRequest requestRegisterEmployee = new RegisterEmployeeDtoRequest(
                    "Katya", "Rogozhina", "katya@12", "katya2001", "02200220"
            );

            tokenEmployer = server.registerEmployer(gson.toJson(requestRegisterEmployer));
            tokenEmployee = server.registerEmployee(gson.toJson(requestRegisterEmployee));
        }

        @Test
        public void testChangeRegistrationData() {
            String errorPassword = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PASSWORD));
            String errorFirstName = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_FIRST_NAME));

            ChangeRegistrationDataDtoRequest changeRegistrationDataDtoRequest1 = new ChangeRegistrationDataDtoRequest(
                    tokenEmployer, "Tamtek", "Krupskaya 6", "Mike", "Voronov",
                    "Andreevich", "mike@12", "9645"
            );
            ChangeRegistrationDataDtoRequest changeRegistrationDataDtoRequest2 = new ChangeRegistrationDataDtoRequest(
                    tokenEmployee, "", "Rogozhina", null, "rogozhina2001", "02200220"
            );

            String serverResult1 = server.changeRegistrationDataEmployer(gson.toJson(changeRegistrationDataDtoRequest1));
            String serverResult2 = server.changeRegistrationDataEmployee(gson.toJson(changeRegistrationDataDtoRequest2));

            assertAll(
                    () -> assertEquals(errorPassword, serverResult1),
                    () -> assertEquals(errorFirstName, serverResult2)
            );
        }

        @Test
        public void testChangeActivityVacancyAndGetVacancies() {
            Set<Requirement> requirements1 = new HashSet<>();
            requirements1.add(new Requirement("python", 3, false));
            requirements1.add(new Requirement("java", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

            Set<Requirement> requirements2 = new HashSet<>();
            requirements2.add(new Requirement("sql", 3, false));
            requirements2.add(new Requirement("java", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirements2);

            Set<Requirement> requirements3 = new HashSet<>();
            requirements3.add(new Requirement("c#", 2, false));
            requirements3.add(new Requirement("c++", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest3 = new AddVacancyDtoRequest(tokenEmployer, "Manager", 40000, requirements3);

            server.addVacancy(gson.toJson(addVacancyDtoRequest1));
            server.addVacancy(gson.toJson(addVacancyDtoRequest2));
            server.addVacancy(gson.toJson(addVacancyDtoRequest3));

            server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));
            server.markVacancyAsInactive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Tester")));

            server.markVacancyAsActive(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));

            Set<Vacancy> vacanciesActive = new HashSet<>();
            vacanciesActive.add(new Vacancy("Manager", 40000, requirements3));
            vacanciesActive.add(new Vacancy("Programmer", 50000, requirements1));

            Set<Vacancy> vacanciesInactive = new HashSet<>();
            vacanciesInactive.add(new Vacancy("Tester", 30000, requirements2));

            Set<Vacancy> vacanciesAll = new HashSet<>();
            vacanciesAll.add(new Vacancy("Programmer", 50000, requirements1));
            vacanciesAll.add(new Vacancy("Tester", 30000, requirements2));
            vacanciesAll.add(new Vacancy("Manager", 40000, requirements3));

            String serverResultActive = server.getActiveVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
            String serverResultInactive = server.getInactiveVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
            String serverResultAll = server.getAllVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));

            Set<Vacancy> resultServerVacanciesActive = gson.fromJson(serverResultActive, GetSetVacanciesDtoResponse.class).getVacancies();
            Set<Vacancy> resultServerVacanciesInactive = gson.fromJson(serverResultInactive, GetSetVacanciesDtoResponse.class).getVacancies();
            Set<Vacancy> resultServerVacanciesAll = gson.fromJson(serverResultAll, GetSetVacanciesDtoResponse.class).getVacancies();

            assertAll(
                    () -> assertEquals(resultServerVacanciesActive, vacanciesActive),
                    () -> assertEquals(resultServerVacanciesInactive, vacanciesInactive),
                    () -> assertEquals(resultServerVacanciesAll, vacanciesAll)
            );
        }

        @Test
        public void testRemoveVacancy() {
            Set<Requirement> requirements1 = new HashSet<>();
            requirements1.add(new Requirement("python", 3, false));
            requirements1.add(new Requirement("java", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements1);

            Set<Requirement> requirements2 = new HashSet<>();
            requirements2.add(new Requirement("sql", 3, false));
            requirements2.add(new Requirement("java", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest2 = new AddVacancyDtoRequest(tokenEmployer, "Tester", 30000, requirements2);

            server.addVacancy(gson.toJson(addVacancyDtoRequest1));
            server.addVacancy(gson.toJson(addVacancyDtoRequest2));

            server.removeVacancy(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Programmer")));

            Set<Vacancy> vacancies = new HashSet<>();
            vacancies.add(new Vacancy("Tester", 30000, requirements2));

            String serverResult1 = server.getAllVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
            assertEquals(gson.toJson(new GetSetVacanciesDtoResponse(vacancies)), serverResult1);

            server.removeVacancy(gson.toJson(new ContainsTokenAndNameVacancyDtoRequest(tokenEmployer, "Tester")));
            String serverResult2 = server.getAllVacancies(gson.toJson(new ContainsTokenDtoRequest(tokenEmployer)));
            assertEquals(gson.toJson(new GetSetVacanciesDtoResponse(new HashSet<>())), serverResult2);
        }

        @Test
        public void testAddRemoveChangeRequirementInProfileWhenException() {
            String errorSkillNotFound = gson.toJson(new ErrorDtoResponse(ServerErrorCode.SKILL_NOT_FOUND));
            String errorInvalidTitleSkill = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_TITLE_REQUIREMENT));
            String errorInvalidLevelSkill = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PROFICIENCY_LEVEL));

            Set<Requirement> requirements = new HashSet<>();
            requirements.add(new Requirement("python", 2));
            requirements.add(new Requirement("java", 4));
            AddProfileDtoRequest addProfileDtoRequest1 = new AddProfileDtoRequest(tokenEmployee, requirements);

            server.addProfile(gson.toJson(addProfileDtoRequest1));

            String resultServerAdd = server.addRequirementInProfile(gson.toJson(
                    new AddOrChangeSkillDtoRequest(tokenEmployee, new Requirement("", 4))
            ));

            String resultServerRemove = server.removeRequirementInProfile(gson.toJson(
                    new RemoveSkillDtoRequest(tokenEmployee, "c++")
            ));

            String resultServerChange1 = server.changeRequirementInProfile(gson.toJson(
                    new AddOrChangeSkillDtoRequest(tokenEmployee, new Requirement("sql", 4))
            ));
            String resultServerChange2 = server.changeRequirementInProfile(gson.toJson(
                    new AddOrChangeSkillDtoRequest(tokenEmployee, new Requirement("python", -6))
            ));

            assertAll(
                    () -> assertEquals(resultServerAdd, errorInvalidTitleSkill),
                    () -> assertEquals(resultServerRemove, errorSkillNotFound),
                    () -> assertEquals(resultServerChange1, errorSkillNotFound),
                    () -> assertEquals(resultServerChange2, errorInvalidLevelSkill)
            );
        }

        @Test
        public void testAddRemoveChangeRequirementInVacancyWhenException() {
            String errorInvalidTitleRequirement = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_TITLE_REQUIREMENT));
            String errorInvalidLevelRequirement = gson.toJson(new ErrorDtoResponse(ServerErrorCode.INVALID_PROFICIENCY_LEVEL));
            String errorNotFoundVacancy = gson.toJson(new ErrorDtoResponse(ServerErrorCode.VACANCY_NOT_FOUND));

            String errorRequirement = gson.toJson(new ErrorDtoResponse(ServerErrorCode.REQUIREMENT_NOT_FOUND));

            Set<Requirement> requirements = new HashSet<>();
            requirements.add(new Requirement("python", 3, false));
            requirements.add(new Requirement("java", 4, true));
            AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(tokenEmployer, "Programmer", 50000, requirements);

            server.addVacancy(gson.toJson(addVacancyDtoRequest));

            AddOrChangeRequirementDtoRequest forAdd1 = new AddOrChangeRequirementDtoRequest(tokenEmployer, "Tester",
                    new Requirement("sql", 2, false));
            AddOrChangeRequirementDtoRequest forAdd2 = new AddOrChangeRequirementDtoRequest(tokenEmployer, "Programmer",
                    new Requirement("", 4, true));

            String serverResultAdd1 = server.addRequirementInVacancy(gson.toJson(forAdd1));
            String serverResultAdd2 = server.addRequirementInVacancy(gson.toJson(forAdd2));

            AddOrChangeRequirementDtoRequest forChange1 = new AddOrChangeRequirementDtoRequest(tokenEmployer, "Programmer",
                    new Requirement("sql", 2, false));
            AddOrChangeRequirementDtoRequest forChange2 = new AddOrChangeRequirementDtoRequest(tokenEmployer, "Programmer",
                    new Requirement("python", -6, false));
            String serverResultChange1 = server.changeRequirementInVacancy(gson.toJson(forChange1));
            String serverResultChange2 = server.changeRequirementInVacancy(gson.toJson(forChange2));

            RemoveRequirementDtoRequest forRemove = new RemoveRequirementDtoRequest(tokenEmployer, "Programmer", "sql");
            String serverResultRemove = server.removeRequirementInVacancy(gson.toJson(forRemove));
            assertAll(
                    () -> assertEquals(serverResultAdd1, errorNotFoundVacancy),
                    () -> assertEquals(serverResultAdd2, errorInvalidTitleRequirement),
                    () -> assertEquals(serverResultChange1, errorRequirement),
                    () -> assertEquals(serverResultChange2, errorInvalidLevelRequirement),
                    () -> assertEquals(serverResultRemove, errorRequirement)
            );
        }
    }