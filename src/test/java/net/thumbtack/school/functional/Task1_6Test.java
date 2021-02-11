package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task1_6Test {

    @Test
    void testTask1() {
        assertAll(
                () -> assertEquals(3, Task1_6.countTask1.apply(Task1_6.splitTask1.apply("12 34 56"))),
                () -> assertEquals(2, Task1_6.countTask1.apply(Task1_6.splitTask1.apply("Katya Ilya"))),
                () -> assertEquals(1, Task1_6.countTask1.apply(Task1_6.splitTask1.apply("New"))),
                () -> assertEquals(0, Task1_6.countTask1.apply(Task1_6.splitTask1.apply(" ")))
        );
    }

    @Test
    void testTask2() {
        assertAll(
                () -> assertEquals(3, Task1_6.countTask2.apply(Task1_6.splitTask2.apply("12 34 56"))),
                () -> assertEquals(2, Task1_6.countTask2.apply(Task1_6.splitTask2.apply("Katya Ilya"))),
                () -> assertEquals(1, Task1_6.countTask2.apply(Task1_6.splitTask2.apply("New"))),
                () -> assertEquals(0, Task1_6.countTask2.apply(Task1_6.splitTask2.apply(" ")))
        );
    }

    @Test
    void testTask3() {
        assertAll(
                () -> assertEquals(3, Task1_6.countTask3.apply(Task1_6.splitTask3.apply("12 34 56"))),
                () -> assertEquals(2, Task1_6.countTask3.apply(Task1_6.splitTask3.apply("Katya Ilya"))),
                () -> assertEquals(1, Task1_6.countTask3.apply(Task1_6.splitTask3.apply("New"))),
                () -> assertEquals(0, Task1_6.countTask3.apply(Task1_6.splitTask3.apply(" ")))
        );
    }

    @Test
    void test1Task4() {
        assertAll(
                () -> assertEquals(3, Task1_6.splitAndCount1.apply("12 34 56")),
                () -> assertEquals(2, Task1_6.splitAndCount1.apply("Katya Ilya")),
                () -> assertEquals(1, Task1_6.splitAndCount1.apply("New")),
                () -> assertEquals(0, Task1_6.splitAndCount1.apply(" "))
        );
    }

    @Test
    void test2Task4() {
        assertAll(
                () -> assertEquals(3, Task1_6.splitAndCount2.apply("12 34 56")),
                () -> assertEquals(2, Task1_6.splitAndCount2.apply("Katya Ilya")),
                () -> assertEquals(1, Task1_6.splitAndCount2.apply("New")),
                () -> assertEquals(0, Task1_6.splitAndCount2.apply(" "))
        );
    }

    @Test
    void testTask5() {
        assertAll(
                () -> assertEquals(new PersonForTask5("Katya"), Task1_6.create1.apply("Katya")),
                () -> assertEquals(new PersonForTask5("Katya"), Task1_6.create2.apply("Katya"))
        );
    }

    @Test
    void testTask6() {
        assertAll(
                () -> assertEquals(5, Task1_6.max.apply(1, 5)),
                () -> assertEquals(2, Task1_6.max.apply(2, 0))
        );
    }

}