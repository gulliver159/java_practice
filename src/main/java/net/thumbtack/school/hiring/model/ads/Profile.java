package net.thumbtack.school.hiring.model.ads;

import java.util.Objects;
import java.util.Set;

public class Profile {
    private final Set<Requirement> requirements;

    public Profile(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public boolean removeRequirement(String titleRequirement) {
        return requirements.removeIf(requirement -> requirement.getTitle().equals(titleRequirement));
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(requirements, profile.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirements);
    }
}
