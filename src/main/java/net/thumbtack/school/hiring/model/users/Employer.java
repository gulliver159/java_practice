package net.thumbtack.school.hiring.model.users;

import net.thumbtack.school.hiring.model.ads.Vacancy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employer extends User {
    private String companyName;
    private String address;

    private final Set<Vacancy> vacancies = new HashSet<>();

    public Employer(String companyName, String address, String firstName, String lastName,
                    String patronymic, String email, String login, String password) {
        super(firstName, lastName, patronymic, email, login, password);
        this.companyName = companyName;
        this.address = address;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public void removeVacancy(Vacancy vacancy) {
        vacancies.remove(vacancy);
    }

    public void changeRegistrationData(String companyName, String address, String firstName, String lastName,
                                       String patronymic, String email, String password) {
        super.changeRegistrationData(companyName, address, firstName, lastName, patronymic, email, password);
        this.companyName = companyName;
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(companyName, employer.companyName) &&
                Objects.equals(address, employer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, address);
    }
}
