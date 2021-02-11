package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task10_11Test {

    @Test
    void testTask10() {
        assertAll(
                () -> assertEquals(3, Task10_11.count.apply(Task10_11.split.apply("12 34 56"))),
                () -> assertEquals(2, Task10_11.count.apply(Task10_11.split.apply("Katya Ilya"))),
                () -> assertEquals(1, Task10_11.count.apply(Task10_11.split.apply("New"))),
                () -> assertEquals(0, Task10_11.count.apply(Task10_11.split.apply(" ")))
        );
    }

}