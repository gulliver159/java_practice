package net.thumbtack.school.hiring.model.ads;

import java.util.Objects;
import java.util.Set;

public class Vacancy {
    private final String jobTitle;
    private final int salary;
    private final Set<Requirement> requirements;
    private boolean active = true;

    public Vacancy(String jobTitle, int salary, Set<Requirement> requirements) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.requirements = requirements;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public boolean removeRequirement(String titleRequirement) {
        return requirements.removeIf(requirement -> requirement.getTitle().equals(titleRequirement));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary &&
                Objects.equals(jobTitle, vacancy.jobTitle) &&
                Objects.equals(requirements, vacancy.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, salary, requirements);
    }
}
