package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task12Part1Test {

    @Test
    void testGetMothersMotherFatherWithExistPerson() {
        Person person = new Person("Katya",
                new Person("Andrei", null, null),
                new Person("Lisa",
                        new Person("Sasha", null, null),
                        new Person("Lyoda",
                                new Person("Ivan", null, null),
                                new Person("Anna", null, null)
                        )
                )
        );
        Person expectedPerson = new Person("Ivan", null, null);

        assertEquals(expectedPerson, Task12Part1.getMothersMotherFather(person));
    }

    @Test
    void testGetMothersMotherFatherWithNull1() {
        Person person = new Person("Katya",
                new Person("Andrei", null, null),
                new Person("Lisa",
                        new Person("Sasha", null, null),
                        new Person("Lyoda",
                                null,
                                new Person("Anna", null, null)
                        )
                )
        );

        assertNull(Task12Part1.getMothersMotherFather(person));
    }

    @Test
    void testGetMothersMotherFatherWithNull() {
        Person person = new Person("Katya",
                new Person("Andrei", null, null),
                new Person("Lisa",
                        new Person("Sasha", null, null),
                        null
                )
        );

        assertNull(Task12Part1.getMothersMotherFather(person));
    }
}