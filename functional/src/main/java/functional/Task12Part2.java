package net.thumbtack.school.functional;

import java.util.Objects;
import java.util.Optional;

class PersonOptional {
    String name;
    Optional<PersonOptional> father;
    Optional<PersonOptional> mother;

    public PersonOptional(String name, PersonOptional father, PersonOptional mother) {
        this.name = name;
        this.father = Optional.ofNullable(father);
        this.mother = Optional.ofNullable(mother);
    }

    public Optional<PersonOptional> getFather() {
        return father;
    }

    public Optional<PersonOptional> getMother() {
        return mother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonOptional that = (PersonOptional) o;
        return Objects.equals(name, that.name) && Objects.equals(father, that.father) && Objects.equals(mother, that.mother);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, father, mother);
    }
}

public class Task12Part2 {
    public static Optional<PersonOptional> getMothersMotherFather(PersonOptional person) {
        return Optional.ofNullable(person).flatMap(PersonOptional::getMother)
                .flatMap(PersonOptional::getMother)
                .flatMap(PersonOptional::getFather);
    }
}
