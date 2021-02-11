package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Task12Part2Test {

    @Test
    void testGetMothersMotherFatherWithExistPerson() {
        PersonOptional person = new PersonOptional("Katya",
                new PersonOptional("Andrei", null, null),
                new PersonOptional("Lisa",
                        new PersonOptional("Sasha", null, null),
                        new PersonOptional("Lyoda",
                                new PersonOptional("Ivan", null, null),
                                new PersonOptional("Anna", null, null)
                        )
                )
        );
        PersonOptional expectedPerson = new PersonOptional("Ivan", null, null);

        assertEquals(Optional.of(expectedPerson), Task12Part2.getMothersMotherFather(person));
    }

    @Test
    void testGetMothersMotherFatherWithNull1() {
        PersonOptional person = new PersonOptional("Katya",
                new PersonOptional("Andrei", null, null),
                new PersonOptional("Lisa",
                        new PersonOptional("Sasha", null, null),
                        new PersonOptional("Lyoda",
                                null,
                                new PersonOptional("Anna", null, null)
                        )
                )
        );

        assertEquals(Optional.empty(), Task12Part2.getMothersMotherFather(person));
    }

    @Test
    void testGetMothersMotherFatherWithNull() {
        PersonOptional person = new PersonOptional("Katya",
                new PersonOptional("Andrei", null, null),
                new PersonOptional("Lisa",
                        new PersonOptional("Sasha", null, null),
                        null
                )
        );

        assertEquals(Optional.empty(), Task12Part2.getMothersMotherFather(person));
    }
}