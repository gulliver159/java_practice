package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Task15_17Test {

    @Test
    void testTask15() {
        List<PersonForTask15> persons = new ArrayList<>();
        persons.add(new PersonForTask15("Katya", 52));
        persons.add(new PersonForTask15("Katya", 34));
        persons.add(new PersonForTask15("Ilya", 20));
        persons.add(new PersonForTask15("Ilya", 18));
        persons.add(new PersonForTask15("Nikolay", 32));
        persons.add(new PersonForTask15("Yana", 42));

        List<String> expectedPersons = new ArrayList<>();
        expectedPersons.add("Yana");
        expectedPersons.add("Katya");
        expectedPersons.add("Nikolay");

        assertEquals(expectedPersons, Task15_17.task15(persons));
    }

    @Test
    void testTask16() {
        List<PersonForTask15> persons = new ArrayList<>();
        persons.add(new PersonForTask15("Katya", 52));
        persons.add(new PersonForTask15("Katya", 34));
        persons.add(new PersonForTask15("Ilya", 20));
        persons.add(new PersonForTask15("Ilya", 18));
        persons.add(new PersonForTask15("Nikolay", 32));
        persons.add(new PersonForTask15("Yana", 42));

        List<String> expectedPersons = new ArrayList<>();
        expectedPersons.add("Katya");
        expectedPersons.add("Nikolay");
        expectedPersons.add("Yana");

        assertEquals(expectedPersons, Task15_17.task16(persons));
    }

    @Test
    void testTask17() {
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);

        assertAll(
                () -> assertEquals(15, Task15_17.sum(list)),
                () -> assertEquals(120, Task15_17.product(list))
        );
    }
}