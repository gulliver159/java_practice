package net.thumbtack.school.hiring.model.ads;

import java.util.Objects;

public class Requirement {
    private final String title;
    private final int proficiencyLevel;
    private boolean obligatory;

    public Requirement(String title, int proficiencyLevel, boolean obligatory) {
        this.title = title;
        this.proficiencyLevel = proficiencyLevel;
        this.obligatory = obligatory;
    }

    public Requirement(String title, int proficiencyLevel) {
        this.title = title;
        this.proficiencyLevel = proficiencyLevel;
    }

    public String getTitle() {
        return title;
    }

    public int getProficiencyLevel() {
        return proficiencyLevel;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
