package net.thumbtack.school.functional;

import java.util.Objects;

class Person {
    String name;
    Person father;
    Person mother;

    public Person(String name, Person father, Person mother) {
        this.name = name;
        this.father = father;
        this.mother = mother;
    }
    public Person getFather() {
        return father;
    }
    public Person getMother() {
        return mother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(father, person.father) && Objects.equals(mother, person.mother);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, father, mother);
    }
}

public class Task12Part1 {
    public static Person getMothersMotherFather(Person person) {
        Person nowPerson = person;
        if (nowPerson.getMother() != null) {
            nowPerson = nowPerson.getMother();
        } else {
            return null;
        }

        if (nowPerson.getMother() != null) {
            nowPerson = nowPerson.getMother();
        } else {
            return null;
        }

        if (nowPerson.getFather() != null) {
            return nowPerson.getFather();
        } else {
            return null;
        }
    }
}
